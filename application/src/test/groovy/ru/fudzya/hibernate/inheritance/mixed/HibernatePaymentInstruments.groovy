package ru.fudzya.hibernate.inheritance.mixed

import org.hibernate.Session
import org.junit.After
import org.junit.Assert
import org.junit.Test
import ru.fudzya.db.Type
import ru.fudzya.hibernate.DataAccessServicesRegistry
import ru.fudzya.junit.hibernate.HibernateSupported

class HibernatePaymentInstruments extends HibernateSupported {

    @Test
    void '[Hibernate] test select account'() {
        DataAccessServicesRegistry.INSTANCE.forInstance(Type.HIBERNATE).voidCall { Session session ->

            session.save(new Account(number: '12345678912345678900'))

            def query = session.getCriteriaBuilder().createQuery(PaymentInstrument).with { query ->
                query.select(from(PaymentInstrument))
            }

            Assert.assertTrue(session.createQuery(query).list().size() > 0)
        }
    }

    @Test
    void '[Hibernate] test select card'() {
        DataAccessServicesRegistry.INSTANCE.forInstance(Type.HIBERNATE).voidCall { Session session ->

            session.save(new Card(number: '12345678912345678900', cardHolder: 'HOLDER'))

            def query = session.getCriteriaBuilder().createQuery(PaymentInstrument).with { query ->
                query.select(from(PaymentInstrument))
            }

            Assert.assertTrue(session.createQuery(query).list().size() > 0)
        }
    }

    @Override
    protected String jndiDataSourceName() {
        return 'dataSource/inheritance'
    }

    @Override
    protected Collection<String> hibernateConfigFilesName() {
        return ['hibernate.cfg.xml']
    }

    @After
    void afterMethod() {
        DataAccessServicesRegistry.INSTANCE.close()
    }
}
