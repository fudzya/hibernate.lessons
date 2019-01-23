package ru.fudzya.hibernate.inheritance.join

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ForeignKey
import javax.persistence.PrimaryKeyJoinColumn
import javax.validation.constraints.NotNull

@Entity
@PrimaryKeyJoinColumn(foreignKey = @ForeignKey(name = 'fk_card_id'))
class Card extends PaymentInstrument {

    @NotNull
    @Column
    String cardHolder
}
