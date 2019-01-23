package ru.fudzya.hibernate.inheritance.mixed

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue('A')
class Account extends PaymentInstrument {
}
