package com.ssafy.algoarium.Problem;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

/**
 * ProblemDTO 클래스는 문제 정보를 전달하는 데이터 전송 객체입니다.
 */
public class ProblemDTO {
    private Integer problemId; // 문제 고유 식별자
    private Integer problemNumber; // 문제 번호
    private String title; // 문제 제목
    private String problemTag; // 문제 태그
    private Integer problemLevel; // 문제 난이도
    private Integer solvedUserCount; // 해결한 사용자 수

    /**
     * ProblemDTO 객체를 ProblemEntity 객체로 변환합니다.
     *
     * @param problemDTO 변환할 ProblemDTO 객체
     * @return ProblemEntity 객체
     */
    public static ProblemEntity toProblemEntity(ProblemDTO problemDTO) {
        return ProblemEntity.builder()
                .problemNumber(problemDTO.getProblemNumber())
                .title(problemDTO.getTitle())
                .problemTag(problemDTO.getProblemTag())
                .problemLevel(problemDTO.getProblemLevel())
                .solvedUserCount(problemDTO.getSolvedUserCount())
                .build();
    }
}
