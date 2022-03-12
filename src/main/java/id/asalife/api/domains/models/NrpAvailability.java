package id.asalife.api.domains.models;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class NrpAvailability {
    @NotEmpty(message = "NRP is mandatory")
    private String nrp;
}
