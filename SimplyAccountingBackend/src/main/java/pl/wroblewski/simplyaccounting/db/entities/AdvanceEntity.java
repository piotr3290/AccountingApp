package pl.wroblewski.simplyaccounting.db.entities;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@DynamicUpdate
@Table(name = "advances", schema = "simplyaccounting")
public class AdvanceEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @Basic
    @Column(name = "realization_date", nullable = false)
    private Date realizationDate;

    @Basic
    @Column(name = "month", nullable = false)
    private byte month;

    @Basic
    @Column(name = "year", nullable = false)
    private short year;

    @ManyToOne
    @JoinColumn(name = "cooperative_id", referencedColumnName = "id", nullable = false)
    private CooperativeEntity cooperative;

    @ManyToOne
    @JoinColumn(name = "advance_type_id", referencedColumnName = "id", nullable = false)
    private AdvanceTypeEntity advanceType;

    @ManyToOne
    @JoinColumn(name = "landlord_id", referencedColumnName = "id", nullable = false)
    private LandlordEntity landlord;

}
