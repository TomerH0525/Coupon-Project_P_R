package com.example.PeelAndReveal_Project.Controllers;

import com.example.PeelAndReveal_Project.EntityBeans.Company;
import com.example.PeelAndReveal_Project.EntityBeans.Customer;
import com.example.PeelAndReveal_Project.Exceptions.*;
import com.example.PeelAndReveal_Project.Services.AdminService;
import com.example.PeelAndReveal_Project.Services.ClientService;
import com.example.PeelAndReveal_Project.Services.CompanyService;
import com.example.PeelAndReveal_Project.Services.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
public class AdminController {
    private final HttpServletRequest request;
    private final HashMap<String, ClientService> sessionStore;

    public AdminController(HttpServletRequest request, HashMap<String, ClientService> sessionStore) {
        this.request = request;
        this.sessionStore = sessionStore;
    }

    @GetMapping("company/{companyId}")
    public ResponseEntity<Company> getOneCompany(@PathVariable int companyId) throws IdNotFoundException, LoginFailedException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getService().getOneCompanyByID(companyId));
    }

    @GetMapping("companies")
    public ResponseEntity<List<Company>> getAllCompanies() throws LoginFailedException {
        System.out.println(LocalDateTime.now() + "   AdminController : Sent All companies from database!");
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(getService().getAllCompanies());
    }

    @PutMapping("company")
    public ResponseEntity<String> updateCompany(@RequestBody Company company) throws IdNotFoundException, NameExistsException, LoginFailedException, EmailExistsException {
        getService().updateCompany(company);
        System.out.println(LocalDateTime.now() + "   AdminController : Updated company with  id = "+company.getId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Successfully updated!");
    }

    @PostMapping("company")
    public ResponseEntity<Company> addCompany(@RequestBody Company company) throws EmailExistsException, NameExistsException, LoginFailedException {
        Company newCompany = getService().addCompany(company);
        System.out.println(LocalDateTime.now() + "   AdminController : Added new company! , company id = "+newCompany.getId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newCompany);
    }

    @DeleteMapping("company/{companyId}")
    public ResponseEntity<String> deleteCompany(@PathVariable  int companyId) throws IdNotFoundException, LoginFailedException {
        if (getService().deleteCompany(companyId)) {
            //used iterator instead of map.foreach() because of ConcurrentModificationException
            // that occurs when I try to delete a key (with the object) from the map while in foreach
            Iterator<Map.Entry<String, ClientService>> iterator = sessionStore.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, ClientService> entry = iterator.next();
                if (entry.getValue() instanceof CompanyService companyService) {
                    if (companyService.getCompanyID() == companyId) {
                        iterator.remove();
                        System.out.println(LocalDateTime.now() + "   AdminController : while removing company with id "+companyId+" found session token, deleting session token!");
                    }
                }
            }
            System.out.println(LocalDateTime.now() + "   AdminController : Deleted company with id = "+companyId);
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body("Deleted Successfully!");

        } else return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Company with the id " + companyId + " was not found (i guess its good?)");
    }

    @GetMapping("customers")
    public ResponseEntity<List<Customer>> getAllCustomers() throws LoginFailedException {
        System.out.println(LocalDateTime.now() + "   AdminController : Sent All customers from database!");
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(getService().getAllCustomers());
    }

    @PostMapping("customer")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) throws EmailExistsException, IdNotZeroException, LoginFailedException {
        Customer newCustomer = getService().addCustomer(customer);
        System.out.println(LocalDateTime.now() + "   AdminController : Added new customer! , new customer id = "+newCustomer.getCustomerID());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newCustomer);
    }

    @GetMapping("customer/{customerID}")
    public ResponseEntity<Customer> getOneCustomer(@PathVariable int customerID) throws IdNotFoundException, LoginFailedException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getService().getOneCustomerByID(customerID));
    }


    @PutMapping("customer")
    public ResponseEntity<String> updateCustomer(@RequestBody Customer customer) throws EmailExistsException, IdNotFoundException, LoginFailedException {
        getService().updateCustomer(customer);
        System.out.println(LocalDateTime.now() + "   AdminController : Updated customer with id = "+customer.getCustomerID());
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body("Updated successfully!");
    }

    @DeleteMapping("customer/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable int customerId) throws IdNotFoundException, LoginFailedException {
        if (getService().deleteCustomer(customerId)) {
            //used iterator instead of map.foreach() because of ConcurrentModificationException
            // that occurs when I try to delete a key (with the object) from the map while in foreach
            Iterator<Map.Entry<String, ClientService>> iterator = sessionStore.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, ClientService> entry = iterator.next();
                if (entry.getValue() instanceof CustomerService customerService) {
                    if (customerService.getCustomerID() == customerId) {
                        iterator.remove();
                        System.out.println(LocalDateTime.now() + "   AdminController : while deleting customer with id "+customerId+" found session token, deleting session token!");
                    }
                }
            }
            System.out.println(LocalDateTime.now() + "   AdminController : Deleted customer with id = "+customerId);
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body("Deleted Successfully!");

        } else return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Company with the id " + customerId + " was not found (i guess its good?)");
    }

    private AdminService getService() throws LoginFailedException {
        String token = request.getHeader("Authorization");
        AdminService admin = (AdminService) sessionStore.get(token);
        if (admin == null) {
            System.out.println(LocalDateTime.now() + "   AdminController : Failed to get AdminService!");
            throw new LoginFailedException("Login information expired");
        }else return admin;

    }
}


