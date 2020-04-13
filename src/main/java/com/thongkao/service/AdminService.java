package com.thongkao.service;

import com.thongkao.entity.AppRule;
import com.thongkao.entity.AppUser;
import com.thongkao.model.User;
import com.thongkao.repository.AppRuleRepository;
import com.thongkao.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AdminService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private AppRuleRepository appRuleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createUser(User user) {
        AppUser appUser = new AppUser();
        appUser.setName(user.getName());
        appUser.setUsername(user.getUsername());
        appUser.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<AppRule> appRuleSet = new HashSet<>();
        user.getRules().forEach(rule -> {
            appRuleSet.add(appRuleRepository.findByRuleName(rule));
        });
        appUser.setAppRules(appRuleSet);
        appUserRepository.save(appUser);
    }

    public void mockAppRule() {
        AppRule appRule = new AppRule();
        appRule.setRuleName("ADMIN");
        appRuleRepository.save(appRule);
    }
}
