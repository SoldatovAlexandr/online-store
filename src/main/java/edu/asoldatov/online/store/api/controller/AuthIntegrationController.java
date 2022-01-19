package edu.asoldatov.online.store.api.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.asoldatov.online.store.common.AuthType;
import edu.asoldatov.online.store.mogel.User;
import edu.asoldatov.online.store.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AuthIntegrationController {

    private final UserService userService;
    private final ObjectMapper objectMapper;

    @Value("${frontend.host}")
    private String frontendUrl;
    private final String secretKey = "secret";
    private final Map<Long, Map<String, String>> auth = new HashMap<>();


    @GetMapping("/")
    public void toHomePage(HttpServletRequest request,
                           HttpServletResponse response,
                           Authentication authentication) throws IOException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        if (token == null) {
            log.error("Пользователь не авторизован");
            response.setStatus(401);
        } else {
            AuthType authType = AuthType.getByTypeString(token.getAuthorizedClientRegistrationId());
            String userName = token.getPrincipal().getAttributes().get(authType.getNameKey()).toString();
            Optional<User> optional = userService.findByLoginAndAuthType(userName, authType);
            if(optional.isPresent()) {
                User user = optional.get();
                successfulAuthentication(request, user);
                response.sendRedirect(frontendUrl + "/auth/" + user.getId());
            }else {
                response.setStatus(403);
                response.sendRedirect(frontendUrl + "/error/");
            }
        }
    }

    @GetMapping("/token/{id}")
    public void getToken(@PathVariable Long id, HttpServletResponse response) {
        try {
            response.setContentType(APPLICATION_JSON_VALUE);
            objectMapper.writeValue(response.getOutputStream(), auth.get(id));
            response.setHeader("Accept", APPLICATION_JSON_VALUE);
        } catch (Exception e) {
            response.setStatus(401);
        }
    }

    private void successfulAuthentication(HttpServletRequest request, User user) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
        String accessToken = JWT.create()
                .withSubject(user.getLogin())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("id", user.getId())
                .withClaim("authType", user.getAuthType().getType())
                .withClaim("roles", user.getRoles().stream().map(v -> v.getName().name()).collect(Collectors.toList()))
                .sign(algorithm);
        String refreshToken = JWT.create()
                .withSubject(user.getLogin())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
        Map<String, String> tokens = Map.of("accessToken", accessToken, "refreshToken", refreshToken);
        auth.put(user.getId(), tokens);
    }
}
