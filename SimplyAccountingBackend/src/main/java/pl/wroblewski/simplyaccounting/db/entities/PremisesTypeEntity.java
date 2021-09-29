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
@Table(name = "premises_types", schema = "simplyaccounting")
public class PremisesTypeEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Basic
    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @OneToMany(mappedBy = "premisesType")
    private Collection<PremisesEntity> premises;

    @OneToMany(mappedBy = "premisesType")
    private Collection<ChargeElementEntity> chargeElements;

}
