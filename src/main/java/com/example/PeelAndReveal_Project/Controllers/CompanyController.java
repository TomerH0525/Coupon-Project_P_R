package com.example.PeelAndReveal_Project.Controllers;

import com.example.PeelAndReveal_Project.EntityBeans.Company;
import com.example.PeelAndReveal_Project.EntityBeans.Coupon;
import com.example.PeelAndReveal_Project.Exceptions.*;
import com.example.PeelAndReveal_Project.Services.ClientService;
import com.example.PeelAndReveal_Project.Services.CompanyService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController()
public class CompanyController {

//    LoginManager loginManager;
    private HttpServletRequest request;
    private HashMap<String, ClientService> sessionStore;

    public CompanyController(HttpServletRequest request, HashMap<String, ClientService> sessionStore) {
        this.request = request;
        this.sessionStore = sessionStore;
    }

    @PostMapping("comapny/coupon/add")
    public ResponseEntity<Integer> addCoupon(@RequestBody Coupon coupon) throws CouponTitleAlreadyExistsException, IdNotFoundException, CouponDateException, CouponAmountException, CouponPriceException {
        System.out.println(coupon);
//        int newCouponID = companyService.addCoupon(coupon);
//        return ResponseEntity.status(HttpStatus.CREATED).body(newCouponID);
        return ResponseEntity.ok(1);
    }

    @GetMapping("comapny")
    public ResponseEntity<Company> getCompanyDetails() throws IdNotFoundException {
        return ResponseEntity.ok(getService().getCompanyDetails());

    }

    private CompanyService getService(){
        String token = request.getHeader("Authorization");
        CompanyService company = (CompanyService) sessionStore.get(token);
        return company;
    }
}
