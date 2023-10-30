package com.ssafy.algoarium.RecommendProblem;

import com.ssafy.algoarium.Problem.ProblemEntity;
import com.ssafy.algoarium.Problem.ProblemService;
import com.ssafy.algoarium.ProblemLike.ProblemLikeDTO;
import com.ssafy.algoarium.ProblemLike.ProblemLikeService;
import com.ssafy.algoarium.User.UserEntity;
import com.ssafy.algoarium.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendProblemService {

    private final RecommendProblemRepository recommendProblemRepository;
    private final ProblemLikeService problemLikeService;

    @Autowired
    public RecommendProblemService(RecommendProblemRepository recommendProblemRepository, ProblemLikeService problemLikeService) {
        this.recommendProblemRepository = recommendProblemRepository;
        this.problemLikeService = problemLikeService;
    }

    public List<RecommendProblemDTO> getStrongRecommendations(Integer userId) {
        List<RecommendProblemEntity> strongRecommendations = recommendProblemRepository.findStrongRecommendations(userId);

        List<RecommendProblemDTO> enrichedRecommendations = new ArrayList<>();
        for (RecommendProblemEntity recommendation : strongRecommendations) {
            ProblemEntity problemEntity = recommendation.getProblemEntity();
            UserEntity userEntity = recommendation.getUserEntity();

            // ProblemLike 정보 가져오기
            ProblemLikeDTO problemLikeInfo = problemLikeService.getProblemLikeInfo(problemEntity, userEntity);

            // Recommendation 정보를 DTO로 변환
            RecommendProblemDTO recommendProblemDTO = new RecommendProblemDTO();
            recommendProblemDTO.setProblemId(problemEntity.getProblemId());
            recommendProblemDTO.setProblemNumber(problemEntity.getProblemNumber());
            recommendProblemDTO.setTitle(problemEntity.getTitle());
            recommendProblemDTO.setProblemTag(problemEntity.getProblemTag());
            recommendProblemDTO.setProblemLevel(problemEntity.getProblemLevel());
            recommendProblemDTO.setSolvedUserCount(problemEntity.getSolvedUserCount());
            recommendProblemDTO.setUserId(Math.toIntExact(userEntity.getUserId()));
            recommendProblemDTO.setLikeType(problemLikeInfo.getLikeType());
            recommendProblemDTO.setMemo(problemLikeInfo.getMemo());

            enrichedRecommendations.add(recommendProblemDTO);
        }

        return enrichedRecommendations;
    }


    public List<RecommendProblemDTO> getWeakRecommendations(Integer userId) {
        List<RecommendProblemEntity> strongRecommendations = recommendProblemRepository.findWeakRecommendations(userId);

        List<RecommendProblemDTO> enrichedRecommendations = new ArrayList<>();
        for (RecommendProblemEntity recommendation : strongRecommendations) {
            ProblemEntity problemEntity = recommendation.getProblemEntity();
            UserEntity userEntity = recommendation.getUserEntity();

            // ProblemLike 정보 가져오기
            ProblemLikeDTO problemLikeInfo = problemLikeService.getProblemLikeInfo(problemEntity, userEntity);

            // Recommendation 정보를 DTO로 변환
            RecommendProblemDTO recommendProblemDTO = new RecommendProblemDTO();
            recommendProblemDTO.setProblemId(problemEntity.getProblemId());
            recommendProblemDTO.setProblemNumber(problemEntity.getProblemNumber());
            recommendProblemDTO.setTitle(problemEntity.getTitle());
            recommendProblemDTO.setProblemTag(problemEntity.getProblemTag());
            recommendProblemDTO.setProblemLevel(problemEntity.getProblemLevel());
            recommendProblemDTO.setSolvedUserCount(problemEntity.getSolvedUserCount());
            recommendProblemDTO.setUserId(Math.toIntExact(userEntity.getUserId()));
            recommendProblemDTO.setLikeType(problemLikeInfo.getLikeType());
            recommendProblemDTO.setMemo(problemLikeInfo.getMemo());

            enrichedRecommendations.add(recommendProblemDTO);
        }

        return enrichedRecommendations;
    }

    public List<RecommendProblemDTO> getSimilarRecommendations(Integer userId) {
        List<RecommendProblemEntity> strongRecommendations = recommendProblemRepository.findSimilarRecommendations(userId);

        List<RecommendProblemDTO> enrichedRecommendations = new ArrayList<>();
        for (RecommendProblemEntity recommendation : strongRecommendations) {
            ProblemEntity problemEntity = recommendation.getProblemEntity();
            UserEntity userEntity = recommendation.getUserEntity();

            // ProblemLike 정보 가져오기
            ProblemLikeDTO problemLikeInfo = problemLikeService.getProblemLikeInfo(problemEntity, userEntity);

            // Recommendation 정보를 DTO로 변환
            RecommendProblemDTO recommendProblemDTO = new RecommendProblemDTO();
            recommendProblemDTO.setProblemId(problemEntity.getProblemId());
            recommendProblemDTO.setProblemNumber(problemEntity.getProblemNumber());
            recommendProblemDTO.setTitle(problemEntity.getTitle());
            recommendProblemDTO.setProblemTag(problemEntity.getProblemTag());
            recommendProblemDTO.setProblemLevel(problemEntity.getProblemLevel());
            recommendProblemDTO.setSolvedUserCount(problemEntity.getSolvedUserCount());
            recommendProblemDTO.setUserId(Math.toIntExact(userEntity.getUserId()));
            recommendProblemDTO.setLikeType(problemLikeInfo.getLikeType());
            recommendProblemDTO.setMemo(problemLikeInfo.getMemo());

            enrichedRecommendations.add(recommendProblemDTO);
        }

        return enrichedRecommendations;
    }
}
