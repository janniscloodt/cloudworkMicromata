package com.oskarwiedeweg.cloudwork.auth;

import com.oskarwiedeweg.cloudwork.user.UserService;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Data
@Service
public class AppUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserUserDetails(userService
                .getUserByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username '%s' not found!".formatted(username))));
    }

}
