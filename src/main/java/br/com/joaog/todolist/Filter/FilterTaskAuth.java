package br.com.joaog.todolist.Filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.joaog.todolist.Repository.IUserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {
    @Autowired
    IUserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().startsWith("/tasks")){
            var auth = request.getHeader("Authorization");
            var user_pass = auth.substring("Basic".length()).trim();

            byte[] authDecoded = Base64.getDecoder().decode(user_pass);
            System.out.println(authDecoded);

            var pass = new String(authDecoded);
            var credentials = pass.split(":");
            var username = credentials[0];
            var password = credentials[1];
            System.out.println(username + password);

            var user = this.userRepository.findByUsername(username);
            if (user == null){
                response.sendError(401);
            }else {
                var passwordVerified = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if (passwordVerified.verified){
                    request.setAttribute("idUser",user.getId());
                    filterChain.doFilter(request,response);
                }else {
                    response.sendError(401);
                }

            }
        }else {
            filterChain.doFilter(request,response);
        }


    }
}
