package com.ssafy.algoarium.SolvedProblemHistory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.algoarium.Problem.ProblemService;
import com.ssafy.algoarium.User.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/solved")
public class SolvedProblemHistoryController {

	private final SolvedProblemHistoryService solvedProblemHistoryService;
	private final UserService userService;
	private final ProblemService problemService;

	@PostMapping("/{userId}/{problemId}/{solve}")
	public void saveSolved(@PathVariable Long userId, @PathVariable int problemId, @PathVariable Long solve){
		boolean solved = false;
		if(solve != 0){
			solved =true;
		}
		 solvedProblemHistoryService.saveSolvedProbelm(SolvedProblemHistoryDTO.builder()
			 .user(userService.getUserById(userId))
			 .problem(problemService.getProblemById(problemId))
			 .solvedOrNot(solved)
			 .build());
	}

	@GetMapping("/{baekjoonUser}")
	public void browseProblem(@PathVariable String baekjoonUser){
		solvedProblemHistoryService.saveBaekjoonId(baekjoonUser);
	}


}
