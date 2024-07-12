package com.smapp.sm_app.security;


import com.smapp.sm_app.entity.User;
import com.smapp.sm_app.exception.CustomAuthenticationException;
import com.smapp.sm_app.service.UserDetailsServiceImpl;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {


        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("auth-token")) {
                    String authorizationHeader = "Bearer " + cookie.getValue();
                    try {
                        User user = jwtTokenProvider.verifyToken(authorizationHeader);
                        if (user != null) {
                            CurrentUser currentUser = new CurrentUser(user);
                            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                    currentUser,
                                    null,
                                    currentUser.getAuthorities()
                            );
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    } catch (AuthenticationException ex) {
                        authenticationEntryPoint.commence(request, response, ex);
                    }catch (AccessDeniedException ex){
                        authenticationEntryPoint.commence(request, response, new CustomAuthenticationException(ex.getMessage()));
                    }
                    catch (Exception ex){
                        authenticationEntryPoint.commence(request, response, new CustomAuthenticationException(ex.getMessage() + " " + ex.getClass()));
                    }
                }
            }
        }

//        if (request.getHeader("Authorization") != null) {
//            String authorizationHeader = request.getHeader("Authorization");
//            try {
//                User user = jwtTokenProvider.verifyToken(authorizationHeader);
//                if (user != null) {
//                    CurrentUser currentUser = new CurrentUser(user);
//                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                            currentUser,
//                            null,
//                            currentUser.getAuthorities()
//                    );
//                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                }
//            } catch (JwtException ex) {
//               authenticationEntryPoint.commence(request, response, new CustomAuthenticationException(ex.getMessage()));
//            }catch (AuthenticationException ex) {
//                authenticationEntryPoint.commence(request, response, ex);
//            }
//        }

        filterChain.doFilter(request, response);
    }








//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String authorizationHeader = request.getHeader("Authorization");
//
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            try {
//                String token = authorizationHeader.substring(7);
//                Long id = jwtTokenProvider.verifyToken(token);
//
//                if (id != null) {
//                    CurrentUser userDetails = (CurrentUser) userDetailsService.loadUserById(id);
//
//                    if (userDetails == null) {
//                        throw new JwtException("User not found");
//                    }
//
//                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                }
//            } catch (JwtException ex) {
//                authenticationEntryPoint.commence(request, response, new CustomAuthenticationException(ex.getMessage()));
//            } catch (AuthenticationException ex) {
//                authenticationEntryPoint.commence(request, response, ex);
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//


}
