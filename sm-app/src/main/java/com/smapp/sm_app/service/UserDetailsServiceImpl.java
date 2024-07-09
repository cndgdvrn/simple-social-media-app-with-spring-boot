package com.smapp.sm_app.service;

import com.smapp.sm_app.security.CurrentUser;
import com.smapp.sm_app.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(username);
        return new CurrentUser(user);
    }

    public UserDetails loadUserById(Long id) {
        User user = userService.getUserById(id);
        return new CurrentUser(user);
    }
}
