package com.example.PeelAndReveal_Project.Services;

import com.example.PeelAndReveal_Project.EntityBeans.Coupon;
import com.example.PeelAndReveal_Project.Exceptions.CouponNotExistException;
import com.example.PeelAndReveal_Project.Repositories.CouponRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicService {
    private final CouponRepository couponRepository;

    public PublicService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public List<Coupon> getAllCoupons(){
        return couponRepository.findAll();
    }

    public Coupon getCouponByID (int couponID) throws CouponNotExistException {
        return couponRepository.findById(couponID).orElseThrow(CouponNotExistException::new);
    }
}
