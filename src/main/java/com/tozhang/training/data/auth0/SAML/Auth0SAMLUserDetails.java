package com.tozhang.training.data.auth0.SAML;

import org.opensaml.saml2.core.Attribute;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A default Implementation of {@link UserDetails} for Spring Boot Security SAML. This simple implementation hardly
 * covers all security aspects since it's mostly hardcoded. I.E. accounts are never locked, expired, or disabled, and
 * always eturn the same granted authority "ROLE_USER".
 * Consider implementing your own {@link UserDetails} and {@link SAMLUserDetailsService}.
 */
public class Auth0SAMLUserDetails implements UserDetails {

    private SAMLCredential samlCredential;

    public Auth0SAMLUserDetails(SAMLCredential samlCredential) {
        this.samlCredential = samlCredential;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return samlCredential.getNameID().getValue();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getAttribute(String name) {
        String value = samlCredential.getAttributeAsString(name);
        return value != null ? value : "";
    }

    public String[] getAttributeArray(String name) {
        return samlCredential.getAttributeAsStringArray(name);
    }

    public Map<String, String> getSampleAttributes() {
        List<String> names = new ArrayList<>();
        names.add("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier");
        names.add("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/upn");
        // names.add("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/emailaddress");
        names.add("http://schemas.auth0.com/email_verified");
        names.add("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/name");
        names.add("http://schemas.auth0.com/nickname");
        names.add("http://schemas.auth0.com/picture");
        Map<String, String> mappings = new HashMap<>();
        for (Attribute attribute : samlCredential.getAttributes()) {
            String name = attribute.getName();
            if (names.contains(name)) {
                mappings.put(name, samlCredential.getAttributeAsString(name));
            }
        }
        return mappings;
    }


    public Map<String, String> getAttributes() {
        return samlCredential.getAttributes().stream()
                .collect(Collectors.toMap(Attribute::getName, this::getValue));
    }

    public Map<String, String[]> getAttributesArrays() {
        return samlCredential.getAttributes().stream()
                .collect(Collectors.toMap(Attribute::getName, this::getValueArray));
    }

    private String getValue(Attribute attribute) {
        return getAttribute(attribute.getName());
    }

    private String[] getValueArray(Attribute attribute) {
        return getAttributeArray(attribute.getName());
    }
}
