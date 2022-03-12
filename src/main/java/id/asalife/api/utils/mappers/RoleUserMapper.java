package id.asalife.api.utils.mappers;

import id.asalife.api.domains.ERole;
import id.asalife.api.domains.ERoleRegister;

public final class RoleUserMapper {
    public static ERole mapRole(ERoleRegister roleRegister) {
        switch (roleRegister) {
            case CUSTOMER:
                return ERole.ROLE_CUSTOMER;
            case WORKER:
                return ERole.ROLE_WORKER;
            default:
                return null;
        }
    }
}
