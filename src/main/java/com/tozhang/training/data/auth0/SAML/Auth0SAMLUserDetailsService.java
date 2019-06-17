package com.tozhang.training.data.auth0.SAML;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;

/**
 * Consider implementing your own {@link UserDetailsService} to check user permissions against a persistent storage and
 * load your own {@link UserDetails} implementation.
 */
public class Auth0SAMLUserDetailsService implements SAMLUserDetailsService {
    @Override
    public Object loadUserBySAML(SAMLCredential credential) throws UsernameNotFoundException {
        return new Auth0SAMLUserDetails(credential);
    }
}

