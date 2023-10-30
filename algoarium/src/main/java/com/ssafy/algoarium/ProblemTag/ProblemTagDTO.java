// ProblemTagDTO
// 이 클래스는 ProblemTagEntity의 데이터를 전달하기 위한 DTO(Data Transfer Object)입니다.
// problem_tag_id는 problem_tag 테이블의 고유한 식별자를 나타냅니다.
// problem_id는 problem_tag 테이블의 problem_id 컬럼을 나타냅니다.
// tag_id는 problem_tag 테이블의 tag_id 컬럼을 나타냅니다.

package com.ssafy.algoarium.ProblemTag;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemTagDTO {
    private Integer problemTagId; // problem_tag 테이블의 고유한 식별자
    private Integer problemId; // problem_tag 테이블의 problem_id
    private Integer tagId; // problem_tag 테이블의 tag_id
}
