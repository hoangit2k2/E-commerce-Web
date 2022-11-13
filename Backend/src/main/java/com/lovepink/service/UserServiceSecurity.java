package com.lovepink.service;

import com.lovepink.Dao.userDao;
import com.lovepink.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceSecurity implements UserDetailsService {
    @Autowired
    userDao dao;
    @Autowired
    BCryptPasswordEncoder pe;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        try {
            Users user = dao.findById(username).get();
            System.out.println(user);
            String password = user.getPassword();
            String role = user.getRole();
            System.out.println(password);
            System.out.println(role);
            return User.withUsername(username)
                    .password(pe.encode(password))
                    .roles(role).build();
        }catch (Exception e){
            throw new UsernameNotFoundException(username + "Not Found");
        }
    }
}
