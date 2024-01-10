package com.example.PeelAndReveal_Project.Services;

import com.example.PeelAndReveal_Project.EntityBeans.Company;
import com.example.PeelAndReveal_Project.EntityBeans.Coupon;
import com.example.PeelAndReveal_Project.EntityBeans.Customer;
import com.example.PeelAndReveal_Project.Exceptions.EmailExistsException;
import com.example.PeelAndReveal_Project.Exceptions.IdNotFoundException;
import com.example.PeelAndReveal_Project.Exceptions.IdNotZeroException;
import com.example.PeelAndReveal_Project.Exceptions.NameExistsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService extends ClientService {

    protected AdminService() {
    }

    /**
     * Login method
     *
     * @param email    String
     * @param password String (case-sensitive)
     * @return boolean (true/false)
     */
    @Override
    protected boolean login(String email, String password) {
        return email.equalsIgnoreCase("admin@admin.co.il") && password.equals("Admin1234");
    }

    /**
     * @return a list of all companies or if empty return empty list.
     */
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    /**
     * @param companyID Integer
     * @return one company associated with the companyID provided.
     * Company params : int companyID , String email , String name , String password , List of coupons
     * @throws IdNotFoundException if no company was found with the param 'companyID'.
     */
    public Company getOneCompanyByID(int companyID) throws IdNotFoundException {
        return companyRepository.findById(companyID).orElseThrow(IdNotFoundException::new);
    }

    /**
     * method adds a new company to the database.
     *
     * @param company needs to contain the params - Name , Email , Password (companyID must be 0)
     * @throws EmailExistsException if the name is already taken
     * @throws NameExistsException  if the email is already taken
     */
    public Company addCompany(Company company) throws EmailExistsException, NameExistsException {
        if (!companyRepository.existsByEmail(company.getEmail())) {
            if (!companyRepository.existsByName(company.getName()))
                return companyRepository.saveAndFlush(company);
            else throw new NameExistsException();
        } else throw new EmailExistsException();
    }

    /**
     * updating company details (cannot update ID nor company name)
     *
     * @param company company Object after changes!
     * @throws IdNotFoundException - if the company's ID wasn't found in the database.
     */
    public void updateCompany(Company company) throws IdNotFoundException, NameExistsException {
        Company companyAtDataBase = companyRepository.findById(company.getId()).orElseThrow(IdNotFoundException::new);
        if (companyAtDataBase.getName().equalsIgnoreCase(company.getName()))
            companyRepository.save(company);
        else throw new NameExistsException("Cannot change company name!");
    }

    /**
     * deleting company from database (deleting coupons created by the company including customers coupon if bought from the company)
     *
     * @param companyID Integer
     * @throws IdNotFoundException - if the id of the company was not found in database.
     */
    @Transactional
    public boolean deleteCompany(int companyID) throws IdNotFoundException {
        boolean isFound = true;
        if (companyRepository.existsById(companyID)) {
            List<Coupon> coupons = couponRepository.findAllBycompany_id(companyID);
            if (!coupons.isEmpty()) {
                couponRepository.deleteAllInBatch(coupons);
            }
            companyRepository.deleteById(companyID);
            return isFound;
        } else return !isFound;
    }

    /**
     * adding customer to database
     *
     * @param customer - customer with the params : String firstName, String lastName, String email, String password (customerID must be 0 while adding)
     * @throws EmailExistsException if a customer with the same email already exists
     * @throws IdNotZeroException   - if the customer you are trying to add already has an id set(must be 0 when adding).
     */
    public Customer addCustomer(Customer customer) throws EmailExistsException, IdNotZeroException {
        if (!customerRepository.existsByEmail(customer.getEmail())) {
            if (customer.getCustomerID() == 0)
                return customerRepository.saveAndFlush(customer);
            else throw new IdNotZeroException();
        } else throw new EmailExistsException();
    }

    /**
     * updates customer information : email , password , name and coupon purchased.
     *
     * @param customer must be with ID
     * @throws IdNotFoundException  if customerID was not found in database.
     * @throws EmailExistsException if the email already exists in database.
     */
    public void updateCustomer(Customer customer) throws IdNotFoundException, EmailExistsException {

        if (customerRepository.existsById(customer.getCustomerID())) {
            if (!customerRepository.existsByEmail(customer.getEmail())) {
                customerRepository.save(customer);

            } else throw new EmailExistsException();
        } else throw new IdNotFoundException();


    }

    /**
     * deletes a customer from database (including his coupons purchased)
     *
     * @param customerID Integer
     * @throws IdNotFoundException if the id provided is not associated with any customer in database.
     */
    public boolean deleteCustomer(int customerID) throws IdNotFoundException {
        if (customerRepository.existsById(customerID)) {
            customerRepository.deleteById(customerID);
            return true;
        } else return false;
    }

    /**
     * @param customerID Integer
     * @return customer Object with the params : int CustomerID , String firstName , String lastName , String email , String password , List of coupons
     * @throws IdNotFoundException if not customer was found in database using the customerID provided
     */
    public Customer getOneCustomerByID(int customerID) throws IdNotFoundException {
        return customerRepository.findById(customerID).orElseThrow(IdNotFoundException::new);
    }

    /**
     * @return a list of customers or if empty an empty list
     * Customer params : int CustomerID , String firstName , String lastName , String email , String password , List of coupons
     */
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }


    //for testing purposes.
    public Coupon getCouponByID(int couponID) throws IdNotFoundException {
        return couponRepository.findById(couponID).orElseThrow(IdNotFoundException::new);
    }
}
