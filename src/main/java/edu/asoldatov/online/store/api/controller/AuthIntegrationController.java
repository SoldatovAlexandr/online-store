package edu.asoldatov.online.store.api.controller;

import edu.asoldatov.online.store.api.dto.UserDto;
import edu.asoldatov.online.store.common.AuthType;
import edu.asoldatov.online.store.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class AuthIntegrationController {

    private final UserService userService;

    @Value("${frontend.host}")
    private String frontendUrl;

    @GetMapping("/")
    public void toHomePage(HttpServletResponse response) throws IOException {
        response.sendRedirect(frontendUrl + "/auth");
    }

    @GetMapping("/users/")
    public ResponseEntity<UserDto> getUser(Authentication authentication) {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        if (token == null) {
            return ResponseEntity.status(401).build();
        }
        AuthType authType = AuthType.getByTypeString(token.getAuthorizedClientRegistrationId());
        String userName = token.getPrincipal().getAttributes().get(authType.getNameKey()).toString();
        return ResponseEntity.ok(userService.find(userName));
    }
}
