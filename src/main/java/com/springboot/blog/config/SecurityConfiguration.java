package com.springboot.blog.config;

import com.springboot.blog.security.CustomUserDetailsService;
import com.springboot.blog.security.JWTAuthenticationFilter;
import com.springboot.blog.security.JwtAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Barnaba Mutai
 * Created on Saturday, July , 16, 2022
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
  private CustomUserDetailsService userDetailsService;
  public SecurityConfiguration(CustomUserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }


  @Bean
  PasswordEncoder passwordEncoder (){

    return new BCryptPasswordEncoder();
  }
  @Autowired
  private JwtAuthenticationEntryPoint authenticationEntryPoint;
  @Bean
  public JWTAuthenticationFilter jwtAuthenticationFilter(){
    return new JWTAuthenticationFilter();
  }


  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .csrf().disable()
      .exceptionHandling()
      .authenticationEntryPoint(authenticationEntryPoint)
      .and()
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .authorizeRequests()
      .antMatchers(HttpMethod.GET,"/api/**").permitAll()
      .antMatchers("/api/auth/**").permitAll()
      .anyRequest()
      .authenticated();
    http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
  }

//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//     http
//       .csrf().disable()
//       .authorizeRequests()
//       .antMatchers(HttpMethod.GET,"/api/**").permitAll()
//       .antMatchers("/api/auth/**").permitAll()
//       .anyRequest()
//       .authenticated()
//       .and()
//       .httpBasic();
//  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  //  @Override
//  @Bean
//  protected UserDetailsService userDetailsService() {
//    UserDetails barn= User.builder().username("barn").password(passwordEncoder()
//      .encode("password")).roles("USER").build();
//    UserDetails admin= User.builder().username("admin").password(passwordEncoder()
//      .encode("admin")).roles("ADMIN").build();
//    return new InMemoryUserDetailsManager(barn,admin);
//  }


  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
