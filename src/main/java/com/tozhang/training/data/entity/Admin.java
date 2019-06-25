package com.tozhang.training.data.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name="ADMIN")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ADMIN_ID")
    private long id;
    @Column(name = "STATUS")
    private String status="inactive";
    @Column(name="FIRST_NAME")
    private String firstName;
    @Column(name="LAST_NAME")
    private String lastName;
    @Column(name ="ADMINACCOUNT",unique = true)
    private String adminaccount;
    @Column(name="EMAIL_ADDRESS",unique = true)
    private String emailAddress;
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
    @Column(name="isFirst")
    private Boolean isFirst = false;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getAdminAccount() {
        return adminaccount;
    }

    public void setAdminAccount(String adminAccount) {
        this.adminaccount = adminAccount;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
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


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Boolean getFirst() {
        return isFirst;
    }

    public void setFirst(Boolean first) {
        isFirst = first;
    }


}
