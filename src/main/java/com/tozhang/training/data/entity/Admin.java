package com.tozhang.training.data.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    @Column(name ="ACCOUNT",unique = true)
    private String account;
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
    @Column(name="deviceId")
    private String deviceId;

}
