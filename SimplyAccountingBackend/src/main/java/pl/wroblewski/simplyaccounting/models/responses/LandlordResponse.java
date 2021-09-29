package pl.wroblewski.simplyaccounting.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class LandlordResponse {

    private Integer id;

    private String fullName;

    private String address;

    private String addressEmail;

    private String phoneNumber;
}
