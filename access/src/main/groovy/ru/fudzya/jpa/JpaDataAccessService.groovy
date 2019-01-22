package ru.fudzya.jpa

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ru.fudzya.db.DataAccessService

import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory

class JpaDataAccessService implements DataAccessService {

	private static Logger LOGGER = LoggerFactory.getLogger(JpaDataAccessService)

	private final EntityManagerFactory factory

	JpaDataAccessService(EntityManagerFactory factory) {
		this.factory = factory
	}

	@Override
	void voidCall(Closure closure) {

		factory.createEntityManager().with { EntityManager manager ->

			def transaction = manager.getTransaction()
			
			try {
				transaction.begin()
				closure.call(manager)
				transaction.commit()
			}
			catch (Throwable e) {
				LOGGER.error('Ошибка при выполнении запроса', e)

				if (transaction.isActive() || transaction.getRollbackOnly()) {
					transaction.rollback()
				}

				throw e
			}
			finally {
				LOGGER.info('Закрываем EntityManager')
				manager.close()
			}
		}
	}
}
