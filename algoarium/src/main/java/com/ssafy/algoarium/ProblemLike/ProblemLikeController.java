// ProblemLikeController

package com.ssafy.algoarium.ProblemLike;

import com.ssafy.algoarium.Problem.ProblemEntity;
import com.ssafy.algoarium.Problem.ProblemService;
import com.ssafy.algoarium.RecommendProblem.RecommendProblemDTO;
import com.ssafy.algoarium.User.UserEntity;
import com.ssafy.algoarium.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProblemLikeController {

    private final ProblemLikeService problemLikeService;
    private final ProblemService problemService; // 추가
    private final UserService userService; // 추가

    @Autowired
    public ProblemLikeController(ProblemLikeService problemLikeService, ProblemService problemService, UserService userService) {
        this.problemLikeService = problemLikeService;
        this.problemService = problemService;
        this.userService = userService;
    }

    @PostMapping("/api/problemLikes/updateMemo")
    public ProblemLikeEntity set_update_Memo(@RequestBody ProblemLikeDTO problemLikeDTO) {
        Integer problemId = problemLikeDTO.getProblemId();
        Integer userId = problemLikeDTO.getUserId();
        String memo = problemLikeDTO.getMemo();

        ProblemEntity problemEntity = problemService.getProblemById(problemId);
        UserEntity userEntity = userService.getUserById(userId);

        ProblemLikeEntity existingLike = problemLikeService.getProblemLikeByProblemAndUser(problemEntity, userEntity);

        if (existingLike != null) {
            existingLike.setMemo(memo);

            return problemLikeService.updateProblemLike(existingLike);
        }

        return problemLikeService.createMemo(problemEntity, userEntity, memo);
    }

    @PostMapping("/recommend/like")
    public ProblemLikeEntity set_update_like(@RequestBody ProblemLikeDTO problemLikeDTO) {
        Integer problemId = problemLikeDTO.getProblemId();
        Integer userId = problemLikeDTO.getUserId();
        Boolean likeType = problemLikeDTO.getLikeType();

        ProblemEntity problemEntity = problemService.getProblemById(problemId);
        UserEntity userEntity = userService.getUserById(userId);

        ProblemLikeEntity existingLike = problemLikeService.getProblemLikeByProblemAndUser(problemEntity, userEntity);

        if (existingLike != null) {
            existingLike.setLikeType(likeType);

            return problemLikeService.updateProblemLike(existingLike);
        }

        return problemLikeService.createLike(problemEntity, userEntity, likeType);
    }

    @GetMapping("/my/like/{userId}")
    public List<RecommendProblemDTO> get_liked_problem(@PathVariable Long userId){
        return problemLikeService.getLikedProblem(userId);
    }

    @GetMapping("/api/problemLikes/getMemo/{userID}/{problemID}")
    public String getMemo(@PathVariable Long userID, @PathVariable Integer problemID){
        return problemLikeService.get_memo(userID,problemID);
    }
}
