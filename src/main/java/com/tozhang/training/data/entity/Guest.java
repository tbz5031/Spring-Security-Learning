package com.tozhang.training.data.entity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
@Entity
@Table(name="GUEST")
@EntityListeners(AuditingEntityListener.class)
public class Guest{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="GUEST_ID")
    private long id;
    @Column(name = "STATUS")
    private String status="inactive";
    @Column(name="FIRST_NAME")
    private String firstName;
    @Column(name="LAST_NAME")
    private String lastName;
    @Column(name ="ACCOUNT",unique = true)
    private String account;
    @Column(name="EMAIL_ADDRESS",unique = true)
    private String emailAddress;
    @Column(name = "refreshToken")
    private String refreshToken;
    @Column(name="ADDRESS")
    private String address;
    @Column(name="COUNTRY")
    private String country;
    @Column(name="STATE")
    private String state;
    @Column(name="PHONE_NUMBER")
    private String phoneNumber;
    @Column(name="password")
    private String password;
    @Column(name="LoginTs")
    private Long loginTs;
    @Column(name="CreatedTs")
    private Long createdTs;
    @Column(name="logoutTs")
    private Long logoutTs;
    @Column(name="deviceId")
    private String deviceId;

    // JavaBeans Pattern - allows inconsistency, mandates mutability

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getLoginTs() {
        return loginTs;
    }

    public void setLoginTs(Long loginTs) {
        this.loginTs = loginTs;
    }

    public Long getCreatedTs() {
        return createdTs;
    }

    public void setCreatedTs(Long createdTs) {
        this.createdTs = createdTs;
    }

    public Long getLogoutTs() {
        return logoutTs;
    }

    public void setLogoutTs(Long logoutTs) {
        this.logoutTs = logoutTs;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }


    // Builder pattern
    public static class Builder {
        private final String account;
        private final String emailAddress;

        // Optional parameters - initialized to default values
        private String status="inactive";
        private String firstName;
        private String lastName;
        private String refreshToken;
        private String address;
        private String country;
        private String state;
        private String phoneNumber;
        private String password;
        private Long loginTs;
        private Long createdTs;
        private Long logoutTs;
        private String deviceId;

        public Builder(String emailAddress, String account) {
            this.emailAddress = emailAddress;
            this.account= account;
        }

        public Builder firstName(String val) { firstName = val; return this; }
        public Builder status(String val) { firstName = val; return this; }
        public Builder lastName(String val) { lastName = val; return this; }
        public Builder refreshToken(String val) { refreshToken = val; return this; }
        public Builder address(String val) { address = val; return this; }
        public Builder country(String val) { country = val; return this; }
        public Builder state(String val) { state = val; return this; }
        public Builder phoneNumber(String val) { phoneNumber = val; return this; }
        public Builder password(String val) { password = val; return this; }
        public Builder loginTs(Long val) { loginTs = val; return this; }
        public Builder createdTs(Long val) { createdTs = val; return this; }
        public Builder logoutTs(Long val) { logoutTs = val; return this; }
        public Builder deviceId(String val) { deviceId = val; return this; }

        public Guest build() {
            return new Guest(this);
        }
    }

    private Guest(Builder builder) {
        firstName  = builder.firstName;
        lastName  = builder.lastName;
        refreshToken  = builder.refreshToken;
        address  = builder.address;
        country = builder.country;
        state = builder.state;
        phoneNumber = builder.phoneNumber;
        password = builder.password;
        loginTs = builder.loginTs;
        createdTs = builder.createdTs;
        logoutTs = builder.logoutTs;
        deviceId = builder.deviceId;
        status = builder.status;
    }





}
