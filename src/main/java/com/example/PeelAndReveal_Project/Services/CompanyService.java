package com.example.PeelAndReveal_Project.Services;

import com.example.PeelAndReveal_Project.EntityBeans.Enum.Category;
import com.example.PeelAndReveal_Project.EntityBeans.Company;
import com.example.PeelAndReveal_Project.EntityBeans.Coupon;
import com.example.PeelAndReveal_Project.Exceptions.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope("prototype")
public class CompanyService extends ClientService {

    //Current logged in company id. (gets initialized only after using login() method)
    private int companyID;

    protected CompanyService() {
    }

    public int getCompanyID() {
        return companyID;
    }

    //save the logged company ID
    private void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    /**
     * Login method checks if there's a company with an email available in database and returns said company if not return an empty company.
     * then compares the password and email provided by the company.
     * note : company's email are unique.
     *
     * @param email    String - company's email
     * @param password String - company's password
     * @return boolean
     */
    @Override
    public boolean login(String email, String password) {
        Company company = companyRepository.findByEmail(email).orElse(new Company());
        if (company.getId() > 0) {
            if (company.getEmail() != null && company.getEmail().equalsIgnoreCase(email) && company.getPassword().equals(password)) {
                setCompanyID(company.getId());
                return true;
            } else return false;
        }
        return false;
    }

    /**
     * Method for the company to add coupons for sale.
     *
     * @param coupon {    (Object)Company, (ENUM)Category, String title, String description, int amount, double price,
     *               Date(SQL) startDate, Date(SQL) endDate, String image}
     * @throws CouponTitleAlreadyExistsException if the title already exists in the logged company list of coupons.
     * @throws CouponDateException        if the company is trying to add a coupon with end_date before today.
     * @throws IdNotFoundException               if the company is not logged in or id not found
     */
    public int addCoupon(Coupon coupon) throws CouponTitleAlreadyExistsException, IdNotFoundException, CouponDateException, CouponAmountException, CouponPriceException {
        //checking if the company logged in or not.
        if (companyID > 0) {
            if (coupon.getAmount() > 0) {
                if (coupon.getPrice() >= 0) {
                    System.out.println(coupon);
                    //checking if the coupon title already exists in company list of coupons and to see if the coupon doesn't have an id (auto increment in database)
                    if (!couponRepository.existsBycompany_idAndTitle(coupon.getCompany().getId(), coupon.getTitle()) && coupon.getCouponID() == 0) {
                        //checking if the coupon start_date is not set after end_date.
                        if (coupon.getEndDate().after(coupon.getStartDate()) || coupon.getEndDate().equals(coupon.getStartDate())) {
                            return couponRepository.saveAndFlush(coupon).getCouponID();
                        } else
                            throw new CouponDateException("Cannot set end_date before start_date!!!");
                    } else
                        throw new CouponTitleAlreadyExistsException(coupon.getTitle());
                }else
                    throw new CouponPriceException();
            }else
                throw new CouponAmountException("Can only add coupon with amount > 0");
        } else
            throw new IdNotFoundException("Please login...(id not present)");
    }

    /**
     * update coupon method
     *
     * @param coupon needs Coupon object with the next params :
     *               { (Object)Company, (ENUM)Category, String title, String description, int amount, double price, Date(SQL) startDate, Date(SQL) endDate, String image}
     * @throws IdNotFoundException if the coupon does not exist by ID or doest have id.
     */
    public void updateCoupon(Coupon coupon) throws IdNotFoundException, CouponTitleAlreadyExistsException {
        if (couponRepository.existsById(coupon.getCouponID())) {
            if (!couponRepository.existsBycompany_idAndTitle(this.companyID, coupon.getTitle()))
                couponRepository.save(coupon);
            else throw new CouponTitleAlreadyExistsException();
        } else throw new IdNotFoundException("Coupon not found, unable to update coupon.(id not found)");
    }

