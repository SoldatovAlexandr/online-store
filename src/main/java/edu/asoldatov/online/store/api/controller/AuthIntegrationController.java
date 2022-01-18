package edu.asoldatov.online.store.api.controller;

import edu.asoldatov.online.store.api.dto.UserDto;
import edu.asoldatov.online.store.common.AuthType;
import edu.asoldatov.online.store.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AuthIntegrationController {

    private final UserService userService;

    @Value("${frontend.host}")
    private String frontendUrl;

    @Value("${domain}")
    private String domain;

    @GetMapping("/")
    public void toHomePage(@RequestHeader Map<String, String> headers,
                           HttpServletResponse response) throws IOException {
        log.info("Заголовки запроса [{}]", headers);
        String jsesid = headers.get("cookie").split("JSESSIONID=")[1];
        Cookie cookie = new Cookie("JSESSIONID", jsesid);
        cookie.setDomain(domain);
        response.addCookie(cookie);
        response.sendRedirect(frontendUrl + "/auth");
    }

    @GetMapping("/users/")
    public ResponseEntity<UserDto> getUser(
            @RequestHeader Map<String, String> headers,
            Authentication authentication) {
        log.info("Заголовки запроса [{}]", headers);
        log.info("Пользователь [{}]", authentication);

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;

        if (token == null) {
            log.error("Пользователь не авторизован");
            return ResponseEntity.status(401).build();
        }
        AuthType authType = AuthType.getByTypeString(token.getAuthorizedClientRegistrationId());
        String userName = token.getPrincipal().getAttributes().get(authType.getNameKey()).toString();
        return ResponseEntity.ok(userService.find(userName));
    }
}
