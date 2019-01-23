package ru.fudzya.hibernate.inheritance.join

import javax.persistence.Entity
import javax.persistence.ForeignKey
import javax.persistence.PrimaryKeyJoinColumn

@Entity
@PrimaryKeyJoinColumn(name = 'account_id', foreignKey = @ForeignKey(name = 'fk_account_id'))
class Account extends PaymentInstrument {
}
