package com.example.demo.configurations;

import com.example.demo.services.UserPrincipalDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserPrincipalDetailsService userPrincipleDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userPrincipleDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
		auth.authenticationProvider(daoAuthenticationProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/login",
						"/register", 
						"/forgotPassword",
						"/resetPassword/**",
						"/updatePassword",
						"/hello/**",
						"/css/**",
						"/images/**", 
						"/js/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin().loginPage("/login");
	}
}




// @Configuration
// // @EnableWebSecurity
// public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

// 	@Autowired
// 	UserDetailsService userDetailService;

// 	@Bean
// 	public AuthenticationProvider authProvider() {
// 		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
// 		provider.setUserDetailsService(userDetailService);
// 		provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
// 		return provider;
// 	}

// 	@Override
// 	protected void configure(HttpSecurity http) throws Exception {
// 		http
// 			.csrf().disable()
// 			.authorizeRequests()
// 			.antMatchers("/login", "/css/**", "/images/**").permitAll()
// 			.anyRequest().permitAll()
// 			// .anyRequest().authenticated()
// 			.and()
// 			.formLogin().loginPage("/login").permitAll()
// 			.and()
// 			.logout().invalidateHttpSession(true).clearAuthentication(true).logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
// 			.logoutSuccessUrl("/logoutSuccess").permitAll();
// 	}

// 	// @Bean
// 	// @Override
// 	// protected UserDetailsService userDetailsService() {
// 	// 	List<UserDetails> userDetails = new ArrayList<>();
// 	// 	userDetails.add(User.withDefaultPasswordEncoder().username("imran").password("imran").roles("ADMIN").build());
// 	// 	return new InMemoryUserDetailsManager(userDetails);
// 	// }
// }