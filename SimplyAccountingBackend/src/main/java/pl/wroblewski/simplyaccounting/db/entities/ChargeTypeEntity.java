package pl.wroblewski.simplyaccounting.db.entities;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@DynamicUpdate
@Table(name = "charges_types", schema = "simplyaccounting")
public class ChargeTypeEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Basic
    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @Basic
    @Column(name = "number", nullable = false)
    private Integer number;

    @OneToMany(mappedBy = "chargeType")
    private Collection<AdvanceEntity> advances;

    @OneToMany(mappedBy = "chargeType")
    private Collection<ConsumptionEntity> consumptions;

    @OneToMany(mappedBy = "chargeType")
    private Collection<RateEntity> rates;

    @OneToMany(mappedBy = "chargeType")
    private Collection<ChargeElementEntity> chargeElements;

}
