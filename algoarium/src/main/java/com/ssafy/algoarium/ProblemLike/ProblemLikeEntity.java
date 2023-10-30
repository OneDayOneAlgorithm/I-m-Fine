package com.ssafy.algoarium.ProblemLike;

import com.ssafy.algoarium.Problem.ProblemEntity;
import com.ssafy.algoarium.User.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "problem_like")
@Getter
@Setter
public class ProblemLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_like_id")
    private Integer problemLikeId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "problem_id", referencedColumnName = "problemId")
    private ProblemEntity problemEntity;

    @Column(name = "like_type", length = 10)
    private Boolean likeType = false;

    @Column(name = "memo", length = 100000)
    private String memo;

    public ProblemLikeEntity() {}

    public ProblemLikeEntity(ProblemEntity problemEntity, UserEntity userEntity) {
        this.problemEntity = problemEntity;
        this.userEntity = userEntity;
    }

}
