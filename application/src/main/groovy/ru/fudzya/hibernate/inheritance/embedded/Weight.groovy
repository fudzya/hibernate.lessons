package ru.fudzya.hibernate.inheritance.embedded

import javax.persistence.AttributeOverride
import javax.persistence.AttributeOverrides
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.validation.constraints.NotNull

@AttributeOverrides([
    @AttributeOverride(name = 'symbol',      column = @Column(name = 'weight_symbol',      nullable = false)),
    @AttributeOverride(name = 'description', column = @Column(name = 'weight_description', nullable = false))
])
@Embeddable
class Weight extends Measurement {
    
    @NotNull
    @Column(nullable = false)
    BigDecimal weight
}
