//package com.tozhang.training.data.auth0.SAML;
//
//import com.github.ulisesbocchio.spring.boot.security.saml.annotation.EnableSAMLSSO;
//import com.github.ulisesbocchio.spring.boot.security.saml.configurer.ServiceProviderBuilder;
//import com.github.ulisesbocchio.spring.boot.security.saml.configurer.ServiceProviderConfigurerAdapter;
//import org.opensaml.common.xml.SAMLConstants;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.saml.websso.WebSSOProfileOptions;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
////@SpringBootApplication
//@EnableSAMLSSO
//public class Auth0SSOSampleApplication {
//
////    public static void main(String[] args) {
////        SpringApplication.run(Auth0SSOSampleApplication.class, args);
////    }
//
//    @Configuration
//    public static class MvcConfig extends WebMvcConfigurerAdapter {
//
//        @Override
//        public void addViewControllers(ViewControllerRegistry registry) {
//            registry.addViewController("/").setViewName("index");
//            registry.addViewController("/protected").setViewName("protected");
//            registry.addViewController("/unprotected/help").setViewName("help");
//
//        }
//    }
//
//    @Configuration
//    public static class MyServiceProviderConfig extends ServiceProviderConfigurerAdapter {
//
//
//        @Override
//        public void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//            .antMatchers("/unprotected/**")
//            .permitAll()
//        .and()
//            .anonymous();
//        }
//
//
//        @Override
//        public void configure(ServiceProviderBuilder serviceProvider) {
//          WebSSOProfileOptions profileOptions = new WebSSOProfileOptions();
//          profileOptions.setBinding(SAMLConstants.SAML2_POST_BINDING_URI);
//          profileOptions.setBinding(SAMLConstants.SAML2_REDIRECT_BINDING_URI);
//            serviceProvider
//                .authenticationProvider()
//                .userDetailsService(new Auth0SAMLUserDetailsService())
//            .and()
//                .metadataGenerator()
//                .entityId("urn:tozhang66666")
//                .requestSigned(false)
//            .and()
//                .sso()
//                .profileOptions(profileOptions)
//                .defaultSuccessURL("/home")
//            .and()
//                .metadataManager()
//                .metadataLocations("classpath:auth0-metadata.xml")
//                .refreshCheckInterval(0);
//
//        }
//
//
//
//    }
//}
