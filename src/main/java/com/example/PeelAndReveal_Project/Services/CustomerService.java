package com.example.PeelAndReveal_Project.Services;

import com.example.PeelAndReveal_Project.EntityBeans.Enum.Category;
import com.example.PeelAndReveal_Project.EntityBeans.Coupon;
import com.example.PeelAndReveal_Project.EntityBeans.Customer;
import com.example.PeelAndReveal_Project.Exceptions.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@Scope("prototype")
public class CustomerService extends ClientService {

    //Current logged in customer id. (gets initialized only after using login() method)
    private int customerID;

    protected CustomerService() {
    }

    public int getCustomerID() {
        return customerID;
    }

    private void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Login method checks if there's a customer with an email available in database and returns said customer if not return an empty customer.
     * then compares the password and email provided by the customer.
     * note : users email are unique.
     *
     * @param email    user's email
     * @param password user's password
     * @return boolean.
     */
    @Override
    public boolean login(String email, String password) {
        Customer customer = customerRepository.findByEmail(email).orElse(new Customer());
        if (customer.getCustomerID() > 0) {
            if (customer.getEmail().equalsIgnoreCase(email) && customer.getPassword().equals(password)) {
                setCustomerID(customer.getCustomerID());
                return true;
            } else return false;

        } else return false;
    }

    /**
     * Customers method to purchase a coupon.
     * This method checks a few things :
     * if the coupon id exists ,
     * if coupon is expired ,
     * if coupon is already owned by the customer ,
     * if the coupon is not out of stock
     *
     * @param couponId { (Object)Company, (ENUM)Category, String title, String description, int amount, double price, Date(SQL) startDate, Date(SQL) endDate, String image}
     * @throws IdNotFoundException         if there was a problem with customer ID not displaying correctly.
     * @throws CouponDateException         if the coupon the customer is trying to purchase out of date.
     * @throws CouponAmountException       if the coupon amount at 0.
     * @throws CouponAlreadyOwnedException if the coupon is already owned by the customer (no duplicates)
     */
    @Transactional
    public synchronized void purchaseCoupon(int couponId) throws IdNotFoundException, CouponDateException, CouponAmountException, CouponAlreadyOwnedException {
        Customer thisCustomer = customerRepository.findById(customerID).orElseThrow(IdNotFoundException::new);
        Coupon dataBaseCoupon = couponRepository.findById(couponId).orElseThrow(() -> new IdNotFoundException("Coupon not found..."));
        Date couponEndDate = dataBaseCoupon.getEndDate();
        boolean ownedCoupon = thisCustomer.getCoupons().stream()
                .anyMatch(c -> c.getCouponID() == dataBaseCoupon.getCouponID());
        if (couponEndDate.after(Date.valueOf(LocalDate.now())) || couponEndDate.equals(Date.valueOf(LocalDate.now()))) {
            if (!ownedCoupon) {
                if (dataBaseCoupon.getAmount() > 0) {
                    dataBaseCoupon.setAmount(dataBaseCoupon.getAmount()-1);
                    thisCustomer.getCoupons().add(dataBaseCoupon);
                    customerRepository.save(thisCustomer);
                } else throw new CouponAmountException("(coupon)" + dataBaseCoupon.getTitle() + " : out of stock");

            } else throw new CouponAlreadyOwnedException();
        } else throw new CouponDateException();
    }

    /**
     * finds all logged in customers coupons
     *
     * @return List of the object Coupon contains :
     * { Company companyID, (ENUM)Category, String title, String description, int amount, double price, Date(SQL) startDate, Date(SQL) endDate, String image}
     * @throws IdNotFoundException if the customer is not logged in or id not found
     */
    public List<Coupon> getCustomerCoupons() throws IdNotFoundException {
        return getCustomerDetails().getCoupons().stream().toList();
    }

    /**
     * finds all logged in customers coupons with a certain category filter
     *
     * @param category filters the coupon to the chosen category.
     * @return filtered List of the object Coupon contains : { Company companyID, (ENUM)Category , String title, String description, int amount, double price, Date(SQL) startDate, Date(SQL) endDate, String image}
     * @throws IdNotFoundException if the customer is not logged in or id not found
     */
    public List<Coupon> getCustomerCoupons(Category category) throws IdNotFoundException {
        return getCustomerDetails().getCoupons().stream().filter(c -> c.getCategory() == category).toList();
    }

    /**
     * finds all logged in customers coupons with a maxPrice filter
     *
     * @param maxPrice (Double variable) filters the coupon to the chosen maxPrice.
     * @return filtered List of the object Coupon contains : { Company companyID, (ENUM)Category, String title, String description, int amount, double price, Date(SQL) startDate, Date(SQL) endDate, String image}
     * @throws IdNotFoundException if the customer is not logged in or id not found
     */
    public List<Coupon> getCustomerCoupons(double maxPrice) throws IdNotFoundException {
        return getCustomerDetails().getCoupons().stream().filter(c -> c.getPrice() <= maxPrice).toList();
    }

    /**
     * method to get current logged in customer details -
     * coupons and personal customer information
     *
     * @return customer object with : firstName, lastName , email , password , List of coupons
     * @throws IdNotFoundException if the customer is not logged in or id not found
     */
    public Customer getCustomerDetails() throws IdNotFoundException {
        return customerRepository.findById(this.customerID).orElseThrow(IdNotFoundException::new);
    }


}