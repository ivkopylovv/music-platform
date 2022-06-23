package com.kopylov.musicplatform.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddSongToLikedDTO {
    private Long songId;
    private String username;
    private Date addedDate;
}
