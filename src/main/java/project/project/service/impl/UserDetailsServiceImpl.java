package project.project.service.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import project.project.model.UserDetailsT;
import project.project.model.enums.UserRoles;
import project.project.repository.UserRepository;
import project.project.model.entity.UserEntity;

import java.util.List;
import java.util.Optional;


public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .map(UserDetailsServiceImpl::map)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found!"));
    }

    private static UserDetails map(UserEntity userEntity){
        return new UserDetailsT(
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getRole().stream()
                        .map(e -> e.getUserRole()
                                .map(UserDetailsServiceImpl::map())));
    }

    private static GrantedAuthority map(UserRoles role ){
        return new SimpleGrantedAuthority("ROLE_" + role);
    }
}
