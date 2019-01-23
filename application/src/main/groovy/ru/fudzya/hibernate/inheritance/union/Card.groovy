package ru.fudzya.hibernate.inheritance.union

import javax.persistence.Column
import javax.persistence.Entity
import javax.validation.constraints.NotNull

@Entity
class Card extends PaymentInstrument {

    @NotNull
    @Column(nullable = false)
    String cardHolder
}
