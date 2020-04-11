package com.thongkao.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"appRules"})
@Table(name = "APP_USER")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "APP_USER_RULE",
            joinColumns = { @JoinColumn(name = "APP_USER_ID") },
            inverseJoinColumns = { @JoinColumn(name = "APP_RULE_ID") })
    private Set<AppRule> appRules = new HashSet<>();
}
