package ru.fudzya.test.message

import com.googlecode.junittoolbox.MultithreadingTester
import org.junit.After
import org.junit.Assert
import org.junit.Test
import org.slf4j.LoggerFactory
import ru.fudzya.hibernate.DataAccessServicesRegistry
import ru.fudzya.junit.jpa.JpaSupported
import ru.fudzya.message.Message

import javax.persistence.EntityManager

class JpaMessageTest extends JpaSupported {

	static def LOGGER = LoggerFactory.getLogger(JpaMessageTest)

	@Override
	protected String jndiDataSourcesName() {
		return 'message'
	}

	@Test
	void '[JPA Single Thread] test save/update/load entity'() {
		DataAccessServicesRegistry.INSTANCE.forInstance().voidCall { EntityManager manager ->

			def message = new Message(text: 'Initial message')

			LOGGER.info('попытка сохранить новую сущность')
			manager.persist(message)

			LOGGER.info('изменяем поле сущности')
			message.text = 'Changed message'

			LOGGER.info('загрузка сущности и тест на корректность полученных результатов')
			Collection<Message> messages = manager.createQuery('select m from Message m').getResultList()
			Assert.assertEquals(messages.size(), 1)
			Assert.assertEquals(messages.first().asType(Message).text, message.text)

			LOGGER.info('тест сущности завершён')
		}
	}

	@Test
	void '[JPA Multi Thread] test utilities'() {

		final Set<EntityManager> managers = Collections.synchronizedSet(new HashSet<EntityManager>())

		new MultithreadingTester().numThreads(30).numRoundsPerThread(1).add({ ->
			DataAccessServicesRegistry.INSTANCE.forInstance().voidCall { EntityManager manager ->
				if (managers.size() > 0) {
					Assert.assertFalse(managers.contains(manager))
				}
				else {
					managers.add(manager)
				}
			}
		}).run()
	}

	@After
	void afterMethod() {
		DataAccessServicesRegistry.INSTANCE.close()
	}
}
