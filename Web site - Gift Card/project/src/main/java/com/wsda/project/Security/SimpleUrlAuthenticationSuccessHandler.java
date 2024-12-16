package com.wsda.project.Security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


public class SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails user = (UserDetails)authentication.getPrincipal();

        String url;

        Collection<? extends GrantedAuthority> roles= user.getAuthorities();
        if(roles.contains(new SimpleGrantedAuthority("USER"))){
            url="/user/home";
        }
        else if(roles.contains(new SimpleGrantedAuthority("NEGOZIANTE"))){
            url="/negoziante/home";
        }
        else{
            url="admin/home";
        }

        redirectStrategy.sendRedirect(request, response, url);
    }
}