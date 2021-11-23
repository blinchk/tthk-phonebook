package ee.bredbrains.phonebook.filter;

import ee.bredbrains.phonebook.service.UserDetailsServiceImpl;
import ee.bredbrains.phonebook.utils.auth.JwtAuthUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {
    private final JwtAuthUtils jwtAuthUtils;
    private final UserDetailsServiceImpl userDetailsService;

    public AuthTokenFilter(JwtAuthUtils jwtAuthUtils, UserDetailsServiceImpl userDetailsService) {
        this.jwtAuthUtils = jwtAuthUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtAuthUtils.validateJwtToken(jwt)) {
                String username = jwtAuthUtils.getUsernameFromJwtToken(jwt);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                        null,
                        userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String AUTHORIZATION_HEADER_KEY = "Authorization";
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER_KEY);
        boolean isHeaderHasText = Strings.isNotBlank(authorizationHeader) && Strings.isNotEmpty(authorizationHeader);
        if (isHeaderHasText && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }

        return null;
    }
}
