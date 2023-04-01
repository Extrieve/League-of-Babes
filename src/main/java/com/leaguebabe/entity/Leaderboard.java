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
public class Leaderboard {

    @Id
    private String id;
    private String name;
    private String imageUrl;
    private int score;
}
