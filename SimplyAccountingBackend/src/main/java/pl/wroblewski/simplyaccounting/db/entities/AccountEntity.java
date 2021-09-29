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
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_type_id", referencedColumnName = "id", nullable = false)
    private AccountTypeEntity accountType;

    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY)
    private ContractorEntity contractor;

    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY)
    private CooperativeEntity cooperative;

    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY)
    private LandlordEntity landlord;

    @OneToMany(mappedBy = "account")
    private Collection<OpeningBalanceEntity> openingBalances;

    public String getSubjectName() {
        if (contractor != null) {
            return contractor.getName();
        } else if (landlord != null) {
            return landlord.getFullName();
        } else {
            return cooperative.getName();
        }
    }

    public Integer getSubjectId() {
        if (contractor != null) {
            return contractor.getId();
        } else if (landlord != null) {
            return landlord.getId();
        } else {
            return cooperative.getId();
        }
    }

}
