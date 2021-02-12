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
@Table(name = "invoices", schema = "simplyaccounting")
public class InvoiceEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @Basic
    @Column(name = "amount", nullable = false, precision = 0)
    private double amount;

    @Basic
    @Column(name = "realization_date", nullable = false)
    private Date realizationDate;

    @ManyToOne
    @JoinColumn(name = "invoice_type_id", referencedColumnName = "id", nullable = false)
    private InvoiceTypeEntity invoiceType;

    @ManyToOne
    @JoinColumn(name = "cooperative_id", referencedColumnName = "id", nullable = false)
    private CooperativeEntity cooperative;

    @ManyToOne
    @JoinColumn(name = "contractor_id", referencedColumnName = "id", nullable = false)
    private ContractorEntity contractor;

}
