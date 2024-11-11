package com.shopme.admin.security;

import com.shopme.admin.repository.UserRepository;
import com.shopme.commom.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ShopmeUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.getUserByEmail(email);
        if (user != null) {
            return new ShopmeUserDetails(user);
        }
        throw new UsernameNotFoundException("Could not find user with email- " + email);
    }
}
