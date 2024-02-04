package com.joe.racthk.repo;

import com.joe.racthk.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {

    // Add any custom repository methods if needed
    /*List<Email> findByStatus(String status);*/


}
