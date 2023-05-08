package com.leaguebabe.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Champion {

    @Id
    private String name;
    private String title;
    private String blurb;
    private String profilePictureUrl;
    private String[] tags;
}
