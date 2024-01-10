package com.example.PeelAndReveal_Project;

import com.example.PeelAndReveal_Project.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;

@SpringBootApplication
@EnableScheduling
public class PeelAndRevealApplication implements CommandLineRunner {
    public static void main(String[] args) {

        SpringApplication.run(PeelAndRevealApplication.class, args);
    }

    @Autowired
    ApplicationContext context;

    @Override
    public void run(String... args) {

            testAll();

    }

    public void testAll() {

        try {
//            LoginManager loginManager = context.getBean(LoginManager.class);

            //Sorted tests for each service ! *****please for comfort use bookmarks!*****

            //***********************************Admin service tests!***********************************
//            AdminService adminService = (AdminService) loginManager.login("admin@admin.co.il", "Admin1234", ClientType.Administrator);

            //Adding company's and customers
            //Company object needs these params: {    String name, String email and String password   }
            //Customer object needs these params: {   String firstName, String lastName, String email, String password   }
//            adminService.addCompany(new Company("BMW","BMW@gmail.com","1234BMW")); //adding company to database
//            adminService.addCompany(new Company("Porsche","Porsche@gmail.com","Porsche1234")); //adding company to database
//            adminService.addCompany(new Company("Lamborghini","Lambo@gmail.com","Lambo1234ghini")); //adding company to database
//            adminService.addCustomer(new Customer("Josh","simpleton","josh_simp@gmail.com","Josh123456"));
//            adminService.addCustomer(new Customer("Yakov","simpleton","Yakov_Simp@gmail.com","Yakov1234"));
//            adminService.addCustomer(new Customer("Rivka","simpleton","Rivak_Simp@gmail.com","Rivak1234"));

            //Getting one/many customer or company from database.
//            System.out.println("-----Company!-----");
//            System.out.println(adminService.getOneCompanyByID(1));
//            System.out.println("-----Customer!-----");
//            System.out.println(adminService.getOneCustomerByID(1));

            //Getting all created customers and companies from database;
//            System.out.println("-----Companies!-----");
//            System.out.println(adminService.getAllCompanies());
//            System.out.println("-----Customers!-----");
//            System.out.println(adminService.getAllCustomers());

            //Updating customer and company and saving to database
//            System.out.println("----------------Before changes!----------------");
//            Company company = adminService.getOneCompanyByID(1);
//            System.out.println(company);
//            Customer customer = adminService.getOneCustomerByID(2);
//            System.out.println(customer);
//            System.out.println("----------------After changes!----------------");
//            company.setPassword("Bmw1234");
//            company.setEmail("Bmwcorp123@gmail.com");
//            adminService.updateCompany(company);
//            customer.setFirstName("Joshua");
//            customer.setLastName("simpeltonier");
//            customer.setPassword("simpelton1234");
//            customer.setEmail("Rivka_Simp@gmail.com");
//            adminService.updateCustomer(customer);
//            System.out.println(adminService.getOneCompanyByID(1));
//            System.out.println(adminService.getOneCustomerByID(2));

            //Deleting customer and company from database!(might want to check with coupons purchased and coupons created by the company)
//            System.out.println("-----Before deleting the company-----\n all companies from database:");
//            System.out.println(adminService.getAllCompanies());
//            adminService.deleteCompany(3);
//            System.out.println("-----After deleting the company-----\n all companies from database:");
//            System.out.println(adminService.getAllCompanies());
//            System.out.println("******************************");
//            System.out.println("-----Before deleting the customer-----\n all customers from database:");
//            System.out.println(adminService.getAllCustomers());
//            adminService.deleteCustomer(4);
//            System.out.println("-----After deleting the customer-----\n all customers from database:");
//            System.out.println(adminService.getAllCustomers());


            //***********************************Company service tests!***********************************
//            CompanyService company = (CompanyService) loginManager.login("Porsche@gmail.com","Porsche1234",ClientType.Company);

            //Getting logged in company details (including coupons)
//            System.out.println(company.getCompanyDetails());

            //Adding coupon **company must log in**
            //Coupon object has the next params : { Company(object with id), (ENUM)Category, String title,
            //String description, int amount, double price, Date(SQL) startDate, Date(SQL) endDate, String image
//            company.addCoupon(new Coupon(x`, Category.CARS,"718 Cayman GT4 RS","A high-revving concept that easily scratches the 9,000 mark. An output of 368kW (500PS). Rational? Not always. Perfect? Always.",
//                    10,143_050, Date.valueOf(LocalDate.now()),Date.valueOf(LocalDate.of(2023,12,29)),"https//somegoodwebsite//porscheGT4718.png"));
//            company.addCoupon(new Coupon(company.getCompanyDetails(), Category.CARS,"Taycan Sport Turismo","The best sports moments are when the soul is in perfect balance. With the Taycan Sport Turismo, this feeling can be experienced every day.",
//                    10,92_550, Date.valueOf(LocalDate.now()),Date.valueOf(LocalDate.of(2023,12,29)),"https//somegoodwebsite//porscheTaycanSport.png"));
//            System.out.println("-----Printing company details after adding coupon-----");
//            System.out.println(company.getCompanyDetails());

            //Update coupon **company must log in** (can only update company's own coupons)
//            Coupon companyCoupon = company.getCompanyCouponById(1);
//            System.out.println("-----Before Updating Coupon-----");
//            System.out.println(companyCoupon);
//            companyCoupon.setAmount(50);
//            companyCoupon.setTitle("718 Cayman GT4");
//            companyCoupon.setPrice(150_000);
//            companyCoupon.setCategory(Category.ELECTRONICS);
//            companyCoupon.setDescription("blah blah");
//            companyCoupon.setImage("best image");
//            companyCoupon.setStartDate(Date.valueOf(LocalDate.of(2023,12,05)));
//            companyCoupon.setEndDate(Date.valueOf(LocalDate.of(2023,12,15)));
//            company.updateCoupon(companyCoupon);
//            System.out.println("-----After Updating Coupon-----");
//            System.out.println(company.getCompanyCouponById(1));

            //Deleting coupon **company must log in** (can only delete company's own coupons)
//            System.out.println("-----Before deleting coupon-----");
//            System.out.println(company.getCompanyDetails());
//            company.deleteCoupon(3);
//            System.out.println("-----After deleting coupon-----");
//            System.out.println(company.getCompanyDetails());

            //Getting company's coupons in various filters!
//            company.addCoupon(new Coupon(company.getCompanyDetails(),Category.FOOD,"Salad","Cesar Salad",100,50.99
//                    ,Date.valueOf(LocalDate.of(2023,12,02)),Date.valueOf(LocalDate.of(2023,12,12))
//                    ,"pretty image of salad"));
//            company.addCoupon(new Coupon(company.getCompanyDetails(),Category.FOOD,"Ham sandwich","delicious 5 foot long sandwich with ham!",65,25.99
//                    ,Date.valueOf(LocalDate.of(2023,12,05)),Date.valueOf(LocalDate.of(2023,12,16))
//                    ,"pretty image of ham sandwich"));
//            company.addCoupon(new Coupon(company.getCompanyDetails(),Category.PHONE,"SamyPhone 5 pro","Best phone the world as ever seen. NO JOKE!",999,599.99
//                    ,Date.valueOf(LocalDate.of(2023,12,01)),Date.valueOf(LocalDate.of(2023,12,29))
//                    ,"pretty image of the SamyPhone 5 pro"));
//            System.out.println("-----All logged in companys coupons!-----");
//            System.out.println(company.getAllCompanyCoupons());
//            System.out.println("\n-----Sorted company coupons with category!");
//            System.out.println(company.getAllCompanyCoupons(Category.FOOD));
//            System.out.println("\n-----Sorted company coupons with max price!");
//            System.out.println(company.getAllCompanyCoupons(60));
//            System.out.println("\n-----Sorted company coupons containing title!");
//            System.out.println(company.getCompanyCouponsByTitle("ham"));


            //***********************************Customer service tests!***********************************
//            CustomerService customer = (CustomerService) loginManager.login("Yakov_Simp@gmail.com", "Yakov1234", ClientType.Customer);
//            CustomerService customer = (CustomerService) loginManager.login("Rivak_Simp@gmail.com", "Rivak1234", ClientType.Customer);

            //Getting logged in customer details (including coupons)
//            System.out.println(customer.getCustomerDetails());

            //Purchasing coupon
            //for testing purposes I suggest using admingService to get coupons from database for purchase.
            //use bookmarks to navigate to **admin service tests!** to de comment the service.
//            customer.purchaseCoupon(adminService.getCouponByID(2));
//            customer.purchaseCoupon(adminService.getCouponByID(3));
//            customer.purchaseCoupon(adminService.getCouponByID(5));


            //Getting customers purchased coupons with various filters!
//            System.out.println("-----Getting all customers purchased coupons!-----");
//            System.out.println(customer.getCustomerCoupons());
//            System.out.println("\n-----Getting customers purchased coupons using category-----");
//            System.out.println(customer.getCustomerCoupons(Category.PHONE));
//            System.out.println("\n-----Getting customers purchased coupons using max price-----");
//            System.out.println(customer.getCustomerCoupons(600));






            //my testing mess before sorting tests by services

//            AdminService admin = (AdminService) loginManager.login("Admin@admin.co.il","Admin1234", ClientType.Administrator);
//            CustomerService customer = (CustomerService)  loginManager.login("tomer@gmail.com","123456Tomer",ClientType.Customer);
//            CompanyService company = (CompanyService) loginManager.login("Jacobs@gmail.com","Jacobs123456",ClientType.Company);

//            System.out.println(Date.valueOf(LocalDate.now()));
//            System.out.println(admin.login("admin@admin.co.il","Admin1234"));
//            Customer customer2 = new Customer("Tomer", "Hananaev", "tomer@gmail.com", "123456Tomer");
//            Customer customer3 = new Customer("Josh","blah","JoshB@gmail.com","Joshblah123");
//            admin.addCustomer(customer3);
//            admin.addCustomer(customer2);
//            Company company3 = new Company("Jacobs","Jacobs@gmail.com","Jacobs123456");
//            admin.addCompany(company3);
//            Company company2 = new Company("Taster's choice", "choioce@gmail.com", "123456Choice");
//            admin.addCompany(company2);
//            System.out.println(company.login("choioce@gmail.com", "123456Choice"));
//            Coupon coupon = new Coupon(admin.getOneCompanyByID(2), Category.FOOD, "Jacobs Instant Coffee", "Best quality instant coffee available on the market!!"
//                    , 999, 29.90,Date.valueOf(LocalDate.of(2023,11,26))
//                    , Date.valueOf(LocalDate.of(2023,12,05)), "https:images");
//            Coupon coupon2 = new Coupon(admin.getOneCompanyByID(2), Category.PHONE, "JasdasPro Max", "Best phone available on the market!!"
//                    , 999, 999.99, Date.valueOf(LocalDate.of(2023,11,26))
//                    ,Date.valueOf(LocalDate.of(2023,11,29)), "https:images");
//            Coupon coupon3 = new Coupon(admin.getOneCompanyByID(2), Category.PHONE, "Jxcx", "Best phone available on the market!!"
//                    , 999, 999.99, Date.valueOf(LocalDate.of(2023,11,26))
//                    ,Date.valueOf(LocalDate.of(2023,11,29)), "https:images");
//            Coupon coupon4 = new Coupon(admin.getOneCompanyByID(2), Category.PHONE, "dasgf", "Best phone available on the market!!"
//                    , 999, 999.99, Date.valueOf(LocalDate.of(2023,11,26))
//                    ,Date.valueOf(LocalDate.of(2023,11,29)), "https:images");
//            Coupon coupon5 = new Coupon(admin.getOneCompanyByID(2), Category.PHONE, "bsad Pro Max", "Best phone available on the market!!"
//                    , 999, 999.99, Date.valueOf(LocalDate.of(2023,11,26))
//                    ,Date.valueOf(LocalDate.of(2023,11,29)), "https:images");
//            Coupon coupon6 = new Coupon(admin.getOneCompanyByID(1), Category.PHONE, "yes", "Best phone available on the market!!"
//                    , 999, 999.99, Date.valueOf(LocalDate.of(2023,11,26))
//                    ,Date.valueOf(LocalDate.of(2023,11,29)), "https:images");
//            company.addCoupon(coupon2);
//            company.addCoupon(coupon3);
//            company.addCoupon(coupon4);
//            company.addCoupon(coupon5);
//            company.addCoupon(coupon6);
//            company.addCoupon(coupon);
//            company.addCoupon(coupon2);
//            System.out.println(customer.login("tomer@gmail.com", "123456Tomer"));
//            System.out.println("before adding");

//            customer.purchaseCoupon(customer.getOneCoupon(14));
//            customer.purchaseCoupon(customer.getOneCoupon(16));
//            System.out.println(customer.getCustomerID());
//            customer.purchaseCoupon(customer.getOneCoupon(10));
//            customer.purchaseCoupon(customer.getOneCoupon(11));
//            customer.purchaseCoupon(customer.getOneCoupon(12));
//
//            customer.purchaseCoupon(customer.getOneCoupon(1));
//            customer.purchaseCoupon(admin.getCouponByID(17));
//            customer.login("Pepe@gmail.frog","Lepassword");
//            customer.purchaseCoupon(admin.getCouponByID(17));
//            Customer updateCustomer = admin.getOneCustomerByID(1);
//            updateCustomer.setEmail("pepe@gmail.frog");
//            updateCustomer.setFirstName("Itzik");
//            updateCustomer.setPassword("Itzik123456");
//            admin.updateCustomer(updateCustomer);

//            System.out.println(customer.login("pepe@gmail.frog", "Lepassword"));
//            customer.purchaseCoupon(customer.getOneCoupon(7));


//            System.out.println("after adding");
//            System.out.println(admin.getAllCompanies());
//            System.out.println("**********************************");
//            System.out.println(admin.getAllCompanyCouponsByID(1));
//            System.out.println("**********************************");
//            System.out.println(admin.getOneCompanyByID(3));
//            Company company3 = admin.getOneCompanyByID(3);
//            System.out.println(company3);
//            company3.setPassword("654321Jacobs");
//            company3.setEmail("Jacbos123@gmail.com");
//            admin.updateCompany(company3);
//            System.out.println("and after change ->>");
//            System.out.println(admin.getOneCompanyByID(3));
////            Customer customer3 = new Customer("Pepe","The frog","Pepe@gmail.frog","Lepassword");
////            admin.addCustomer(customer3);
//            System.out.println(customer.login("pepe@gmail.frog","Lepassword"));
//            customer.purchaseCoupon(customer.getOneCoupon(1));
//            admin.deleteCompany(5);
//            admin.deleteCustomer(1);
//            System.out.println(admin.getAllCustomers());

//            System.out.println(company.getCompanyDetails());
//            System.out.println("**********************************************************");
//            System.out.println(company.getAllCompanyCoupons(999.99));
//            System.out.println("**********************************************************");
//            System.out.println(company.getAllCompanyCoupons(900));
//            System.out.println("**********************************************************");
//            System.out.println(company.getAllCompanyCoupons(Category.PHONE));
//            Coupon coupon = admin.getCouponByID(8);
//            coupon.setPrice(1099.99);
//            coupon.setDescription("Best phone on the market! .");
//            company.updateCoupon(coupon);
//            admin.deleteCompany(1);
//            company.deleteCoupon(16);
//            System.out.println(admin.getOneCustomerByID(1));
//            System.out.println(customer.getCustomerCoupons(Category.getCategoryByNumber(6)));
//            System.out.println("*************************************************");
//            System.out.println(customer.getCustomerCoupons(999.99));
//            System.out.println("*************************************************");
//



        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Bean
    public HashMap<String, ClientService> sessionStore(){
        return new HashMap<String, ClientService>();
    }
}

