package com.ssafy.algoarium.BaekjoonUser;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaekjoonUserDTO {
    private String bjId;
    private Integer solvedCount;
    private Integer bjClass;
    private Integer tier;
    private Integer rating;
    private Integer ratingByProblemSum;
    private Integer ratingByClass;
    private Integer ratingBySolvedCount;
    private Integer rank;

    public static BaekjoonUserEntity toBaekjoonUserEntity(BaekjoonUserDTO baekjoonUserDTO) {
        return BaekjoonUserEntity.builder()
                .bjId(baekjoonUserDTO.getBjId())
                .solvedCount(baekjoonUserDTO.getSolvedCount())
                .bjClass(baekjoonUserDTO.getBjClass())
                .tier(baekjoonUserDTO.getTier())
                .rating(baekjoonUserDTO.getRating())
                .ratingByProblemSum(baekjoonUserDTO.getRatingByProblemSum())
                .ratingByClass(baekjoonUserDTO.getRatingByClass())
                .ratingBySolvedCount(baekjoonUserDTO.getRatingBySolvedCount())
                .rank(baekjoonUserDTO.getRank())
                .build();
    }
}
