package com.r00t.cifs.services;

import com.r00t.cifs.models.UserModel;
import com.r00t.cifs.principals.UserPrincipal;
import com.r00t.cifs.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserModel temp = repository.findByUsername(s);

        if (temp != null)
            return new UserPrincipal(temp);
        else
            throw new UsernameNotFoundException("User not found");
    }
}