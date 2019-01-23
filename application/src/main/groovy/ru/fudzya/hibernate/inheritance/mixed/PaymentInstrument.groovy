package ru.fudzya.hibernate.inheritance.mixed

import javax.persistence.DiscriminatorColumn
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.validation.constraints.NotNull

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = 'DISCRIMINATOR')
abstract class PaymentInstrument {

    @Id
    @GeneratedValue(generator = 'PAYMENT_INSTRUMENT_GENERATOR')
    protected Long id

    @NotNull
    String number

    Long getId() {
        return id
    }
}
