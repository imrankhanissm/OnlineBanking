package com.example.demo.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.example.demo.models.UserModel;
import com.example.demo.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserModel userModel = userRepository.findByUsername(username);
		if(userModel == null)	throw new UsernameNotFoundException("user 404");
		return new UserPrincipal(userModel);
	}
}

class UserPrincipal implements UserDetails {
	
	UserModel userModel;

	public UserPrincipal(UserModel userModel) {
		this.userModel = userModel;
	}	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> list = new ArrayList<>();
		list.add(new SimpleGrantedAuthority("ADMIN"));
		return list;
	}

	@Override
	public String getPassword() {
		return userModel.getPassword();
	}

	@Override
	public String getUsername() {
		return userModel.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}