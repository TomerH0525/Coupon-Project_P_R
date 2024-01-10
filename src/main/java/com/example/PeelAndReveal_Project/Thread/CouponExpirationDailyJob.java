package com.example.PeelAndReveal_Project.Thread;

import com.example.PeelAndReveal_Project.EntityBeans.Coupon;
import com.example.PeelAndReveal_Project.Repositories.CouponRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class CouponExpirationDailyJob {

    CouponRepository couponRepository;

    private CouponExpirationDailyJob(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @PostConstruct //runs on startup of the program (in case the program was down and a day past don't want expired coupons to be on the site!!)
    public void onStartup() {
        System.out.println(LocalDateTime.now() + "  daily-job (on startup)|| '" + "starting to clean expired coupons" + "'");
        checkExpiredCoupons();
    }

    @Scheduled(cron = "@daily")//runs everyday at midnight
    public void onSchedule(){
        System.out.println(LocalDateTime.now() + "  daily-job (on schedule)|| '" + "starting to clean expired coupons" + "'");
        checkExpiredCoupons();
    }

    public void checkExpiredCoupons() { //method on how to check for expired coupons
        List<Coupon> expiredCoupons = couponRepository.findAll().stream().filter(c -> c.getEndDate().before(Date.valueOf(LocalDate.now()))).toList();
        if (!expiredCoupons.isEmpty())
            couponRepository.deleteAllInBatch(expiredCoupons);
    }


}
