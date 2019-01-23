package ru.fudzya.hibernate.inheritance.mixed

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ForeignKey
import javax.persistence.SecondaryTable
import javax.validation.constraints.NotNull

@Entity
@SecondaryTable(name = 'card', foreignKey = @ForeignKey(name = 'fk_card_id'))
class Card extends PaymentInstrument {

    @NotNull
    @Column(table = 'card', nullable = false)
    String cardHolder
}
