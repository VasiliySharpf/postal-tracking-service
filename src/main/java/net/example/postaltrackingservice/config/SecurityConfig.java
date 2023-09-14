package net.example.postaltrackingservice.config;

import lombok.RequiredArgsConstructor;
import net.example.postaltrackingservice.jwt.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class SecurityConfig {

    @Value("${security.enabled}")
    private boolean securityEnabled;
    private final JwtTokenFilter jwtTokenFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .httpBasic(httpBasic -> httpBasic.disable());

        if (securityEnabled) {
            http.sessionManagement(sessionManagement -> sessionManagement
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(authorize -> authorize
                            .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll() // доступ к странице Swagger
                            .requestMatchers("/api/v1/auth/login").permitAll()
                            .anyRequest().authenticated())
                    .addFilterAfter(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                    .exceptionHandling(exceptionHandling ->
                            exceptionHandling.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));

        } else {
            http.authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/**").permitAll());
        }

        return http.build();

    }

}
