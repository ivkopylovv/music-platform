package com.kopylov.musicplatform.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NonNull
    private String username;

    @NonNull
    private String password;

    private String firstName;
    private String lastName;
    private String imageName;

    @ManyToMany(fetch = EAGER)
    private Set<Role> roles = new HashSet<>();

}
