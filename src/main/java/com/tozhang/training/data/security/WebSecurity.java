package com.tozhang.training.data.security;

import com.auth0.AuthenticationController;
import com.github.ulisesbocchio.spring.boot.security.saml.annotation.EnableSAMLSSO;
import com.github.ulisesbocchio.spring.boot.security.saml.configurer.ServiceProviderBuilder;
import com.github.ulisesbocchio.spring.boot.security.saml.configurer.ServiceProviderConfigurerAdapter;
import com.tozhang.training.data.auth0.SAML.Auth0SAMLUserDetailsService;
import com.tozhang.training.data.filters.TransactionFilter;
import com.tozhang.training.util.CustomAccessDeniedHandler;
import org.opensaml.common.xml.SAMLConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
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
import org.springframework.security.saml.websso.WebSSOProfileOptions;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import com.tozhang.training.data.auth0.LogoutController;

import java.io.UnsupportedEncodingException;

import static com.tozhang.training.data.security.SecurityConstants.*;
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity {

//    @Configuration
//    @EnableSAMLSSO
//    @Order(2)
//    public static class MyServiceProviderConfig extends ServiceProviderConfigurerAdapter {
//
//
//        @Override
//        public void configure(HttpSecurity http) throws Exception {
//            http.authorizeRequests()
//                    .antMatchers("/index/**")
//                    .permitAll()
//                    .and()
//                    .anonymous();
//        }


//        @Override
//        public void configure(ServiceProviderBuilder serviceProvider) throws Exception {
//            WebSSOProfileOptions profileOptions = new WebSSOProfileOptions();
//            profileOptions.setBinding(SAMLConstants.SAML2_POST_BINDING_URI);
//            profileOptions.setBinding(SAMLConstants.SAML2_REDIRECT_BINDING_URI);
//
//            serviceProvider
//                    .authenticationProvider()
//                    .userDetailsService(new Auth0SAMLUserDetailsService())
//                    .and()
//                    .metadataGenerator()
//                    .entityId("urn:tozhang66666")
//                    .requestSigned(false)
//                    .and()
//                    .sso()
//                    .profileOptions(profileOptions)
//                    .defaultSuccessURL("/portal/home")
//                    .and()
//                    .metadataManager()
//                    .metadataLocations("classpath:auth0-metadata.xml")
//                    .refreshCheckInterval(0).and()
//                    ;
//        }


        @Order(1)
        public class AppSecurity extends WebSecurityConfigurerAdapter {
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
            public void setTransactionFilter(TransactionFilter transactionFilter) {
                TransactionFilter.transactionFilter = transactionFilter;
            }


            public AppSecurity(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
                this.userDetailsService = userDetailsService;
                this.bCryptPasswordEncoder = bCryptPasswordEncoder;
            }

            @Override
            protected void configure(HttpSecurity http) throws Exception {
                HttpSecurity httpSecurity =
                        http.csrf().disable().antMatcher("/guest/signIn").antMatcher("/saml/SSO").authorizeRequests()
                                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
                                .antMatchers(SIGN_IN_URL).permitAll()
                                .antMatchers("/invalidToken").permitAll()
                                .antMatchers("/index").permitAll()
                                .antMatchers("/reservations").permitAll()
                                .antMatchers("/callback", "/login", "/portal/home", "/saml/SSO").permitAll()
                                .anyRequest().authenticated()
                                .and()
                                // todo We’ll start by implementing the org.springframework.web.filter.GenericFilterBean.
                                //  to customise filter, need to remove component.
//                                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
//                                .addFilterAfter(new JWTAuthorizationFilter(authenticationManager()), JWTAuthenticationFilter.class)
//                                .addFilterAfter(transactionFilter,JWTAuthenticationFilter.class)//this is used for development.
                                .exceptionHandling()
//                                .authenticationEntryPoint(new MyAuthenticationEntryPoint())
                                .accessDeniedPage("/error").and();

                http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
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

    }
