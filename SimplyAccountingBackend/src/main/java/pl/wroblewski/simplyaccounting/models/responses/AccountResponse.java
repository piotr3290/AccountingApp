package pl.wroblewski.simplyaccounting.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponse {

    private Integer id;

    private Integer accountTypeId;

    private String subjectName;

    private Integer subjectId;
}
