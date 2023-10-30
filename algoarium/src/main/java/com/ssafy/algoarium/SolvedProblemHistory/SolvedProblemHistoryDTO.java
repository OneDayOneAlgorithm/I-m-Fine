package com.ssafy.algoarium.SolvedProblemHistory;

import com.ssafy.algoarium.Problem.ProblemEntity;
import com.ssafy.algoarium.User.UserEntity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SolvedProblemHistoryDTO {

	private Long solvedProblemHistoryId;
	private Boolean solvedOrNot;
	private UserEntity user;
	private ProblemEntity problem;
	

}
