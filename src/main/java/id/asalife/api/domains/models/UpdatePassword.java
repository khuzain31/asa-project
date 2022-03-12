package id.asalife.api.domains.models;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UpdatePassword {
    @NotEmpty(message = "NewPassword is mandatory")
    public String newPassword;
}

