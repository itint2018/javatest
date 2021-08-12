package ru.itintego.javatest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.itintego.javatest.controllers.filter.AuthorizationFilter;
import ru.itintego.javatest.repositories.UserRepository;
import ru.itintego.javatest.services.UserServiceDetailImpl;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;

    private final AuthorizationFilter authorizationFilter;

    public SecurityConfiguration(UserRepository userRepository, AuthorizationFilter authorizationFilter) {
        this.userRepository = userRepository;
        this.authorizationFilter = authorizationFilter;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
                .csrf().disable()
                .formLogin().disable()
                .logout().disable()
                .authorizeRequests()
                .antMatchers("/auth").permitAll()
                .antMatchers("/api/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/js/**", "/js/*", "/images/**", "/images/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserServiceDetailImpl(userRepository);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }


}
