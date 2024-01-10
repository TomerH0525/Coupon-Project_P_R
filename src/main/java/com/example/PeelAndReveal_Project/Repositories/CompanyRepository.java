package com.example.PeelAndReveal_Project.Repositories;

import com.example.PeelAndReveal_Project.EntityBeans.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Integer> {

    public Optional<Company> findByEmail(String email);

    public boolean existsByEmail(String email);

    public boolean existsByName(String name);


}
