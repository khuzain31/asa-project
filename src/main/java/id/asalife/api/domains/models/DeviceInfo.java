package id.asalife.api.domains.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class DeviceInfo {
    private String userAgent;
    private String remoteAddr;
}
