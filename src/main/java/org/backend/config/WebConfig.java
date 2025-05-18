//package org.backend.config;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
//        try {
//            http
//                    .authorizeHttpRequests(auth -> auth
//                            .requestMatchers("/fusionX/**").authenticated() // Require authentication for /fusionX/** endpoints
//                            .anyRequest().permitAll() // Allow all other requests
//                    )
//                    .csrf(csrf -> csrf.disable()) // Disable CSRF for testing (enable with proper handling in production)
//                    .httpBasic(httpBasic -> httpBasic
//                            .realmName("MyAppRealm") // Optional: Define a realm name
//                            .authenticationEntryPoint((request, response, authException) -> {
//                                logger.error("Authentication failed: {}", authException.getMessage());
//                                response.sendError(401, "Unauthorized: Invalid credentials");
//                            })
//                    );
//
//            logger.info("Security configuration applied successfully");
//            return http.build();
//        } catch (Exception e) {
//            logger.error("Failed to configure security filter chain: {}", e.getMessage(), e);
//            throw new RuntimeException("Security configuration failed", e);
//        }
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        logger.info("Configuring in-memory user details service");
//        UserDetails user = User.withUsername("admin")
//                .password("{noop}password") // Use BCrypt in production
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }
//}

package org.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173","http://localhost:5174")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}