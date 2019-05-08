package com.tozhang.training.data.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="GUEST")
@EntityListeners(AuditingEntityListener.class)
public class Guest {
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
    private Timestamp loginTs;
    @Column(name="CreatedTs")
    private Timestamp createdTs;
    @Column(name="logoutTs")
    private Timestamp logoutTs;

    public String getAccount() {
        return account;
    }

    public void setAccount(String firstName) {
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

    public Timestamp getLoginTs() {
        return loginTs;
    }

    public void setLoginTs(Timestamp loginTs) {
        this.loginTs = loginTs;
    }

    public Timestamp getCreatedTs() {
        return createdTs;
    }

    public void setCreatedTs(Timestamp createdTs) {
        this.createdTs = createdTs;
    }

    public Timestamp getLogoutTs() {
        return logoutTs;
    }

    public void setLogoutTs(Timestamp logoutTs) {
        this.logoutTs = logoutTs;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
