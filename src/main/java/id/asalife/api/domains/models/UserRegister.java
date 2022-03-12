package id.asalife.api.domains.models;

import id.asalife.api.utils.validators.ValidEnum;
import id.asalife.api.domains.ERoleRegister;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegister {

    @NotEmpty(message = "Name is mandatory")
    private String name;

    @NotEmpty(message = "NRP is mandatory")
    private String nrp;

    @NotEmpty(message = "Department is mandatory")
    private String department;

    @NotEmpty(message = "Password is mandatory")
    private String password;

    @NotNull(message = "Role is mandatory")
    @ValidEnum(enumClass = ERoleRegister.class, groups = ERoleRegister.class, message = "Role is not available")
    private ERoleRegister role;
}
