package com.example.PeelAndReveal_Project.Filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

@Component
@Order(2)
public class TokenFilter extends OncePerRequestFilter {
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
                //if there is role in the token it won't throw exception, and you can move to the next step
                filterChain.doFilter(request,response);
                //if there is not (probably someone constructed a token and tries to mess with us)
            }catch (JWTDecodeException ex){
                response.setStatus(403);
                response.getWriter().write("You must log in!");
            }
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        List<String> patterns = List.of("/v3/api-docs", "/configuration/", "/swagger", "/webjars", "/auth/login");
        return patterns.stream().anyMatch( p-> request.getRequestURL().toString().contains(p));
    }
}
