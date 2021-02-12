package pl.wroblewski.simplyaccounting.db.entities;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@DynamicUpdate
@Table(name = "accounts", schema = "simplyaccounting")
public class AccountEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @ManyToOne
    @JoinColumn(name = "account_type_id", referencedColumnName = "id", nullable = false)
    private AccountTypeEntity accountType;

    @OneToMany(mappedBy = "account")
    private Collection<ContractorEntity> contractors;

    @OneToMany(mappedBy = "account")
    private Collection<CooperativeEntity> cooperatives;

    @OneToMany(mappedBy = "account")
    private Collection<LandlordEntity> landlords;

    @OneToMany(mappedBy = "account")
    private Collection<OpeningBalanceEntity> openingBalances;

}
