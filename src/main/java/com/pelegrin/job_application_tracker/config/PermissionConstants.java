package com.pelegrin.job_application_tracker.config;

import java.util.List;

public final class PermissionConstants {

    private PermissionConstants() {
        // Prevent instantiation
    }

    public static final String USER_READ = "USER_READ";
    public static final String USER_CREATE = "USER_CREATE";
    public static final String USER_UPDATE = "USER_UPDATE";
    public static final String USER_DELETE = "USER_DELETE";

    public static final String ROLE_READ = "ROLE_READ";
    public static final String ROLE_CREATE = "ROLE_CREATE";
    public static final String ROLE_UPDATE = "ROLE_UPDATE";
    public static final String ROLE_DELETE = "ROLE_DELETE";

    public static final String PERMISSION_READ = "PERMISSION_READ";
    public static final String PERMISSION_CREATE = "PERMISSION_CREATE";
    public static final String PERMISSION_UPDATE = "PERMISSION_UPDATE";
    public static final String PERMISSION_DELETE = "PERMISSION_DELETE";

    public static final String APPLICATION_READ = "APPLICATION_READ";
    public static final String APPLICATION_CREATE = "APPLICATION_CREATE";
    public static final String APPLICATION_UPDATE = "APPLICATION_UPDATE";
    public static final String APPLICATION_DELETE = "APPLICATION_DELETE";

    public static final String COMPANY_READ = "COMPANY_READ";
    public static final String COMPANY_CREATE = "COMPANY_CREATE";
    public static final String COMPANY_UPDATE = "COMPANY_UPDATE";
    public static final String COMPANY_DELETE = "COMPANY_DELETE";

    public static final List<String> ALL = List.of(
            USER_READ,
            USER_CREATE,
            USER_UPDATE,
            USER_DELETE,

            ROLE_READ,
            ROLE_CREATE,
            ROLE_UPDATE,
            ROLE_DELETE,

            PERMISSION_READ,
            PERMISSION_CREATE,
            PERMISSION_UPDATE,
            PERMISSION_DELETE,

            APPLICATION_READ,
            APPLICATION_CREATE,
            APPLICATION_UPDATE,
            APPLICATION_DELETE,

            COMPANY_READ,
            COMPANY_CREATE,
            COMPANY_UPDATE,
            COMPANY_DELETE
    );
}