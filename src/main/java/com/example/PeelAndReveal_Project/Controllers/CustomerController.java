package com.example.PeelAndReveal_Project.Controllers;

import com.example.PeelAndReveal_Project.EntityBeans.Customer;
import com.example.PeelAndReveal_Project.Exceptions.*;
import com.example.PeelAndReveal_Project.Services.ClientService;
import com.example.PeelAndReveal_Project.Services.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController()
@RequestMapping("/customer")
public class CustomerController {

    private final HttpServletRequest request;
    private final HashMap<String, ClientService> sessionStore;

    public CustomerController(HttpServletRequest request, HashMap<String, ClientService> sessionStore) {
        this.request = request;
        this.sessionStore = sessionStore;
    }

    @GetMapping("/details")
    public ResponseEntity<Customer> getCustomerDetails() throws LoginFailedException, IdNotFoundException {
        Customer thisCustomer = getService().getCustomerDetails();
        return ResponseEntity.ok(thisCustomer);
    }

    @PutMapping("/purchase/{couponId}")
    public ResponseEntity<String> customerPurchaseCoupon(@PathVariable int couponId) throws LoginFailedException, CouponAlreadyOwnedException, CouponAmountException, IdNotFoundException, CouponDateException {
        System.out.println("You got to the right place!");
        getService().purchaseCoupon(couponId);
        return ResponseEntity.ok("Purchased successfully");
    }

    private CustomerService getService() throws LoginFailedException {
        String token = request.getHeader("Authorization");
        CustomerService customer = (CustomerService) sessionStore.get(token);
        System.out.println("good");
        if (customer == null) {
            System.out.println("hyuston we have a bromlem");
            throw new LoginFailedException("Login information expired");
        } else return customer;

    }
}
