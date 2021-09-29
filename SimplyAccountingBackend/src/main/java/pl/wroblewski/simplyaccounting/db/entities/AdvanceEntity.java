package pl.wroblewski.simplyaccounting.db.entities;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@DynamicUpdate
@Table(name = "advances", schema = "simplyaccounting")
public class AdvanceEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Basic
    @Column(name = "amount", nullable = false)
    private Double amount;

    @Basic
    @Column(name = "realization_date", nullable = false)
    @Builder.Default
    private LocalDate realizationDate = LocalDate.now();

    @Basic
    @Column(name = "month", nullable = false)
    private Byte month;

    @Basic
    @Column(name = "year", nullable = false)
    private Short year;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "premises_id", referencedColumnName = "id", nullable = false)
    private PremisesEntity premises;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "charge_type_id", referencedColumnName = "id", nullable = false)
    private ChargeTypeEntity chargeType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landlord_id", referencedColumnName = "id", nullable = false)
    private LandlordEntity landlord;

}
