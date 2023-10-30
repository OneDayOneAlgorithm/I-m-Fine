// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

package com.ssafy.algoarium.UserStatus;
import java.util.List;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserStatusResponse {

    @JsonProperty("rating")
    private int rating;
    @JsonProperty("solvedCount")
    private int solvedCount;
    @JsonProperty("maxStreak")
    private int maxStreakCount; // 속성 이름 변경

    @JsonProperty("stardusts")
    private int stardustsCount; // 속성 이름 변경

    @JsonProperty("ratingBySolvedCount")
    private int ratingBySolvedCount;

}
