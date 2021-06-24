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
@Table(name = "landlords_payments", schema = "simplyaccounting")
public class LandlordPaymentEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Basic
    @Column(name = "realization_date", nullable = false)
    private LocalDate realizationDate;

    @Basic
    @Column(name = "value", nullable = false)
    private Double value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "premises_id", referencedColumnName = "id", nullable = false)
    private PremisesEntity premises;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landlord_id", referencedColumnName = "id", nullable = false)
    private LandlordEntity landlord;

}
