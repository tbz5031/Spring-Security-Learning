package com.tozhang.training.data.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tozhang.training.data.entity.Guest;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface GuestRepository extends JpaRepository<Guest,Long> {
    Guest findByEmailAddress(String emailAddress);
    Guest findByEmailAddressAndCountry(String email, String country);
    Guest findByFirstName(String Name);
}
//,JpaRepository<Guest,Long>
