package com.example.PeelAndReveal_Project.Filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.example.PeelAndReveal_Project.Controllers.LoginController;
import com.example.PeelAndReveal_Project.Exceptions.LoginFailedException;
import com.example.PeelAndReveal_Project.Services.ClientService;
import com.example.PeelAndReveal_Project.Services.LoginManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Component
@Order(2)
public class TokenFilter extends OncePerRequestFilter {

    private final LoginController logincontroller;
    private final HashMap<String, ClientService> sessionStore;

    public TokenFilter(LoginController logincontroller, HashMap<String, ClientService> sessionStore) {
        this.logincontroller = logincontroller;
        this.sessionStore = sessionStore;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token == null){
            //no token
            response.setStatus(403);
            response.getWriter().write("You must log in!");
        }else {
            //we got a token
            token = token.replace("Bearer ","");

            try {

                JWT.decode(token).getClaim("role").asString();
                if (sessionStore.containsKey(token)) {
                Date exp = JWT.decode(token).getExpiresAt();

                    if (exp.after(new Date())) {
                        //if there is role in the token it won't throw exception, and you can move to the next step
                        filterChain.doFilter(request, response);
                    }else {
                        System.out.println(LocalDateTime.now()+" : deleted expired token from map :  " +token);
                        sessionStore.remove(token);
                        throw new LoginFailedException("Login token expired please log in again");
                    }
                }else {
                    System.out.println(LocalDateTime.now() +" : No token found : "+token+"\nsent status code 403! ^^ ");
                    response.setStatus(403);
                    response.getWriter().write("You must log in!");
                }
                //if there is not (probably someone constructed a token and tries to mess with us)
            }catch (JWTDecodeException ex){
                response.setStatus(403);
                response.getWriter().write("You must log in!");
            } catch (LoginFailedException e) {
                response.setStatus(403);
                response.getWriter().write(e.getMessage());
            }
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        List<String> patterns = List.of("/v3/api-docs", "/configuration/", "/swagger", "/webjars", "/auth/login","/coupons","/images");
        return patterns.stream().anyMatch( p-> request.getRequestURL().toString().contains(p));
    }
}
