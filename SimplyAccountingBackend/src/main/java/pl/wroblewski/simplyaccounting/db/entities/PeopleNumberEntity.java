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
@Table(name = "people_numbers", schema = "simplyaccounting")
public class PeopleNumberEntity implements IDatePeriod {
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
    @Column(name = "number", nullable = false)
    private Integer number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "premises_id", referencedColumnName = "id", nullable = false)
    private PremisesEntity premises;

}
