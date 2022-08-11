package edu.hoang.service;

import edu.hoang.DAO.AccountDAO;
import edu.hoang.entity.Account;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
// lấy dữ liệu từ csdl
@Service
public class UserService
//implements UserDetailsService 
{
//    @Autowired
//    AccountDAO accountDao;
//    @Autowired
//    BCryptPasswordEncoder pe;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
//        try {
//            Account account = accountDao.findById(username).get();
//
//            //Tao UserDetails từ Account
//            String password = account.getPassword();
//            String[] roles = account.getAuthorities().stream()
//                    .map(au -> au.getRole().getId())
//                    .collect(Collectors.toList()).toArray(new String[0]);
//            return User.withUsername(username)
//                    .password(pe.encode(password))
//                    .roles(roles).build();
//        }catch (Exception e){
//            //hiện thông báo lỗi
//            throw new UsernameNotFoundException(username+"Not Found");
//        }
//
//    }
//    public  void loginFormOAuth2(OAuth2AuthenticationToken oauth2){
//        String email = oauth2.getPrincipal().getAttribute("email");
//        String password = Long.toHexString(System.currentTimeMillis());
//
//        UserDetails user = User.withUsername(email)
//                .password(pe.encode(password)).roles("STAF").build();
//        Authentication auth = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(auth);
//    }
}
