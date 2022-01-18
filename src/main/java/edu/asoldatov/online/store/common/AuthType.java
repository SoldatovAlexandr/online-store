package edu.asoldatov.online.store.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthType {

    GOOGLE("google", "email"),
    GITHUB("github", "login");

    private final String type;
    private final String nameKey;

    public static AuthType getByTypeString(String type) {
        for (AuthType authType : values()) {
            if (authType.getType().equals(type)) {
                return authType;
            }
        }
        throw new RuntimeException("Type not found");
    }
}
