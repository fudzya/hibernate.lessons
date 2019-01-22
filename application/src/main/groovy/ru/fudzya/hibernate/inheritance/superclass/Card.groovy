package ru.fudzya.hibernate.inheritance.superclass

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.NotNull

@Entity
class Card extends PaymentInstrument {

    @Id
    @GeneratedValue(generator = 'CARD_GENERATOR')
    protected Long id

    @NotNull
    @Column
    String cardHolder

    @Override
    Long getId() {
        return id
    }
}
