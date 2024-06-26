package com.helloIftekhar.springJwt.service;

import com.helloIftekhar.springJwt.DES;
import com.helloIftekhar.springJwt.model.AuthenticationResponse;
import com.helloIftekhar.springJwt.model.Token;
import com.helloIftekhar.springJwt.model.User;
import com.helloIftekhar.springJwt.repository.TokenRepository;
import com.helloIftekhar.springJwt.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository repository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            TokenRepository tokenRepository,
            AuthenticationManager authenticationManager
    ) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(User request) {
// ali ********
        if(repository.findByUsername(   DES.tripleDESEncrypt(request.getUsername())).isPresent()) {
            return new AuthenticationResponse(null, "User already exist");
        }
        User user = new User();
        //encrypt
        user.setFirstName(DES.tripleDESEncrypt(request.getFirstName()));
        user.setLastName(DES.tripleDESEncrypt(request.getLastName()));
        user.setUsername(DES.tripleDESEncrypt(request.getUsername()));
        //hash
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user = repository.save(user);
        String jwt = jwtService.generateToken(user);
        saveUserToken(jwt, user);
        return new AuthenticationResponse(jwt, "User registration was successful",user.getUsername(),user.getId(),user.getRole());
    }

    public AuthenticationResponse authenticate(User request) {
        request.setUsername( DES.tripleDESEncrypt(request.getUsername()) );
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        User user = repository.findByUsername(request.getUsername()).orElseThrow();
        String jwt = jwtService.generateToken(user);
        revokeAllTokenByUser(user);
        saveUserToken(jwt, user);
        String userName = DES.tripleDESDecrypt(user.getUsername());
        return new AuthenticationResponse(jwt, "User login was successful",userName,user.getId(),user.getRole());
    }
    private void revokeAllTokenByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllTokensByUser(user.getId());
        if(validTokens.isEmpty()) {
            return;
        }
        validTokens.forEach(t-> {
            t.setLoggedOut(true);
        });
        tokenRepository.saveAll(validTokens);
    }
    private void saveUserToken(String jwt, User user) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }
}
