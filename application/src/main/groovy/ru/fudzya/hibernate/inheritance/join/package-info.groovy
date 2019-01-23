package ru.fudzya.hibernate.inheritance.join

@GenericGenerators([
    @GenericGenerator(name = 'PAYMENT_INSTRUMENT_GENERATOR', strategy = 'enchanced_sequence', parameters = [
        @Parameter(name = 'sequence_name', value = 'S_PAYMENT_INSTRUMENT_GENERATOR')
    ])
])

import org.hibernate.annotations.Parameter
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.GenericGenerators