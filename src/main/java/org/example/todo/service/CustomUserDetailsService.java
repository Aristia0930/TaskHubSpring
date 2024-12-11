package org.example.todo.service;

import org.example.todo.entity.CustomUserDetails;
import org.example.todo.entity.User;
import org.example.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        User user = userRepository.findByUserId(userId);
        if(user==null){

            throw new UsernameNotFoundException("User not found with id: " + userId);

        }


        return new CustomUserDetails(user);
    }
}
