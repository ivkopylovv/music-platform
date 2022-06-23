package com.kopylov.musicplatform.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

import static com.kopylov.musicplatform.constants.DateFormat.TOKEN_EXPIRATION_FORMAT;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NonNull
    private String token;

    @NonNull
    @DateTimeFormat(pattern = TOKEN_EXPIRATION_FORMAT)
    private Date expirationDate;

    @OneToOne(fetch = EAGER, cascade = ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
