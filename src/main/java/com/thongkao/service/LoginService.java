package com.thongkao.service;

import com.thongkao.entity.AppRule;
import com.thongkao.entity.AppUser;
import com.thongkao.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        AppUser user = appUserRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException(username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (AppRule role : user.getAppRules()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRuleName()));
        }

        return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
