package pl.wroblewski.simplyaccounting.db.entities;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@DynamicUpdate
@Table(name = "landlords_payments", schema = "simplyaccounting")
public class LandlordPaymentEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @Basic
    @Column(name = "realization_date", nullable = false)
    private LocalDate realizationDate;

    @ManyToOne
    @JoinColumn(name = "cooperative_id", referencedColumnName = "id", nullable = false)
    private CooperativeEntity cooperative;

    @ManyToOne
    @JoinColumn(name = "landlord_id", referencedColumnName = "id", nullable = false)
    private LandlordEntity landlord;

}
