package com.example.PeelAndReveal_Project.Controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.PeelAndReveal_Project.EntityBeans.Company;
import com.example.PeelAndReveal_Project.EntityBeans.Customer;
import com.example.PeelAndReveal_Project.EntityBeans.Enum.ClientType;
import com.example.PeelAndReveal_Project.EntityBeans.User;
import com.example.PeelAndReveal_Project.Exceptions.IdNotFoundException;
import com.example.PeelAndReveal_Project.Exceptions.LoginFailedException;
import com.example.PeelAndReveal_Project.Services.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class LoginController {
    private final LoginManager loginManager;
    private final HashMap<String, ClientService> sessionStore;
    private final HttpServletRequest request;

    public LoginController(LoginManager loginManager, HashMap<String, ClientService> sessionStore, HttpServletRequest request) {
        this.loginManager = loginManager;
        this.sessionStore = sessionStore;
        this.request = request;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) throws IdNotFoundException, LoginFailedException {
        System.out.println("user is !" + user);
        ClientService facade = loginManager.login(user.getEmail(), user.getPassword(), user.getClientType());
        if (facade != null){
            String clientToken = createToken(facade);
            sessionStore.put(clientToken,facade);
            return ResponseEntity.status(HttpStatus.OK).body(clientToken);
        }else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email or password are incorrect...");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(){
        sessionStore.remove(request.getHeader("Authorization").replace("Bearer ",""));
        return ResponseEntity.ok("logged out successfully");
    }

    private String createToken(ClientService facade) throws IdNotFoundException {
       if (facade instanceof CompanyService){
           Company company = ((CompanyService) facade).getCompanyDetails();
           return JWT.create()
                   .withClaim("id",company.getId())
                   .withClaim("name",company.getName())
                   .withClaim("email",company.getEmail())
                   .withClaim("role", ClientType.Company.toString())
                   .withIssuedAt(Instant.now())
                   .withExpiresAt(Instant.now().plus(30, ChronoUnit.MINUTES))
                   .sign(Algorithm.none());

       }else if (facade instanceof CustomerService){
           Customer customer = ((CustomerService) facade).getCustomerDetails();
           return JWT.create()
                   .withClaim("id", customer.getCustomerID())
                   .withClaim("firstName", customer.getFirstName())
                   .withClaim("lastName", customer.getLastName())
                   .withClaim("email", customer.getEmail())
                   .withClaim("role", ClientType.Customer.toString())
                   .withIssuedAt(Instant.now())
                   .withExpiresAt(Instant.now().plus(30, ChronoUnit.MINUTES))
                   .sign(Algorithm.none());

       }else if (facade instanceof AdminService){
           return JWT.create()
                   .withClaim("name", "Big Boss")
                   .withClaim("role", ClientType.Administrator.toString())
                   .withIssuedAt(Instant.now())
                   .withExpiresAt(Instant.now().plus(15, ChronoUnit.MINUTES))
                   .sign(Algorithm.none());

       }else return "";

    }
}
