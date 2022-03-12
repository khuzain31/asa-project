package id.asalife.api.utils.mappers;

import id.asalife.api.domains.ERole;
import id.asalife.api.domains.ERoleRegister;

public final class RoleAdminMapper {
    public static ERole mapRole(ERoleRegister roleRegister) {
        switch (roleRegister) {
            case MEGAUSER:
                return ERole.ROLE_MEGAUSER;
            case SUPERUSER:
                return ERole.ROLE_SUPERUSER;
            default:
                return null;
        }
    }
}
