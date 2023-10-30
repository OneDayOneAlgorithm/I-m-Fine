package com.ssafy.algoarium.RecommendProblem;

import com.ssafy.algoarium.Problem.ProblemEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendProblemDTO {
//    private Integer recommendProblemId; // 필드명 수정
//    private Integer problemId;
//    private Integer userEntity; // 필드명 수정
    private Integer problemId;
    private Integer userId;
    private Integer problemNumber; // problemNumber 필드 추가
    private String title; // title 필드 추가
    private String problemTag; // problemTag 필드 추가
    private Integer problemLevel; // problemLevel 필드 추가
    private Integer solvedUserCount; // solvedUserCount 필드 추가
    private Boolean likeType;
    private String memo;
}
