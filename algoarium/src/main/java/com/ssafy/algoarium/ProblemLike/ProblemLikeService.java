// ProblemLikeService

package com.ssafy.algoarium.ProblemLike;

import com.ssafy.algoarium.Problem.ProblemEntity;
import com.ssafy.algoarium.RecommendProblem.RecommendProblemDTO;
import com.ssafy.algoarium.User.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProblemLikeService {

    private final ProblemLikeRepository problemLikeRepository;

    @Autowired
    public ProblemLikeService(ProblemLikeRepository problemLikeRepository) {
        this.problemLikeRepository = problemLikeRepository;
    }

    public ProblemLikeEntity likeProblem(ProblemEntity problemEntity, UserEntity userEntity, Boolean likeType) {
        ProblemLikeEntity problemLikeEntity = new ProblemLikeEntity(problemEntity, userEntity);
        problemLikeEntity.setLikeType(likeType);

        return problemLikeRepository.save(problemLikeEntity);
    }

    public ProblemLikeEntity getProblemLikeByProblemAndUser(ProblemEntity problemEntity, UserEntity userEntity) {
        return problemLikeRepository.findByProblemEntityAndUserEntity(problemEntity, userEntity);
    }

    public ProblemLikeEntity updateProblemLike(ProblemLikeEntity problemLikeEntity) {
        return problemLikeRepository.save(problemLikeEntity);
    }

    public ProblemLikeEntity createMemo(ProblemEntity problemEntity, UserEntity userEntity, String memo) {
        ProblemLikeEntity problemLikeEntity = new ProblemLikeEntity(problemEntity, userEntity);
        problemLikeEntity.setMemo(memo);

        return problemLikeRepository.save(problemLikeEntity);
    }

    public ProblemLikeEntity createLike(ProblemEntity problemEntity, UserEntity userEntity, Boolean likeType) {
        ProblemLikeEntity problemLikeEntity = new ProblemLikeEntity(problemEntity, userEntity);
        problemLikeEntity.setLikeType(likeType);
        return problemLikeRepository.save(problemLikeEntity);
    }

    // ProblemEntity와 UserEntity에 해당하는 메모와 좋아요 여부 가져오기
    public ProblemLikeDTO getProblemLikeInfo(ProblemEntity problemEntity, UserEntity userEntity) {
        ProblemLikeEntity problemLikeEntity = problemLikeRepository.findByProblemEntityAndUserEntity(problemEntity, userEntity);
        if (problemLikeEntity != null) {
            return ProblemLikeDTO.builder()
                    .likeType(problemLikeEntity.getLikeType())
                    .memo(problemLikeEntity.getMemo())
                    .build();
        } else {
            // 해당하는 데이터가 없을 경우 처리
            return ProblemLikeDTO.builder()
                    .likeType(false)
                    .memo(null)
                    .build();
        }
    }

    public List<RecommendProblemDTO> getLikedProblem(Long userId) {
        // ProblemLike 정보 가져오기
        List<ProblemLikeEntity> likedEntities = problemLikeRepository.findByUserEntityUserIdAndLikeTypeIsTrue(userId);

        List<RecommendProblemDTO> likedProblems = likedEntities.stream()
                .map(likeEntity -> {
                    ProblemEntity problemEntity = likeEntity.getProblemEntity();

                    // Create a RecommendProblemDTO
                    RecommendProblemDTO recommendProblemDTO = new RecommendProblemDTO();
                    recommendProblemDTO.setProblemId(problemEntity.getProblemId());
                    recommendProblemDTO.setUserId(userId.intValue());

                    // Populate other fields from problemEntity
                    recommendProblemDTO.setProblemNumber(problemEntity.getProblemNumber());
                    recommendProblemDTO.setTitle(problemEntity.getTitle());
                    recommendProblemDTO.setProblemTag(problemEntity.getProblemTag());
                    recommendProblemDTO.setProblemLevel(problemEntity.getProblemLevel());
                    recommendProblemDTO.setSolvedUserCount(problemEntity.getSolvedUserCount());

                    // Set likeType and memo from likeEntity
                    recommendProblemDTO.setLikeType(likeEntity.getLikeType());
                    recommendProblemDTO.setMemo(likeEntity.getMemo());

                    return recommendProblemDTO;
                })
                .sorted(Comparator.comparingInt(RecommendProblemDTO::getProblemNumber))
                .collect(Collectors.toList());

        return likedProblems;
    }

    public String get_memo(Long userID, Integer problemID) {
        // Parse problemID to Integer (assuming problemID is an Integer)
        Integer problemId = problemID;

        // Find the ProblemLikeEntity by user ID and problem ID
        ProblemLikeEntity problemLikeEntity = problemLikeRepository.findByUserEntityUserIdAndProblemEntityProblemId(userID, problemId);

        // Check if the ProblemLikeEntity is not null and has a memo
        if (problemLikeEntity != null && problemLikeEntity.getMemo() != null) {
            return problemLikeEntity.getMemo();
        } else {
            // Return a default message or handle the case when memo is not found
            return "";
        }
    }

}
