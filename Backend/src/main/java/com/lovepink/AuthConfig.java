package com.lovepink;

import com.lovepink.service.UserServiceSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserServiceSecurity service;
    @Autowired
    BCryptPasswordEncoder pe;

    @Bean
    public BCryptPasswordEncoder getpasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws  Exception{
        auth.userDetailsService(service);
    }
    @Override
    protected void configure(HttpSecurity http ) throws  Exception {
        http.csrf().disable().cors().disable();
        //phân quyền sử dụng
        http.authorizeRequests()
                .anyRequest().permitAll();
//        http.exceptionHandling()
//                .accessDeniedPage("");//thong bao loi
//        http.rememberMe()
//                .rememberMeParameter("");
        http.formLogin();
        http.logout();
        http.httpBasic();
//                .logoutUrl("")
//                .logoutSuccessUrl("");
    }
}
