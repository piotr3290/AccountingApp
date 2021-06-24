package pl.wroblewski.simplyaccounting.db.entities;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@DynamicUpdate
@Table(name = "invoices_types", schema = "simplyaccounting")
public class InvoiceTypeEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Basic
    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @Basic
    @Column(name = "number", nullable = false)
    private Integer number;

    @OneToMany(mappedBy = "invoiceType")
    private Collection<InvoiceEntity> invoices;

}
