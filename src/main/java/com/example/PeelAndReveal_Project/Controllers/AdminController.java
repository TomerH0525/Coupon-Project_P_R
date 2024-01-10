package com.example.PeelAndReveal_Project.Controllers;

import com.example.PeelAndReveal_Project.EntityBeans.Company;
import com.example.PeelAndReveal_Project.EntityBeans.Customer;
import com.example.PeelAndReveal_Project.Exceptions.EmailExistsException;
import com.example.PeelAndReveal_Project.Exceptions.IdNotFoundException;
import com.example.PeelAndReveal_Project.Exceptions.IdNotZeroException;
import com.example.PeelAndReveal_Project.Exceptions.NameExistsException;
import com.example.PeelAndReveal_Project.Services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {
    @Autowired
    AdminService admin;

    @GetMapping("company")
    public ResponseEntity<Company> getOneCompany(int companyID) throws IdNotFoundException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(admin.getOneCompanyByID(companyID));
    }

    @GetMapping("companies")
    public ResponseEntity<List<Company>> getAllCompanies() {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(admin.getAllCompanies());
    }

    @PutMapping("comapny")
    public ResponseEntity<String> updateCompany(@RequestBody Company company) throws IdNotFoundException, NameExistsException {
        admin.updateCompany(company);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Successfully updated!");
    }

    @PostMapping("company")
    public ResponseEntity<Company> addCompany(@RequestBody Company company) throws EmailExistsException, NameExistsException {
        Company newCompany = admin.addCompany(company);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newCompany);
    }

    @DeleteMapping("company")
    public ResponseEntity<String> deleteCompany(int companyID) throws IdNotFoundException {
        if (admin.deleteCompany(companyID)) {
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body("Deleted Successfully!");

        } else return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Company with the id " + companyID + " was not found (i guess its good?)");
    }

    @GetMapping("customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(admin.getAllCustomers());
    }

    @PostMapping("customer")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) throws EmailExistsException, IdNotZeroException {
        Customer newCustomer = admin.addCustomer(customer);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newCustomer);
    }

    @GetMapping("customer")
    public ResponseEntity<Customer> getOneCustomer(int customerID) throws IdNotFoundException {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(admin.getOneCustomerByID(customerID));
    }


    @PutMapping("customer")
    public ResponseEntity<String> updateCustomer(@RequestBody Customer customer) throws EmailExistsException, IdNotFoundException {
        admin.updateCustomer(customer);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body("Updated successfully!");
    }

    @DeleteMapping("customer")
    public ResponseEntity<String> deleteCustomer(int customerID) throws IdNotFoundException {
        if (admin.deleteCustomer(customerID)) {
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body("Deleted Successfully!");

        } else return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Company with the id " + customerID + " was not found (i guess its good?)");
    }

//    private CompanyService getService(){
//        String token = request.getHeader
//
//    }
}


