package pl.wroblewski.simplyaccounting.db.entities;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import pl.wroblewski.simplyaccounting.interfaces.IDatePeriod;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@DynamicUpdate
@Table(name = "charges_elements", schema = "simplyaccounting")
public class ChargeElementEntity implements IDatePeriod {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Basic
    @Column(name = "start_date", nullable = true)
    private LocalDate startDate;

    @Basic
    @Column(name = "end_date", nullable = true)
    private LocalDate endDate;

    @Basic
    @Column(name = "multiply_people_number", nullable = true)
    @Builder.Default
    private Boolean multiplyPeopleNumber = false;

    @Basic
    @Column(name = "multiply_consumption", nullable = true)
    @Builder.Default
    private Boolean multiplyConsumption = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cooperative_id", referencedColumnName = "id", nullable = false)
    private CooperativeEntity cooperative;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "charge_type_id", referencedColumnName = "id", nullable = false)
    private ChargeTypeEntity chargeType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "premises_type_id", referencedColumnName = "id", nullable = true)
    private PremisesTypeEntity premisesType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_type_id", referencedColumnName = "id", nullable = true)
    private AreaTypeEntity areaType;

}
