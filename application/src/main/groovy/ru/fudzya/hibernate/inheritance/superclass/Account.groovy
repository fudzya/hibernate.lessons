package ru.fudzya.hibernate.inheritance.superclass

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Account extends PaymentInstrument {

    @Id
    @GeneratedValue(generator = 'ACCOUNT_GENERATOR')
    protected Long id

    @Override
    Long getId() {
        return id
    }
}
