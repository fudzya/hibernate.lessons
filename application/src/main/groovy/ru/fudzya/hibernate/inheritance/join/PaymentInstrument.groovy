package ru.fudzya.hibernate.inheritance.join

import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
/*
 * @DiscriminatorColumn(name = 'type') при её использовании выборка по JoinPaymentInstrument будет использовать
 * не case для определения типа наследника, а дискриминатор
 */
abstract class PaymentInstrument {

    @Id
    @GeneratedValue(generator = 'PAYMENT_INSTRUMENT_GENERATOR')
    protected Long id

    @NotNull
    @Column(nullable = false)
    String number

    Long getId() {
        return id
    }
}
