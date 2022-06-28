package com.kopylov.musicplatform.entity.compositekey;

import com.kopylov.musicplatform.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

import static javax.persistence.FetchType.EAGER;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistId implements Serializable {
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String title;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaylistId)) return false;
        PlaylistId that = (PlaylistId) o;
        return user != null && user.equals(that.user) &&
                title != null && title.equals(that.title);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
