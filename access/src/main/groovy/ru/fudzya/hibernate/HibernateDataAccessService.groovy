package ru.fudzya.hibernate

import groovy.transform.PackageScope
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.resource.transaction.spi.TransactionStatus
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ru.fudzya.db.DataAccessService

@PackageScope
class HibernateDataAccessService implements DataAccessService {

	private static Logger LOGGER = LoggerFactory.getLogger(HibernateDataAccessService)

	private final SessionFactory sessionFactory

	HibernateDataAccessService(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory
	}

	@Override
	void voidCall(Closure closure) {

		sessionFactory.openSession().withCloseable { Session session ->

			def transaction = session.getTransaction()
			
			try {
				transaction.begin()
				closure(session)
				transaction.commit()
			}
			catch (Throwable e) {
				LOGGER.error('Ошибка при выполнении запроса', e)

				if (transaction.isActive() || transaction.getStatus() == TransactionStatus.MARKED_ROLLBACK) {
					transaction.rollback()
				}

				throw e
			}
		}
	}
}
