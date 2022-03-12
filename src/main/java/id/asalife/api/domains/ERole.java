package id.asalife.api.domains;

public enum ERole {
    ROLE_ADMIN(Constants.ADMIN),
    ROLE_USER(Constants.USER),

    ROLE_CUSTOMER(Constants.CUSTOMER),
    ROLE_WORKER(Constants.WORKER),
    ROLE_SUPERUSER(Constants.SUPERUSER),
    ROLE_MEGAUSER(Constants.MEGAUSER);

    ERole(String roleString) {
    }

    public static class Constants {
        public static final String ADMIN = "ROLE_ADMIN";
        public static final String USER = "ROLE_USER";

        public static final String CUSTOMER = "ROLE_CUSTOMER";
        public static final String WORKER = "ROLE_WORKER";
        public static final String SUPERUSER = "ROLE_SUPERUSER";
        public static final String MEGAUSER = "ROLE_MEGAUSER";
    }
}
