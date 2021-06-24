package pl.wroblewski.simplyaccounting.db.entities;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import pl.wroblewski.simplyaccounting.enums.RateHierarchyLevel;
import pl.wroblewski.simplyaccounting.interfaces.IDatePeriod;
import pl.wroblewski.simplyaccounting.interfaces.IHierarchy;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@DynamicUpdate
@Table(name = "rates", schema = "simplyaccounting")
public class RateEntity implements IDatePeriod, IHierarchy {
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
    @Column(name = "value", nullable = false)
    private Double value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cooperative_id", referencedColumnName = "id")
    private CooperativeEntity cooperative;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id", referencedColumnName = "id")
    private BuildingEntity building;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "premises_id", referencedColumnName = "id")
    private PremisesEntity premises;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "charge_type_id", referencedColumnName = "id", nullable = false)
    private ChargeTypeEntity chargeType;

    @Override
    public Integer getCooperativeId() {
        return cooperative != null ? cooperative.getId() : null;
    }

    @Override
    public Integer getPremisesId() {
        return premises != null ? premises.getId() : null;
    }

    @Override
    public Integer getBuildingId() {
        return building != null ? building.getId() : null;
    }

    public RateHierarchyLevel getHierarchyLevel() {
        return RateHierarchyLevel.getHierarchyLevel(this);
    }
}
