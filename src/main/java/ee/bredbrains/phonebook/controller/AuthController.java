package ee.bredbrains.phonebook.controller;

import ee.bredbrains.phonebook.model.User;
import ee.bredbrains.phonebook.model.UserDetailsImpl;
import ee.bredbrains.phonebook.model.payload.request.LoginRequest;
import ee.bredbrains.phonebook.model.payload.request.RegisterRequest;
import ee.bredbrains.phonebook.model.payload.response.JwtResponse;
import ee.bredbrains.phonebook.repository.UserRepository;
import ee.bredbrains.phonebook.utils.JwtUtils;
import ee.bredbrains.phonebook.utils.validator.RegisterValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository repository;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;

    public AuthController(UserRepository repository,
                          JwtUtils jwtUtils,
                          AuthenticationManager authenticationManager,
                          @Qualifier("passwordEncoder") PasswordEncoder encoder) {
        this.repository = repository;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.encoder = encoder;
    }

    @GetMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public JwtResponse login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail());
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
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
