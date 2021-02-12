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
@Table(name = "rates", schema = "simplyaccounting")
public class RateEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @Basic
    @Column(name = "start_date", nullable = true)
    private Date startDate;

    @Basic
    @Column(name = "end_date", nullable = true)
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "cooperative_id", referencedColumnName = "id")
    private CooperativeEntity cooperative;

    @ManyToOne
    @JoinColumn(name = "building_id", referencedColumnName = "id")
    private BuildingEntity building;

    @ManyToOne
    @JoinColumn(name = "premises_id", referencedColumnName = "id")
    private PremisesEntity premises;

    @ManyToOne
    @JoinColumn(name = "advance_type_id", referencedColumnName = "id", nullable = false)
    private AdvanceTypeEntity advanceType;

}
