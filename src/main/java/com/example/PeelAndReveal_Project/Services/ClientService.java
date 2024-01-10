package com.example.PeelAndReveal_Project.Services;

import com.example.PeelAndReveal_Project.Exceptions.IdNotFoundException;
import com.example.PeelAndReveal_Project.Exceptions.LoginFailedException;
import com.example.PeelAndReveal_Project.Repositories.CompanyRepository;
import com.example.PeelAndReveal_Project.Repositories.CouponRepository;
import com.example.PeelAndReveal_Project.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class ClientService {
    @Autowired
    protected CompanyRepository companyRepository;
    @Autowired
    protected CouponRepository couponRepository;
    @Autowired
    protected CustomerRepository customerRepository;




    protected abstract boolean login(String email, String password) throws IdNotFoundException, LoginFailedException;

}
