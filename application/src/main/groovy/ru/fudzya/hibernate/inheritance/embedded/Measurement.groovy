package ru.fudzya.hibernate.inheritance.embedded

import javax.persistence.MappedSuperclass
import javax.validation.constraints.NotNull

@MappedSuperclass
abstract class Measurement {

    @NotNull
    String symbol

    @NotNull
    String description
}
