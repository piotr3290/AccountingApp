package pl.wroblewski.simplyaccounting.db.entities;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@DynamicUpdate
@Table(name = "advances_types", schema = "simplyaccounting")
public class AdvanceTypeEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @Basic
    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @Basic
    @Column(name = "calculation_type", nullable = false)
    private int calculationType;

    @OneToMany(mappedBy = "advanceType")
    private Collection<AdvanceEntity> advances;

    @OneToMany(mappedBy = "advanceType")
    private Collection<ConsumptionEntity> consumptions;

    @OneToMany(mappedBy = "advanceType")
    private Collection<RateEntity> rates;

}
