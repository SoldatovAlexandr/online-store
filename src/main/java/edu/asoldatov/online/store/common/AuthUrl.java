package edu.asoldatov.online.store.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthUrl {

    USERS("/api/users/", UserRoles.ROLE_USER),
    BASKETS("/api/baskets/", UserRoles.ROLE_USER);

    private final String url;
    private final UserRoles role;

    public static AuthUrl getByUrl(String url) {
        for (AuthUrl aut : values()) {
            if(url.startsWith(aut.url)){
                return aut;
            }
        }
        return null;
    }
}
