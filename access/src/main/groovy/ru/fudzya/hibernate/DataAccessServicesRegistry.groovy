package ru.fudzya.hibernate

import groovy.transform.stc.ClosureParams
import groovy.transform.stc.FirstParam
import org.hibernate.internal.SessionFactoryRegistry
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ru.fudzya.common.dataaccess.utils.Constants
import ru.fudzya.db.DataAccessService
import ru.fudzya.db.Type
import ru.fudzya.common.dataaccess.utils.NamesUtils
import ru.fudzya.jpa.JpaDataAccessService

import javax.persistence.Persistence
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.locks.ReadWriteLock
import java.util.concurrent.locks.ReentrantReadWriteLock

@Singleton(property = 'INSTANCE', lazy = true)
final class DataAccessServicesRegistry implements AutoCloseable {

	private static Logger LOGGER = LoggerFactory.getLogger(DataAccessServicesRegistry)

	private final Map<String, DataAccessService> services = new ConcurrentHashMap<>()
	private final ReadWriteLock lock = new ReentrantReadWriteLock()

	DataAccessService forInstance(Type type = Type.JPA, String name = Constants.DEFAULT_REGISTRY_ENTRY_NAME) {

		if (type == Type.HIBERNATE) {
			name = NamesUtils.toSessionFactoryName(name)
			return getOrCreate(name, { value -> new HibernateDataAccessService(SessionFactoryRegistry.INSTANCE.getNamedSessionFactory(value)) })
		}
		else {
			name = NamesUtils.toPersistenceUnitName(name)
			return getOrCreate(name, { value -> new JpaDataAccessService(Persistence.createEntityManagerFactory(value)) })
		}
	}

	private DataAccessService getOrCreate(String name, @ClosureParams(FirstParam) Closure<DataAccessService> closure = null) {
		try {
			lock.readLock().lock()
			if (services.containsKey(name)) {
				LOGGER.debug('Сервис найден по ключу {}', name)
				return services.get(name)
			}
		}
		finally {
			lock.readLock().unlock()
		}

		try {

			lock.writeLock().lock()
			LOGGER.debug('Блокировка установлена для сервиса с ключом  {}', name)
			if (services.containsKey(name)) {
				LOGGER.debug('Сервис найден при повторном поиске по ключу {}', name)
				return services.get(name)
			}

			def service = closure.call(name)
			services.put(name, service)
			LOGGER.debug('Сервис создан и добавлен с ключом {}', name)
			return service
		}
		finally {
			lock.writeLock().unlock()
		}
	}

	void close() {
		try {
			lock.writeLock().lock()
			services.clear()
		}
		finally {
			lock.writeLock().unlock()
		}
	}
}
