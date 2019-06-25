package com.tozhang.training.data.repository;

import com.tozhang.training.data.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {
    Admin findByEmailAddress(String emailAddress);
    Admin findByadminaccount(String adminAccount);
    List<Admin> findAll();
}
