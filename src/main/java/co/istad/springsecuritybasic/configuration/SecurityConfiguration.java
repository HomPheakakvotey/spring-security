package co.istad.springsecuritybasic.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filter (HttpSecurity http) throws Exception{
        return http.authorizeHttpRequests(
                (auth) -> auth.requestMatchers("/login","/register")
                        .permitAll()
                        .requestMatchers("api/v1/admins/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"api/v1/articles/**","/api/v1/...").hasAnyRole("USER","ADMIN","AUTHOR")
                        .requestMatchers("api/v1/articles/**").hasRole("AUTHOR")
                        .anyRequest()
                        .authenticated()
        )
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user1 = User.builder()
                .username("mr.Admin")
                .password(passwordEncoder().encode("12345"))
                .roles("ADMIN")
                .build();
        UserDetails user2 = User.builder()
                .username("mr.normal")
                .password(passwordEncoder().encode("konoyaro"))
                .roles("USER")
                .build();
        UserDetails user3 = User.builder()
                .username("mr.author")
                .password(passwordEncoder().encode("authorr"))
                .roles("AUTHOR")
                .build();

        return new InMemoryUserDetailsManager(user1, user2, user3);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


//    optional : Spring alr provide DaoAuthentication, but if u want to customization provider do it this way
    @Bean
    public DaoAuthenticationProvider authProvider(UserDetailsService userDetailsService){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userDetailsService);

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration
                                                       configurer) throws Exception{
        return configurer.getAuthenticationManager();
    }

}
