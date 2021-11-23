package ee.bredbrains.phonebook.utils.auth;

import ee.bredbrains.phonebook.exception.auth.AuthenticationException;
import ee.bredbrains.phonebook.model.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtAuthUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthUtils.class);

    @Value("${bredbrains.phonebook.jwtSecret}")
    private String jwtSecret;
    @Value("${bredbrains.phonebook.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        String username = userPrincipal.getUsername();
        Date now = new Date();
        Date expiration = new Date((new Date()).getTime() + jwtExpirationMs);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token);

            return true;
        } catch (SignatureException e) {
            throw new AuthenticationException(String.format("Invalid JWT signature: %s", e.getMessage()));
        } catch (MalformedJwtException e) {
            throw new AuthenticationException(String.format("Invalid JWT token: %s", e.getMessage()));
        } catch (ExpiredJwtException e) {
            throw new AuthenticationException(String.format("JWT token is expired: %s", e.getMessage()));
        } catch (UnsupportedJwtException e) {
            throw new AuthenticationException(String.format("JWT token is unsupported: %s", e.getMessage()));
        } catch (IllegalArgumentException e) {
            throw new AuthenticationException(String.format("JWT claims string is empty: %s", e.getMessage()));
        }
    }
}
