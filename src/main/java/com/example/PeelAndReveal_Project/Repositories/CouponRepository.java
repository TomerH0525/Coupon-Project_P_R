package com.example.PeelAndReveal_Project.Repositories;

import com.example.PeelAndReveal_Project.EntityBeans.Enum.Category;
import com.example.PeelAndReveal_Project.EntityBeans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon,Integer> {

    public List<Coupon> findAllBycompany_id(int companyID);

    public boolean existsBycompany_idAndTitle(int companyID ,String couponTitle);

    public List<Coupon> findAllBycompany_idAndCategory(int companyID, Category category);

    public List<Coupon> findAllBycompany_idAndPriceLessThanEqual(int companyID, double maxPrice);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM `customers_coupons` WHERE coupons_couponid = ?")
    public void deleteCouponFromCustomers(int couponID);

    public List<Coupon> findBycompany_idAndTitleIsContaining(int companyId, String couponTitle);

    //helps with testing maybe later for searching in companys page?
    public Optional<Coupon> findBycompany_idAndCouponID(int companyId, int couponId);

    public boolean existsBycompany_idAndCouponID(int companyId, int couponId);

}
