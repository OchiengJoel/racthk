package com.joe.racthk.enums;

public enum Role {

    ADMIN("Admin"),
    EDITOR("Editor"),

    USER("User");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