    /**
     * deleting coupon method
     *
     * @param couponID using coupon ID to find it in database and delete.
     * @throws IdNotFoundException if the id was not found in database.
     */
    public void deleteCoupon(int couponID) throws IdNotFoundException, CouponNotExistException {
        if (this.companyID > 0) {
            if (couponRepository.existsBycompany_idAndCouponID(this.companyID, couponID)) {
                couponRepository.deleteCouponFromCustomers(couponID);
                couponRepository.deleteById(couponID);
            } else throw new CouponNotExistException("Coupon not found or not owned");
        } else throw new IdNotFoundException("Must login to get information...");
    }

    /**
     * find all logged in company coupons
     *
     * @return List of the object Coupon contains :
     * { (Object)Company, (ENUM)Category , String title, String description, int amount, double price, Date(SQL) startDate, Date(SQL) endDate, String image}
     * @throws IdNotFoundException if the company is not logged in or id not found
     */
    public List<Coupon> getAllCompanyCoupons() throws IdNotFoundException {
        if (companyID > 0) {
            return couponRepository.findAllBycompany_id(this.companyID);
        } else throw new IdNotFoundException("Must login to get information...");
    }

    /**
     * find all logged in company coupons with a category filter
     *
     * @param category filters the coupon to the chosen category.
     * @return filtered List of the object Coupon contains : { (Object)Company, (ENUM)Category, String title, String description, int amount, double price, Date(SQL) startDate, Date(SQL) endDate, String image}
     * @throws IdNotFoundException if the company is not logged in or id not found
     */
    public List<Coupon> getAllCompanyCoupons(Category category) throws IdNotFoundException {
        if (companyID > 0) {
            return couponRepository.findAllBycompany_idAndCategory(this.companyID, category);
        } else throw new IdNotFoundException("Must login to get information...");
    }

    /**
     * find all logged in company coupons with a maxPrice filter
     *
     * @param maxPrice (Double variable) filters the coupon to the chosen maxPrice.
     * @return filtered List of the object Coupon contains : { (Object)Company, (ENUM)Category, String title, String description, int amount, double price, Date(SQL) startDate, Date(SQL) endDate, String image}
     * @throws IdNotFoundException if the company is not logged in or id not found
     */
    public List<Coupon> getAllCompanyCoupons(double maxPrice) throws IdNotFoundException {
        if (this.companyID > 0) {
            return couponRepository.findAllBycompany_idAndPriceLessThanEqual(companyID, maxPrice);
        } else throw new IdNotFoundException("Must login to get information...");
    }

    /**
     * method to get current logged in company details -
     * coupons and personal company information
     *
     * @return Company object with : name , email , password , List of coupons
     * @throws IdNotFoundException if the company is not logged in or id not found
     */
    public Company getCompanyDetails() throws IdNotFoundException {
        return companyRepository.findById(this.companyID).orElseThrow(() -> new IdNotFoundException("Must login to get information..."));
    }


    //*************EXTRA METHODS*************

    /**
     * /**
     * find all logged in company coupons containing title
     *
     * @param couponTitle (String variable) filters to show only the coupons containing the title given.
     * @return filtered List of the object Coupon contains : { (Object)Company, (ENUM)Category, String title, String description, int amount, double price, Date(SQL) startDate, Date(SQL) endDate, String image}
     * @throws IdNotFoundException if the company is not logged in or id not found
     */
    public List<Coupon> getCompanyCouponsByTitle(String couponTitle) throws IdNotFoundException {
        if (this.companyID > 0)
            return couponRepository.findBycompany_idAndTitleIsContaining(this.companyID, couponTitle);
        else throw new IdNotFoundException("Must login to get information...");
    }

    public Coupon getCompanyCouponById(int couponID) throws CouponNotExistException, IdNotFoundException {
        if (this.companyID > 0)
            return couponRepository.findBycompany_idAndCouponID(this.companyID, couponID).orElseThrow(CouponNotExistException::new);
        else throw new IdNotFoundException("Must login to get information...");
    }
}
