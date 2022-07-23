package com.homion.springsecurityjwt.services.MyUserDetailsService;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);

        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();

        if (username.equals("student")) {
            authorities.add(new SimpleGrantedAuthority("CAN_VISIT_HOME"));
            return new User("student", "student", authorities);

        } else if (username.equals("admin")) {
            authorities.add(new SimpleGrantedAuthority("CAN_VISIT_HOME"));
            authorities.add(new SimpleGrantedAuthority("CAN_VISIT_DETAILS"));
            authorities.add(new SimpleGrantedAuthority("CAN_VISIT_ADMIN"));
            return new User("admin", "admin", authorities);
        } else throw new UsernameNotFoundException("Could not find user");
    }
}
