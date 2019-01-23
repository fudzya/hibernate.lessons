package ru.fudzya.hibernate.inheritance.single

import javax.persistence.Column
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.validation.constraints.NotNull

@Entity
@DiscriminatorValue('C')
class Card extends PaymentInstrument {

    @NotNull
    @Column
    String cardHolder
}
