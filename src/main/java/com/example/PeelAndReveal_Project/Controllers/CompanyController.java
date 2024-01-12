package com.example.PeelAndReveal_Project.Controllers;

import com.example.PeelAndReveal_Project.EntityBeans.Company;
import com.example.PeelAndReveal_Project.EntityBeans.Coupon;
import com.example.PeelAndReveal_Project.EntityBeans.Enum.Category;
import com.example.PeelAndReveal_Project.Exceptions.*;
import com.example.PeelAndReveal_Project.Services.ClientService;
import com.example.PeelAndReveal_Project.Services.CompanyService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController()
@RequestMapping("/company")
public class CompanyController {

//    LoginManager loginManager;
    private final HttpServletRequest request;
    private final HashMap<String, ClientService> sessionStore;

    public CompanyController(HttpServletRequest request, HashMap<String, ClientService> sessionStore) {
        this.request = request;
        this.sessionStore = sessionStore;
    }

    @GetMapping("details")
    public ResponseEntity<Company> getCompanyDetails() throws IdNotFoundException, LoginFailedException {
        return ResponseEntity.ok(getService().getCompanyDetails());

    }

    @PostMapping("/coupon/add")
    public ResponseEntity<Integer> addCoupon(@RequestBody Coupon coupon) throws CouponTitleAlreadyExistsException, IdNotFoundException, CouponDateException, CouponAmountException, CouponPriceException, LoginFailedException {
        System.out.println(coupon);
        int newCouponID = getService().addCoupon(coupon);
        return ResponseEntity.ok(newCouponID);
    }

    @PutMapping("/coupon_"+"{id}/update")
    public ResponseEntity<Coupon> updateCoupon(@RequestBody Coupon coupon, @PathVariable("id") int couponID) throws LoginFailedException, CouponTitleAlreadyExistsException, IdNotFoundException, CouponAmountException, CouponPriceException, CouponDateException {
        return ResponseEntity.accepted().body(getService().updateCoupon(coupon));
    }

    @DeleteMapping("/coupon/{id}/delete")
    public ResponseEntity<String> deleteCoupon(@PathVariable int couponID) throws LoginFailedException, CouponNotExistException, IdNotFoundException {
        getService().deleteCoupon(couponID);
        return ResponseEntity.accepted().body("Coupon Deleted Successfully!");
    }

    @GetMapping("/coupon/{id}")
    public ResponseEntity<Coupon> getCouponByID(@PathVariable int couponID) throws LoginFailedException, CouponNotExistException, IdNotFoundException {
        return ResponseEntity.ok(getService().getCompanyCouponById(couponID));
    }

    @GetMapping("/coupons/filter/title-{filter}")
    public ResponseEntity<List<Coupon>> getAllCouponsByTitle(@PathVariable("filter") String userInput) throws LoginFailedException, IdNotFoundException {
        return ResponseEntity.ok(getService().getCompanyCouponsByTitle(userInput));
    }

    @GetMapping("/coupon/filter/maxPrice-{price}")
    public ResponseEntity<List<Coupon>> getAllCouponsByMaxPrice(@PathVariable("price")double maxPrice) throws LoginFailedException, IdNotFoundException {
        return ResponseEntity.ok(getService().getAllCompanyCoupons(maxPrice));
    }

    @GetMapping("/coupon/filter/category-{category}")
    public ResponseEntity<List<Coupon>> getAllCouponsByCategory(@PathVariable("category") Category category) throws LoginFailedException, IdNotFoundException {
        return ResponseEntity.ok(getService().getAllCompanyCoupons(category));
    }







    private CompanyService getService() throws LoginFailedException {
        String token = request.getHeader("Authorization");
        CompanyService company = (CompanyService) sessionStore.get(token);
        if(company == null)
            throw new LoginFailedException();
        else return company;

    }
}
