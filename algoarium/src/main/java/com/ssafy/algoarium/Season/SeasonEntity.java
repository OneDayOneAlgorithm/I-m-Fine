package com.ssafy.algoarium.Season;

import com.ssafy.algoarium.User.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "season")
public class SeasonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "season_id", nullable = false)
    private Long seasonId;

    @Column(name = "promotion_season", nullable = false, length = 100)
    private Boolean promotionSeason = false;

    @Builder
    public SeasonEntity(Long seasonId , Boolean promotionSeason) {
        this.seasonId = seasonId;
        this.promotionSeason = promotionSeason;
    }
}
