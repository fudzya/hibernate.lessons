package ru.fudzya.hibernate.inheritance.single

@GenericGenerator(name = 'PAYMENT_INSTRUMENT_GENERATOR', strategy = 'enhanced_sequence', parameters = [
    @Parameter(name = 'sequence_name', value = 'S_PAYMENT_INSTRUMENT_GENERATOR')
])
import org.hibernate.annotations.Parameter
import org.hibernate.annotations.GenericGenerator
