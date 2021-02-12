package pl.wroblewski.simplyaccounting.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LandlordResponse {
    private int id;
    private String fullName;
    private String address;
    private String addressEmail;
    private String phoneNumber;

}
