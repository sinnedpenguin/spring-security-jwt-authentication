package com.angularpsringbootecommerce.backend.services;

import com.angularpsringbootecommerce.backend.dtos.UserLoginDto;
import com.angularpsringbootecommerce.backend.models.User;
import com.angularpsringbootecommerce.backend.models.UserRole;
import com.angularpsringbootecommerce.backend.repository.UserRepository;
import com.angularpsringbootecommerce.backend.repository.UserRoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.AuthenticationException;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public User register(String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);

        UserRole userRole = userRoleRepository.findByAuthority("USER").get();
        Set<UserRole> authorities = new HashSet<>();

        authorities.add(userRole);

        return userRepository.save(new User(0L, email, encodedPassword, authorities));
    }

    public UserLoginDto login(String email, String password) {
        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            String token = tokenService.generateJwt(auth);

            return new UserLoginDto(userRepository.findByEmail(email).get(), token);

        } catch(AuthenticationException e){
            return new UserLoginDto(null, "");
        }
    }
}
