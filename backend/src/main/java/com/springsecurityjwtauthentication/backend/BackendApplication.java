package com.springsecurityjwtauthentication.backend;

import com.springsecurityjwtauthentication.backend.models.User;
import com.springsecurityjwtauthentication.backend.models.UserRole;
import com.springsecurityjwtauthentication.backend.repository.UserRepository;
import com.springsecurityjwtauthentication.backend.repository.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserRoleRepository userRoleRepository, UserRepository userRepository, PasswordEncoder passwordEncode){
		return args ->{
			if(userRoleRepository.findByAuthority("ADMIN").isPresent()) return;
			UserRole adminRole = userRoleRepository.save(new UserRole("ADMIN"));
			userRoleRepository.save(new UserRole("USER"));

			Set<UserRole> roles = new HashSet<>();
			roles.add(adminRole);

			User admin = new User(1L, "admin", passwordEncode.encode("password"), roles);

			userRepository.save(admin);
		};
	}
}
