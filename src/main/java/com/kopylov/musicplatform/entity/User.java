package com.kopylov.musicplatform.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @NonNull
    private String username;

    @NonNull
    private String password;

    private String firstName;
    private String lastName;
    private String userImage;

    @ManyToMany(fetch = EAGER, cascade = ALL)
    private Set<Role> roles = new HashSet<>();

}
