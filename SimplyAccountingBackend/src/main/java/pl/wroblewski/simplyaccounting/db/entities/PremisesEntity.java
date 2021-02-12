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
@Table(name = "premises", schema = "simplyaccounting")
public class PremisesEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @Basic
    @Column(name = "premises_number", nullable = false, length = 8)
    private String premisesNumber;

    @OneToMany(mappedBy = "premises")
    private Collection<AreaEntity> areas;

    @OneToMany(mappedBy = "premises")
    private Collection<ConsumptionEntity> consumptions;

    @OneToMany(mappedBy = "premises")
    private Collection<PeopleNumberEntity> peopleNumbers;

    @ManyToOne
    @JoinColumn(name = "building_id", referencedColumnName = "id", nullable = false)
    private BuildingEntity building;

    @ManyToOne
    @JoinColumn(name = "premises_type_id", referencedColumnName = "id", nullable = false)
    private PremisesTypeEntity premisesType;

    @OneToMany(mappedBy = "premises")
    private Collection<PremisesLandlordEntity> premisesLandlords;

    @OneToMany(mappedBy = "premises")
    private Collection<RateEntity> rates;

}
