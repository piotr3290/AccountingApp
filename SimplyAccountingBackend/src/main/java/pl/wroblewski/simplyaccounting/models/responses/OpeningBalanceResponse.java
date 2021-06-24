package pl.wroblewski.simplyaccounting.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpeningBalanceResponse {

    private Integer id;

    private Double balance;

    private Integer accountId;

    private Integer year;

    private Integer cooperativeId;

    private String cooperativeName;
}
