package com.r00t.cifs.configs;

import com.r00t.cifs.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    private UserService service;

    public AppSecurityConfig(UserService service) {
        this.service = service;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(service);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
//                .antMatchers("/").access("hasRole('USER')")
                .antMatchers("/create-user").access("hasRole('ADMIN')")
                .antMatchers("/login", "/").permitAll()
                .antMatchers("/*.js", "/*.css", "/*.jpg", "/*.png", "/*.gif").permitAll()
                .antMatchers("/content/**", "/css/**", "/image/**", "/js/**", "/plugins/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/")
                .and()
                .rememberMe().key("uniqueAndSecret").tokenValiditySeconds(86400).userDetailsService(service)
                .and()
                .logout().deleteCookies("JSESSIONID");
    }
}
