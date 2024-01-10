package com.example.PeelAndReveal_Project.Controllers;

import com.example.PeelAndReveal_Project.Services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("coupon")
public class CouponController {
    @Autowired
    CompanyService companyService;


}
