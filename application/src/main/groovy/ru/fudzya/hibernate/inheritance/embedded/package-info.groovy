package ru.fudzya.hibernate.inheritance.embedded

@GenericGenerator(name = 'ITEM_GENERATOR', strategy = 'enhanced_sequence', parameters = [
    @org.hibernate.annotations.Parameter(name = 'sequence_name', value = 'S_ITEM_GENERATOR')
])
import org.hibernate.annotations.GenericGenerator
