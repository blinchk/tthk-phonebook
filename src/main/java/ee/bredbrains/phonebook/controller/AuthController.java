package ee.bredbrains.phonebook.controller;

import ee.bredbrains.phonebook.exception.auth.AuthorizationException;
import ee.bredbrains.phonebook.model.User;
import ee.bredbrains.phonebook.model.UserDetailsImpl;
import ee.bredbrains.phonebook.model.payload.request.RegisterRequest;
import ee.bredbrains.phonebook.model.payload.request.header.BasicAuthLogicCredentials;
import ee.bredbrains.phonebook.model.payload.response.JwtResponse;
import ee.bredbrains.phonebook.repository.UserRepository;
import ee.bredbrains.phonebook.utils.auth.BasicAuthUtils;
import ee.bredbrains.phonebook.utils.auth.JwtAuthUtils;
import ee.bredbrains.phonebook.utils.validator.RegisterValidator;
import org.apache.logging.log4j.util.Strings;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController()
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository repository;
    private final JwtAuthUtils jwtAuthUtils;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;

    public AuthController(UserRepository repository,
                          JwtAuthUtils jwtAuthUtils,
                          AuthenticationManager authenticationManager,
                          @Qualifier("passwordEncoder") PasswordEncoder encoder) {
        this.repository = repository;
        this.jwtAuthUtils = jwtAuthUtils;
        this.authenticationManager = authenticationManager;
        this.encoder = encoder;
    }

    @GetMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public JwtResponse login(HttpServletRequest request) {
        final String AUTHORIZATION_HEADER = "Authorization";
        String header = request.getHeader(AUTHORIZATION_HEADER);
        if (Strings.isEmpty(header)) {
            throw new AuthorizationException("Cannot authorize without Basic authentication.");
        }
        BasicAuthLogicCredentials credentials = BasicAuthUtils.decode(header);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtAuthUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail());
    }

    @PostMapping(value = "/register")
    public String register(@RequestBody RegisterRequest registerRequest) {
        new RegisterValidator(repository).validate(registerRequest);
        User user = new User(registerRequest.getUsername(),
                registerRequest.getEmail(),
                registerRequest.getFirstName(),
                registerRequest.getLastName(),
                encoder.encode(registerRequest.getPassword()));

        repository.save(user);

        return "User registered successfully.";
    }
}
