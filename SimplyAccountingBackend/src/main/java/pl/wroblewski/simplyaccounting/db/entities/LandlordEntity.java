package pl.wroblewski.simplyaccounting.db.entities;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@DynamicUpdate
@Table(name = "landlords", schema = "simplyaccounting")
public class LandlordEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @Basic
    @Column(name = "first_name", nullable = true, length = 64)
    private String firstName;

    @Basic
    @Column(name = "middle_name", nullable = true, length = 64)
    private String middleName;

    @Basic
    @Column(name = "last_name", nullable = true, length = 64)
    private String lastName;

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
    @Column(name = "premises_number", nullable = true, length = 8)
    private String premisesNumber;

    @Basic
    @Column(name = "postal_code", nullable = true, length = 6)
    private String postalCode;

    @Basic
    @Column(name = "pesel", nullable = true, length = 11)
    private String pesel;

    @Basic
    @Column(name = "phone_number", nullable = true, length = 15)
    private String phoneNumber;

    @Basic
    @Column(name = "email_address", nullable = true, length = 255)
    private String emailAddress;

    @OneToMany(mappedBy = "landlord")
    private Collection<AdvanceEntity> advances;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private AccountEntity account;

    @OneToMany(mappedBy = "landlord")
    private Collection<LandlordPaymentEntity> landlordsPayments;

    @OneToMany(mappedBy = "landlord")
    private Collection<PremisesLandlordEntity> premisesLandlords;

    public String getAddress() {
        return Stream
                .of(street, houseNumber, premisesNumber, postalCode, city)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(" "));
    }

    public String getFullName() {
        return Stream
                .of(lastName, firstName, middleName)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(" "));
    }
}
