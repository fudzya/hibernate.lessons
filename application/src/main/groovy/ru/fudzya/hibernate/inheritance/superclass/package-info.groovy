package ru.fudzya.hibernate.inheritance.superclass

@GenericGenerators([
    @GenericGenerator(name = 'CARD_GENERATOR', strategy = 'enhanced-sequence', parameters = [
        @org.hibernate.annotations.Parameter(name = 'sequence_name', value = 'S_CARD_GENERATOR')
    ]),

    @GenericGenerator(name = 'ACCOUNT_GENERATOR', strategy = 'enhanced-sequence', parameters = [
        @org.hibernate.annotations.Parameter(name = 'sequence_name', value = 'S_ACCOUNT_GENERATOR')
    ])
])

import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.GenericGenerators
