package id.asalife.api.domains.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequest {
    @NotEmpty(message = "NRP is mandatory")
    private String nrp;

    @NotEmpty(message = "Password is mandatory")
    private String password;
}
