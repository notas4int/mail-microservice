package org.artem.projects.mailregistry.services;


import lombok.RequiredArgsConstructor;
import org.artem.projects.mailregistry.models.SecurityUserDetails;
import org.artem.projects.mailregistry.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var account = repository.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new SecurityUserDetails(account);
    }
}
