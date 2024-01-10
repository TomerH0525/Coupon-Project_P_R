package com.example.PeelAndReveal_Project.Repositories;

import com.example.PeelAndReveal_Project.EntityBeans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    public Optional<Customer> findByEmail(String email);

    public boolean existsByEmail(String email);

}