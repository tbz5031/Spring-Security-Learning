package com.tozhang.training.data.security;

import com.auth0.AuthenticationController;
import com.tozhang.training.data.filters.TransactionFilter;
import com.tozhang.training.util.CustomAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import com.tozhang.training.data.mvc.LogoutController;

import java.io.UnsupportedEncodingException;

import static com.tozhang.training.data.security.SecurityConstants.*;
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {

    /**
     * This is your auth0 domain (tenant you have created when registering with auth0 - account name)
     */
    @Value(value = "${com.auth0.domain}")
    private String domain;

    /**
     * This is the client id of your auth0 application (see Settings page on auth0 dashboard)
     */
    @Value(value = "${com.auth0.clientId}")
    private String clientId;

    /**
     * This is the client secret of your auth0 application (see Settings page on auth0 dashboard)
     */
    @Value(value = "${com.auth0.clientSecret}")
    private String clientSecret;

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver
                = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new LogoutController();
    }

    @Bean
    public AuthenticationController authenticationController() throws UnsupportedEncodingException {
        return AuthenticationController.newBuilder(domain, clientId, clientSecret)
                .build();
    }



    /* todo need to combine configure together with previous one */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//
//        http
//                .authorizeRequests()
//                .antMatchers("/callback", "/login").permitAll()
//                .antMatchers("/**").authenticated()
//                .and()
//                .logout().logoutSuccessHandler(logoutSuccessHandler()).permitAll();
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
//    }

    public String getDomain() {
        return domain;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

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
                .antMatchers(SIGN_IN_URL).permitAll()
                .antMatchers( "/invalidToken").permitAll()
                        .antMatchers("/callback", "/login","/portal/home").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                        .addFilterAfter(new JWTAuthorizationFilter(authenticationManager()),JWTAuthenticationFilter.class)
                        //.addFilterBefore(transactionFilter,JWTAuthenticationFilter.class)//this is used for development.
                .exceptionHandling().accessDeniedPage("/error").and();
//        http
//                .authorizeRequests()
//                .antMatchers("/callback", "/login").permitAll()
//                .antMatchers("/**").authenticated()
//                .and()
//                .logout().logoutSuccessHandler(logoutSuccessHandler()).permitAll();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);

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