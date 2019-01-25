package ru.fudzya.hibernate.inheritance.embedded

import org.hibernate.Session
import org.junit.After
import org.junit.Test
import ru.fudzya.db.Type
import ru.fudzya.hibernate.DataAccessServicesRegistry
import ru.fudzya.junit.hibernate.HibernateSupported

class HibernatePaymentInstruments extends HibernateSupported {

    @Test
    void '[Hibernate] test select item'() {
        DataAccessServicesRegistry.INSTANCE.forInstance(Type.HIBERNATE).voidCall { Session session ->

            Weight    weight    = new Weight   (symbol: 'kg', description: 'kilogram',    weight: new BigDecimal(10d))
            Dimension dimension = new Dimension(symbol: 'cm', description: 'centimeters', width: new BigDecimal(10d), heigth: new BigDecimal(10d), depth: new BigDecimal(10d))

            session.save(new Item(weight, dimension))
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
