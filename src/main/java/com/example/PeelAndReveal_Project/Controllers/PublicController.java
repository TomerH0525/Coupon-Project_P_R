package com.example.PeelAndReveal_Project.Controllers;

import com.example.PeelAndReveal_Project.EntityBeans.Coupon;
import com.example.PeelAndReveal_Project.Exceptions.CouponNotExistException;
import com.example.PeelAndReveal_Project.Services.PublicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/coupons")
public class    PublicController {

    private final PublicService service;

    public PublicController(PublicService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Coupon>> getAllCoupons(){
        return ResponseEntity.ok(service.getAllCoupons());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coupon> getCouponByID(@PathVariable int couponID) throws CouponNotExistException {
        return ResponseEntity.ok(service.getCouponByID(couponID));
    }
}
