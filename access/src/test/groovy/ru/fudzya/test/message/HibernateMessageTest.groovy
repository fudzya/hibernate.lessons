package ru.fudzya.test.message

import com.googlecode.junittoolbox.MultithreadingTester
import org.hibernate.Session
import org.junit.After
import org.junit.Assert
import org.junit.Test
import org.slf4j.LoggerFactory
import ru.fudzya.db.Type
import ru.fudzya.hibernate.DataAccessServicesRegistry
import ru.fudzya.junit.hibernate.HibernateSupported
import ru.fudzya.message.Message

class HibernateMessageTest extends HibernateSupported {

	static def LOGGER = LoggerFactory.getLogger(HibernateMessageTest)

	@Override
	protected String jndiDataSourceName() {
		return 'message'
	}

	@Override
	protected Collection<String> hibernateConfigFilesName() {
		return ['message/hibernate.cfg.xml']
	}

	@Test
	void '[Hibernate Single Thread] test save/update/load entity'() {
		DataAccessServicesRegistry.INSTANCE.forInstance(Type.HIBERNATE).voidCall { Session session ->

			def message = new Message(text: 'Initial message')
			LOGGER.info('попытка сохранить новую сущность')
			session.save(message)

			LOGGER.info('изменяем поле сущности')
			message.text = 'Changed message'

			LOGGER.info('загрузка сущности и тест на корректность полученных результатов')
			Collection<Message> messages = session.getNamedQuery('Hibernate.Message.All').getResultList()
			Assert.assertEquals(messages.size(), 1)
			Assert.assertEquals(messages.first().asType(Message).text, 'Changed message')

			LOGGER.info('тест сущности завершён')
		}
	}

	@Test
	void '[Hibernate Multi Thread] test utilities'() {

		final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>())

		new MultithreadingTester().numThreads(30).numRoundsPerThread(1).add {
			DataAccessServicesRegistry.INSTANCE.forInstance(Type.HIBERNATE).voidCall { Session session ->
				if (sessions.size() > 0) {
					Assert.assertFalse(sessions.contains(session))
				}
				else {
					sessions.add(session)
				}
			}
		}.run()

		sessions.clear()
	}

	@After
	void afterMethod() {
		DataAccessServicesRegistry.INSTANCE.close()
	}
}
