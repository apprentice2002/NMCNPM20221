package com.cnpm;

import java.util.Set;

public final class UserSession {

    private static String username;
    private static Set<String> privileges;

    private UserSession(String username, Set<String> privileges) {
        UserSession.username = username;
        UserSession.privileges = privileges;
    }

    public static String getUsername() { return username; }
    public static Set<String> getPrivileges() { return privileges; }

    public static void setUsername(String username) {
        UserSession.username = username;
    }
    public static void setPrivileges(Set<String> privileges) {
        UserSession.privileges = privileges;
    }
}
