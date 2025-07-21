package com.Saad.JournalApp.Service;

import com.Saad.JournalApp.Entity.UserEntry;
import com.Saad.JournalApp.Repository.UserEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserEntryRepository userEntryRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntry userEntry =userEntryRepository.findByUsername(username);
        if(userEntry!=null){
            UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                    .username(userEntry.getUsername())
                    .password(userEntry.getPassword())
                    .roles(userEntry.getRoles().toArray(new String[0]))
                    .build();
            return userDetails;
        }
        throw new UsernameNotFoundException("User not found");
    }
}