package com.tozhang.training.data.repository;

import com.tozhang.training.data.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Guest,Long> {
    Guest findByEmailAddress(String emailAddress);
    Guest findByEmailAddressAndCountry(String email, String country);
    Guest findByFirstName(String Name);
}
