package com.ssafy.algoarium.ProblemTag;

import com.ssafy.algoarium.Problem.ProblemEntity;
import com.ssafy.algoarium.Tag.TagEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "problem_tag")
@Getter
public class ProblemTagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_tag_id")
    private Integer problemTagId;

    @ManyToOne
    @JoinColumn(name = "problem_id", referencedColumnName = "problemId")
    private ProblemEntity problemEntity;

    @ManyToOne
    @JoinColumn(name = "tag_id", referencedColumnName = "tagId") // "tag_id"는 problem_tag 테이블의 컬럼명, "tagId"는 TagEntity 클래스의 필드명입니다.
    private TagEntity tagEntity;


    public ProblemTagEntity() {}

    public ProblemTagEntity(ProblemEntity problemEntity, TagEntity tagEntity) {
        this.problemEntity = problemEntity;
        this.tagEntity = tagEntity;
    }
}
