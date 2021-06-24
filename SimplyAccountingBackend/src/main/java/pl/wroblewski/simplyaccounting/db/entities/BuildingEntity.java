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
@Table(name = "buildings", schema = "simplyaccounting")
public class BuildingEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Basic
    @Column(name = "city", nullable = false, length = 64)
    private String city;

    @Basic
    @Column(name = "street", nullable = false, length = 255)
    private String street;

    @Basic
    @Column(name = "house_number", nullable = false, length = 8)
    private String houseNumber;

    @Basic
    @Column(name = "postal_code", nullable = false, length = 6)
    private String postalCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cooperative_id", referencedColumnName = "id", nullable = false)
    private CooperativeEntity cooperative;

    @OneToMany(mappedBy = "building")
    private Collection<PremisesEntity> premises;

    @OneToMany(mappedBy = "building")
    private Collection<RateEntity> rates;

    public String getAddress() {
        return Stream
                .of(street, houseNumber, postalCode, city)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(" "));
    }

}
