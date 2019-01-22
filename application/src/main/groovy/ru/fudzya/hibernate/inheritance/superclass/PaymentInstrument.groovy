package ru.fudzya.hibernate.inheritance.superclass

import javax.persistence.MappedSuperclass
import javax.validation.constraints.NotNull

@MappedSuperclass
abstract class PaymentInstrument {

    @NotNull
    String number

    abstract Long getId()
}
