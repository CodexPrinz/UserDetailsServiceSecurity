package com.example.UserDetailsServiceSecurity.service.impl;

import com.example.UserDetailsServiceSecurity.entity.User;
import com.example.UserDetailsServiceSecurity.repository.UserRepository;
import com.example.UserDetailsServiceSecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

            Optional<User> opt = userRepository.findByEmail(email);

            org.springframework.security.core.userdetails.User springUser=null;

            if(opt.isEmpty()) {
                throw new UsernameNotFoundException("User with email: " +email +" not found");
            }
            User user =opt.get();
            List<String> roles = user.getRoles();
            Set<GrantedAuthority> ga = new HashSet<>();
            for(String role:roles) {
                ga.add(new SimpleGrantedAuthority(role));
            }

            springUser = new org.springframework.security.core.userdetails.User(
                    email,
                    user.getPassword(),
                    ga );
            return springUser;
        }

    public Long saveUser(User user){
        User u = new User();
        u.setEmail(user.getEmail());
        u.setUsername(user.getUsername());
        String pw = bCryptPasswordEncoder.encode(user.getPassword());

        User newUser= userRepository.save(u);
        return newUser.getId();
    }
}
