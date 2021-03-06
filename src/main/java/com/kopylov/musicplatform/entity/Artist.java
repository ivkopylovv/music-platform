package com.kopylov.musicplatform.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Artist {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NonNull
    private String name;
    private String imageName;


}
