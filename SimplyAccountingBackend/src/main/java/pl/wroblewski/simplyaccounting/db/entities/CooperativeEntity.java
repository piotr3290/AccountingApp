package pl.wroblewski.simplyaccounting.db.entities;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@DynamicUpdate
@Table(name = "cooperatives", schema = "simplyaccounting")
public class CooperativeEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Basic
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Basic
    @Column(name = "nip", nullable = true, length = 10)
    private String nip;

    @Basic
    @Column(name = "regon", nullable = true, length = 14)
    private String regon;

    @Basic
    @Column(name = "city", nullable = true, length = 64)
    private String city;

    @Basic
    @Column(name = "street", nullable = true, length = 255)
    private String street;

    @Basic
    @Column(name = "house_number", nullable = true, length = 8)
    private String houseNumber;

    @Basic
    @Column(name = "postal_code", nullable = true, length = 6)
    private String postalCode;

    @OneToMany(mappedBy = "cooperative")
    private Collection<BuildingEntity> buildings;

    @OneToMany(mappedBy = "cooperative")
    private Collection<ContractorPaymentEntity> contractorsPayments;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private AccountEntity account;

    @OneToMany(mappedBy = "cooperative")
    private Collection<InvoiceEntity> invoices;

    @OneToMany(mappedBy = "cooperative")
    private Collection<RateEntity> rates;

    @OneToMany(mappedBy = "cooperative")
    private Collection<ChargeElementEntity> chargeElements;

    @OneToMany(mappedBy = "cooperative")
    private Collection<OpeningBalanceEntity> openingBalances;

    public String getAddress() {
        return Stream
                .of(street, houseNumber, postalCode, city)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(" "));
    }

}
