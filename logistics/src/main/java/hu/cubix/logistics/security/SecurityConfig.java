package hu.cubix.logistics.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails addressMgr = User.builder()
            .username("addressMgr")
            .password(passwordEncoder.encode("sultkrumpli"))
            .authorities("AddressManager")
            .build();
        UserDetails transportMgr = User.builder()
            .username("transportMgr")
            .password(passwordEncoder.encode("rantotthus"))
            .authorities("TransportManager")
            .build();

        return new InMemoryUserDetailsManager(addressMgr, transportMgr);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/api/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/addresses").hasAuthority("AddressManager")
                .requestMatchers(HttpMethod.PUT, "/api/addresses/**").hasAuthority("AddressManager")
                .requestMatchers(HttpMethod.DELETE, "/api/addresses/**").hasAuthority("AddressManager")
                .requestMatchers(HttpMethod.POST, "/api/transportPlans/**").hasAuthority("TransportManager")
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
