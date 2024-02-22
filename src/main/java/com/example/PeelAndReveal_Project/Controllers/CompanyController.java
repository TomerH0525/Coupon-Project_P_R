package com.example.PeelAndReveal_Project.Controllers;

import com.example.PeelAndReveal_Project.EntityBeans.Company;
import com.example.PeelAndReveal_Project.EntityBeans.Coupon;
import com.example.PeelAndReveal_Project.EntityBeans.Enum.Category;
import com.example.PeelAndReveal_Project.Exceptions.*;
import com.example.PeelAndReveal_Project.Services.ClientService;
import com.example.PeelAndReveal_Project.Services.CompanyService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.Base64;
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
        Company company = getService().getCompanyDetails();
        System.out.println(company);
        return ResponseEntity.ok(company);

    }

    @PostMapping("/coupon/add")
    public ResponseEntity<Integer> addCoupon(@RequestBody Coupon coupon) throws CouponTitleAlreadyExistsException, IdNotFoundException, CouponDateException, CouponAmountException, CouponPriceException, LoginFailedException, CouponCategoryException, CouponDescriptionException, CouponImageException, InvalidCouponException {
        coupon.setCompany(getService().getCompanyDetails());
        if (!coupon.getImage().isBlank()) {
            String imageFileName = (coupon.getTitle().replaceAll("\\s*","")+"_"+coupon.getCompany().getId()+".jpg");
            byte[] base64IntoBytes = Base64.getDecoder().decode(coupon.getImage().split(",", 2)[1]);
            saveCouponImage(imageFileName,base64IntoBytes);
            coupon.setImage("http://localhost:8080/images/"+imageFileName);
        }else{
            coupon.setImage("http://localhost:8080/images/couponPlaceholder.jpg");
        }
        int newCouponID = getService().addCoupon(coupon);
        return ResponseEntity.ok(newCouponID);
    }

    @PutMapping("/coupon/{id}/update")
    public ResponseEntity<Coupon> updateCoupon(@RequestBody Coupon coupon, @PathVariable("id") int couponID) throws LoginFailedException, CouponTitleAlreadyExistsException, IdNotFoundException, CouponAmountException, CouponPriceException, CouponDateException, CouponImageException {
        coupon.setCompany(getService().getCompanyDetails());
        if (coupon.getImage().length() > 200) {
            if (!coupon.getImage().isBlank()) {
                String imageFileName = (coupon.getTitle().replaceAll("\\s*", "") + "_" + coupon.getCompany().getId() + ".jpg");
                byte[] base64IntoBytes = Base64.getDecoder().decode(coupon.getImage().split(",", 2)[1]);
                saveCouponImage(imageFileName, base64IntoBytes);
                coupon.setImage("http://localhost:8080/images/" + imageFileName);
            } else {
                coupon.setImage("http://localhost:8080/images/couponPlaceholder.jpg");
            }
        }
        return ResponseEntity.accepted().body(getService().updateCoupon(coupon));
    }

    @DeleteMapping("/coupon/{couponID}/delete")
    public ResponseEntity<String> deleteCoupon(@PathVariable int couponID) throws LoginFailedException, CouponNotExistException, IdNotFoundException {
        getService().deleteCoupon(couponID);
        return ResponseEntity.accepted().body("Coupon Deleted Successfully!");
    }

    @GetMapping("/coupon/all")
    public ResponseEntity<List<Coupon>> getAllCompanyCoupons() throws LoginFailedException, IdNotFoundException {
        return ResponseEntity.ok(getService().getAllCompanyCoupons());
    }

    @GetMapping("/coupon/{couponID}")
    public ResponseEntity<Coupon> getCouponByID(@PathVariable int couponID) throws LoginFailedException, CouponNotExistException, IdNotFoundException {
        return ResponseEntity.ok(getService().getCompanyCouponById(couponID));
    }

    @GetMapping("/coupons/filter/title-{filter}")
    public ResponseEntity<List<Coupon>> getAllCouponsByTitle(@PathVariable("filter") String userInput) throws LoginFailedException, IdNotFoundException {
        return ResponseEntity.ok(getService().getCompanyCouponsByTitle(userInput));
    }

    @GetMapping("/coupon/filter/maxPrice-{price}")
    public ResponseEntity<List<Coupon>> getAllCouponsByMaxPrice(@PathVariable("price") double maxPrice) throws LoginFailedException, IdNotFoundException {
        return ResponseEntity.ok(getService().getAllCompanyCoupons(maxPrice));
    }

    @GetMapping("/coupon/filter/category-{category}")
    public ResponseEntity<List<Coupon>> getAllCouponsByCategory(@PathVariable("category") Category category) throws LoginFailedException, IdNotFoundException {
        return ResponseEntity.ok(getService().getAllCompanyCoupons(category));
    }


    private CompanyService getService() throws LoginFailedException {
        String token = request.getHeader("Authorization");
        CompanyService company = (CompanyService) sessionStore.get(token);
        if (company == null)
            throw new LoginFailedException("Login information expired");
        else return company;

    }

    private void isImagesFolderExists() throws CouponImageException {
        File images = new File(System.getProperty("user.dir") + File.separator + "/target/classes/images");
        if (!images.exists() && !images.isDirectory()) {
            boolean folderCreated = images.mkdir();
            if (folderCreated) {
                System.out.println(LocalDateTime.now() +" : CompanyController/isImagesFolderExists: Created images folder. (was not present)");
            } else {
                System.out.println(LocalDateTime.now() +" : CompanyController/isImagesFolderExists: Encountered an error while trying to create images folder.");
                throw new CouponImageException();
            }
        }
    }

    private void saveCouponImage(String imageFileName, byte[] image) throws CouponImageException {
        String outputDirectory = System.getProperty("user.dir") + File.separator + "/target/classes/images";
        File outputFile = new File(outputDirectory, imageFileName);
        isImagesFolderExists();
        try (FileOutputStream imageWriter = new FileOutputStream(outputFile)) {

            imageWriter.write(image);

        } catch (Exception e) {
            System.out.println("Couldn't save image due to:\n" + e.getMessage());
            throw new CouponImageException();
        }
    }
}
