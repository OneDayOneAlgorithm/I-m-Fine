package com.ssafy.algoarium.SolvedProblemHistory;

import com.ssafy.algoarium.Problem.ProblemEntity;
import com.ssafy.algoarium.User.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "solved_problem_history")
public class SolvedProblemHistoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long solvedProblemHistoryId;

	@Column(name = "solved_or_not")
	private Boolean solvedOrNot = false;

	// 보강 필요
	@ManyToOne // 다대일 관계 설정
	@JoinColumn(name = "user_id", referencedColumnName = "user_id") // 연관되는 컬럼 지정
	private UserEntity user;

	@ManyToOne // 다대일 관계 설정
	@JoinColumn(name = "problem_id", referencedColumnName = "problemId") // 연관되는 컬럼 지정
	private ProblemEntity problem;

	@Builder
	public SolvedProblemHistoryEntity(Long solvedProblemHistoryId, boolean solvedOrNot, UserEntity userEntity, ProblemEntity problemEntity){
		this.solvedProblemHistoryId = solvedProblemHistoryId;
		this.solvedOrNot = solvedOrNot;
		this.user = userEntity;
		this.problem = problemEntity;
	}
}
