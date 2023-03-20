package com.linker.springlinker.services;

import com.linker.springlinker.models.User;
import com.linker.springlinker.repos.UserRepository;
import com.linker.springlinker.services.details.AppUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User doesn't exist");
        }
        return new AppUserDetails(user);
    }

    public User loadUserById(Long userId) throws UsernameNotFoundException {
        User user = userRepository.findById(userId).get();
        if (user == null) {
            throw new UsernameNotFoundException("User doesn't exist");
        }
        return user;
    }

    public String createUser(User user) {
        if(userRepository.findByUsername(user.getUsername()) != null) {
            return "Username has already been used";
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
        return "Created";
    }

}
