package com.inLine.SecurityAndJWT.Security;

import com.inLine.service.AccountService;
import com.inLine.SecurityAndJWT.jwt.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.crypto.SecretKey;

@CrossOrigin(origins = "http://localhost:8080")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AccountService applicationUserService;

    private final SecretKey secretKey;
    private final PasswordEncoder passwordEncoder;


    private UserDetailsService jwtUserDetailsService;

    private JwtRequestFilter jwtRequestFilter;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public SecurityConfig(AccountService applicationUserService, SecretKey secretKey, PasswordEncoder passwordEncoder, UserDetailsService jwtUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.applicationUserService = applicationUserService;

        this.secretKey = secretKey;
        this.passwordEncoder = passwordEncoder;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // Add a filter to validate the tokens with every request
                .addFilterAfter(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/", "/index","/*.js", "/*.js.*", "/css/*", "/js/*").permitAll()
                .antMatchers("/v3/api-docs/**", "/api-docs", "/api", "/api/**", "/api.*", "swagger.json", "/swagger-ui.html", "/swagger-ui/**", "/swagger-config", "/configuration/ui", "/swagger-resources/**", "/swagger-resources", "/configuration/security", "/webjars/**").permitAll()//OpenAPI
                .antMatchers("/login/submit", "/register/submit", "/get/all").permitAll()//account
                .antMatchers("/stores", "/stores/*").permitAll()//stores
                .antMatchers("/admin/stores").hasRole("ADMIN")
                //.antMatchers("").permitAll()
                //.antMatchers("").hasRole("USER")
                .anyRequest()
                .authenticated();
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }
}
