package com.ssafy.algoarium.Season;

import com.ssafy.algoarium.User.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeasonDTO {
    private Long seasonId;
    private Boolean promotionSeason;
}
