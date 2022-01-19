package edu.asoldatov.online.store.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.asoldatov.online.store.common.AuthUrl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    public final String TOKEN_NAME = "Bearer";
    public final String SECRET_KEY = "secret";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        AuthUrl authUrl = AuthUrl.getByUrl(request.getServletPath());
        if (authUrl != null) {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_NAME)) {
                response.setStatus(401);
            } else {
                try {
                    String token = StringUtils.deleteWhitespace(authorizationHeader.substring(TOKEN_NAME.length()));
                    Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    List<String> roles = Arrays.asList(decodedJWT.getClaim("roles").asArray(String.class));
                    if (!roles.contains(authUrl.getRole().name())) {
                        response.setStatus(403);
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                    response.setHeader("error", e.getMessage());
                    response.setStatus(FORBIDDEN.value());
                    Map<String, String> errors = Map.of("error_message", e.getMessage());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), errors);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
