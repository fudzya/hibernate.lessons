package ru.fudzya.hibernate.inheritance.embedded

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Item {

    @Id
    @GeneratedValue(generator = 'ITEM_GENERATOR')
    private Long id

    Weight    weight
    Dimension dimension

    Item() {
    }

    Item(Weight weight, Dimension dimension) {
        this.weight    = weight
        this.dimension = dimension
    }

    Long getId() {
        return id
    }
}
