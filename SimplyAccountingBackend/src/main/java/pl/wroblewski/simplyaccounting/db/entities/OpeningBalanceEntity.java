package pl.wroblewski.simplyaccounting.db.entities;


import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@DynamicUpdate
@Table(name = "opening_balances", schema = "simplyaccounting")
public class OpeningBalanceEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Basic
    @Column(name = "year", nullable = false)
    private Short year;

    @Basic
    @Column(name = "balance", nullable = false, precision = 0)
    private Double balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private AccountEntity account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cooperative_id", referencedColumnName = "id", nullable = false)
    private CooperativeEntity cooperative;

}
