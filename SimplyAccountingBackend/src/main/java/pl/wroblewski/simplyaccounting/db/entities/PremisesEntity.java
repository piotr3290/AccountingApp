package pl.wroblewski.simplyaccounting.db.entities;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

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
    private Integer id;

    @Basic
    @Column(name = "premises_number", nullable = false, length = 8)
    private String premisesNumber;

    @OneToMany(mappedBy = "premises")
    @OrderBy("startDate")
    private List<AreaEntity> areas;

    @OneToMany(mappedBy = "premises")
    @OrderBy("startDate")
    private List<ConsumptionEntity> consumptions;

    @OneToMany(mappedBy = "premises")
    @OrderBy("startDate")
    private List<PeopleNumberEntity> peopleNumbers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id", referencedColumnName = "id", nullable = false)
    private BuildingEntity building;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "premises_type_id", referencedColumnName = "id", nullable = false)
    private PremisesTypeEntity premisesType;

    @OneToMany(mappedBy = "premises")
    @OrderBy("startDate")
    private List<PremisesLandlordEntity> premisesLandlords;

    @OneToMany(mappedBy = "premises")
    @OrderBy("startDate")
    private List<RateEntity> rates;

    @OneToMany(mappedBy = "premises")
    @OrderBy("realizationDate")
    private List<LandlordPaymentEntity> landlordPayments;

    @OneToMany(mappedBy = "premises")
    @OrderBy("realizationDate")
    private Collection<AdvanceEntity> advances;

}
