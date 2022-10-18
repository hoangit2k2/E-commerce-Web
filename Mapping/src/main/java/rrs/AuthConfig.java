package rrs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import rrs.model.services.AuthService;

@Configuration
@SuppressWarnings("deprecation")
public class AuthConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired private AuthService user;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(user);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().disable(); // _csrf -> code... so we need to disabled to make easier login

		// ____________________________________________________________ page accessibility
		http // allow request page authenticated
			.authorizeRequests()
        	.antMatchers("/**").permitAll()
        	.anyRequest().authenticated(); // have login
		http.exceptionHandling().accessDeniedPage("/security/deniedPage");
		
		// ____________________________________________________________ form configuration
		http // form login
	 		.formLogin()
			.loginProcessingUrl("/security/login") // default [/login] => process the submitted credential
			.loginPage("/security/loginForm") // form display to login - this post method
	 		.defaultSuccessUrl("/security/loginSuccess", true)
			.failureForwardUrl("/security/loginFailed"); // login failed

        http// logout
			.logout()
			.logoutUrl("/security/logout") // default [/logout]
			.logoutSuccessUrl("/security/logoutSuccess");

	}
	
}
