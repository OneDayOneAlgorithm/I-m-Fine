package com.ssafy.algoarium.RecommendProblem;

import com.ssafy.algoarium.Problem.ProblemEntity;
import com.ssafy.algoarium.User.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "recommend_problem")
public class RecommendProblemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommend_problem_id")
    private Integer recommendProblemId;

    @ManyToOne
    @JoinColumn(name = "problem_id", referencedColumnName = "problemId")
    private ProblemEntity problemEntity;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserEntity userEntity; // userId 필드명 수정

    @Column(name = "type", length = 10)
    private Integer type;
}
