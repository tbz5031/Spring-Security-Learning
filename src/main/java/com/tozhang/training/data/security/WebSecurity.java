package com.tozhang.training.data.security;

import com.tozhang.training.data.filters.TransactionFilter;
import com.tozhang.training.util.CustomAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.tozhang.training.data.security.SecurityConstants.*;
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private TransactionFilter transactionFilter;

    @Autowired
    public void setTransactionFilter(TransactionFilter transactionFilter ) {
        TransactionFilter.transactionFilter = transactionFilter;
    }


    public WebSecurity(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //todo impletment filter chain on 3 filters.
//        HttpSecurity httpSecurity =
//                http.csrf().disable().authorizeRequests()
//                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
//                .antMatchers(HttpMethod.POST, SIGN_IN_URL).permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
//                        .addFilterAfter(new JWTAuthorizationFilter(authenticationManager()),JWTAuthenticationFilter.class)
//                        .addFilterBefore(transactionFilter,JWTAuthenticationFilter.class)
//                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
//                .and();


        HttpSecurity httpSecurity =
                http.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
                .antMatchers(HttpMethod.POST, SIGN_IN_URL).permitAll()
                .antMatchers( "/error").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                        .addFilterAfter(new JWTAuthorizationFilter(authenticationManager()),JWTAuthenticationFilter.class)
                        .addFilterBefore(transactionFilter,JWTAuthenticationFilter.class)//this is used for development.
                .exceptionHandling().accessDeniedPage("/error")
                .and();

////                .addFilterBefore(new TransactionFilter(), BasicAuthenticationFilter.class)
////                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
////                .addFilter(new JWTAuthorizationFilter(authenticationManager()));

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    CustomAccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }
}