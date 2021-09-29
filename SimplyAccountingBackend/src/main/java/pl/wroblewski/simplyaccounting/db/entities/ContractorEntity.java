package pl.wroblewski.simplyaccounting.db.entities;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@DynamicUpdate
@Table(name = "contractors", schema = "simplyaccounting")
public class ContractorEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Basic
    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @Basic
    @Column(name = "nip", nullable = true, length = 10)
    private String nip;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private AccountEntity account;

    @OneToMany(mappedBy = "contractor")
    @OrderBy("realizationDate")
    private List<ContractorPaymentEntity> contractorsPayments;

    @OneToMany(mappedBy = "contractor")
    @OrderBy("realizationDate")
    private List<InvoiceEntity> invoices;
}
