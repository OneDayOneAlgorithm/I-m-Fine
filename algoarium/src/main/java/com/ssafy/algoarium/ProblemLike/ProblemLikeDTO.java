package com.ssafy.algoarium.ProblemLike;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemLikeDTO {
    private Integer problemLikeId;
    private Integer userEntity;
    private Integer problemEntity;
    private Boolean likeType;
    private String memo;
    private Integer problemId;
    private Integer userId;
    private Integer problemNumber; // problemNumber 필드 추가
    private String title; // title 필드 추가
    private String problemTag; // problemTag 필드 추가
    private Integer problemLevel; // problemLevel 필드 추가
    private Integer solvedUserCount; // solvedUserCount 필드 추가

    public Integer getProblemId() {
        return this.problemId;
    }

    public Integer getUserId() {
        return this.userId;
    }
}

