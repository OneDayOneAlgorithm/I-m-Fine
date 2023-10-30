package com.ssafy.algoarium.RecommendProblem;

import com.ssafy.algoarium.Problem.ProblemService;
import com.ssafy.algoarium.ProblemLike.ProblemLikeDTO;
import com.ssafy.algoarium.ProblemLike.ProblemLikeService;
import com.ssafy.algoarium.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recommend")
public class RecommendProblemController {

    private final RecommendProblemService recommendProblemService;
    private final ProblemLikeService problemLikeService;
    private final ProblemService problemService; // 추가
    private final UserService userService; // 추가

    @Autowired
    public RecommendProblemController(RecommendProblemService recommendProblemService, ProblemLikeService problemLikeService, ProblemService problemService, UserService userService) {
        this.recommendProblemService = recommendProblemService;
        this.problemLikeService = problemLikeService;
        this.problemService = problemService;
        this.userService = userService;
    }

    @GetMapping("/strong/{userId}")
    public List<RecommendProblemDTO> getStrongRecommendations(@PathVariable Long userId) {
        return recommendProblemService.getStrongRecommendations(Math.toIntExact(userId));
    }

    @GetMapping("/weak/{userId}")
    public List<RecommendProblemDTO> getWeakRecommendations(@PathVariable Long userId) {
        return recommendProblemService.getWeakRecommendations(Math.toIntExact(userId));
    }

    @GetMapping("/similar/{userId}")
    public List<RecommendProblemDTO> getSimilarRecommendations(@PathVariable Long userId) {
        return recommendProblemService.getSimilarRecommendations(Math.toIntExact(userId));
    }
}
