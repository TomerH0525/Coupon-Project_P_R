package com.example.PeelAndReveal_Project.Services;

import com.example.PeelAndReveal_Project.EntityBeans.Enum.ClientType;
import com.example.PeelAndReveal_Project.Exceptions.LoginFailedException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class LoginManager {

    private ApplicationContext context;

    public LoginManager(ApplicationContext context) {
        this.context = context;
    }

    public ClientService login(String email, String password, ClientType clientType) throws LoginFailedException {

        switch (clientType){
            case Administrator :
                AdminService admin = context.getBean(AdminService.class);
                if (admin.login(email,password))
                    return admin;
                else
                    throw new LoginFailedException();

            case Company :
                CompanyService company = context.getBean(CompanyService.class);
                if (company.login(email,password))
                    return company;
                else
                    throw new LoginFailedException();

            case Customer:
                CustomerService customer = context.getBean(CustomerService.class);
                if (customer.login(email,password))
                    return customer;
                else
                    throw new LoginFailedException();

            default:
                throw new LoginFailedException();
        }
    }
}
