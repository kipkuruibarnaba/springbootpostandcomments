package com.springboot.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Barnaba Mutai
 * Created on Sunday, July , 17, 2022
 */
public class JWTAuthenticationFilter extends OncePerRequestFilter {
  //Inject The required dependencies
  @Autowired
  private JwtTokenProvider tokenProvider;
  @Autowired
  private CustomUserDetailsService customUserDetailsService;
  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse
                                    response,
                                  FilterChain filterChain) throws ServletException, IOException {
   //get JWT (token) from http requests
     String token = getJWTFromRequest(request);
    //validate token
     if(StringUtils.hasText(token) && tokenProvider.validateToken(token)){

       //get username from token
       String username = tokenProvider.getUserNameFromJwt(token);

       // load user associated with the token from database
       UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
       UsernamePasswordAuthenticationToken authenticateToken = new UsernamePasswordAuthenticationToken(
         userDetails ,null,userDetails.getAuthorities()
       );
        authenticateToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

       // set spring security
       SecurityContextHolder.getContext().setAuthentication(authenticateToken);
     }
     filterChain.doFilter(request,response);
  }
  //Bearer <accesstoken>
  private String getJWTFromRequest(HttpServletRequest request){
    String bearerToken = request.getHeader("Authorization");
    if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")){
      return bearerToken.substring(7, bearerToken.length());
    }
    return null;
  }
}
