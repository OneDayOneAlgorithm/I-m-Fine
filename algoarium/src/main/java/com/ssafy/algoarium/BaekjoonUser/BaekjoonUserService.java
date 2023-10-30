package com.ssafy.algoarium.BaekjoonUser;

import com.ssafy.algoarium.User.UserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class BaekjoonUserService {
    @Autowired
    private BaekjoonUserRepository baekjoonUserRepository;
    @Autowired
    private UserRepository userRepository; // UserRepository 주입

    @Transactional
    public void fetchUserAndSaveToDatabase(String bjId) {
        // 유저 조회 URL
        String apiUrl = "https://solved.ac/api/v3/user/show?handle=";

        // RestTemplate을 사용하여 API 호출
        RestTemplate restTemplate = new RestTemplate();
        String apiResponse = restTemplate.getForObject(apiUrl + bjId, String.class);

        try {
            // API 응답을 DTO로 변환
            BaekjoonUserDTO baekjoonUserDTO = convertApiResponseToDTO(apiResponse);

            // bjId를 사용하여 이미 존재하는 엔터티를 찾습니다.
            BaekjoonUserEntity existingUserEntity = baekjoonUserRepository.findByBjId(bjId);

            // 만약 이미 존재하는 엔터티가 있다면 업데이트, 아니면 새로운 엔터티를 생성합니다.
            if (existingUserEntity != null) {
                updateExistingEntity(existingUserEntity, baekjoonUserDTO);
            } else {
                saveNewEntity(baekjoonUserDTO);
            }
        } catch (Exception e) {
            // 에러가 발생하면 예외 처리
            e.printStackTrace(); // 에러 로그 출력 (나중에 수정하세요)
        }
    }


    @Transactional
    public String fetchBio(String bjId) {
        // 유저 조회 URL
        String apiUrl = "https://solved.ac/api/v3/user/show?handle=";

        // RestTemplate을 사용하여 API 호출
        RestTemplate restTemplate = new RestTemplate();
        String apiResponse = restTemplate.getForObject(apiUrl + bjId, String.class);

        // JSON 문자열을 객체로 변환
        JSONObject jsonObject = new JSONObject(apiResponse);

        return jsonObject.getString("bio");
    }

        /**
         * API 응답을 DTO 리스트로 변환합니다.
         * @param apiResponse API에서 받아온 JSON 형식의 응답
         * @return ProblemDTO 리스트
         */
        private BaekjoonUserDTO convertApiResponseToDTO(String apiResponse){
            // JSON 문자열을 객체로 변환
            JSONObject jsonObject = new JSONObject(apiResponse);

            // 필요한 정보 추출
            String bjId = jsonObject.getString("handle");
            Integer solvedCount = jsonObject.getInt("solvedCount");
            Integer bjClass = jsonObject.getInt("class");
            Integer tier = jsonObject.getInt("tier");
            Integer rating = jsonObject.getInt("rating");
            Integer ratingByProblemSum = jsonObject.getInt("ratingByProblemsSum");
            Integer ratingByClass = jsonObject.getInt("ratingByClass");
            Integer ratingBySolvedCount = jsonObject.getInt("ratingBySolvedCount");
            Integer rank = jsonObject.getInt("rank");

            // BaekjoonUserDTO 생성
            BaekjoonUserDTO baekjoonUserDTO = BaekjoonUserDTO.builder()
                    .bjId(bjId)
                    .solvedCount(solvedCount)
                    .bjClass(bjClass)
                    .tier(tier)
                    .rating(rating)
                    .ratingByProblemSum(ratingByProblemSum)
                    .ratingByClass(ratingByClass)
                    .ratingBySolvedCount(ratingBySolvedCount)
                    .rank(rank)
                    .build();

            return baekjoonUserDTO;
        }

    // 이미 존재하는 엔터티를 업데이트합니다.
    private void updateExistingEntity(BaekjoonUserEntity existingUserEntity, BaekjoonUserDTO baekjoonUserDTO) {
        // 엔터티의 정보를 업데이트합니다.
        existingUserEntity.setSolvedCount(baekjoonUserDTO.getSolvedCount());
        existingUserEntity.setBjClass(baekjoonUserDTO.getBjClass());
        existingUserEntity.setTier(baekjoonUserDTO.getTier());
        existingUserEntity.setRating(baekjoonUserDTO.getRating());
        existingUserEntity.setRatingByProblemSum(baekjoonUserDTO.getRatingByProblemSum());
        existingUserEntity.setRatingByClass(baekjoonUserDTO.getRatingByClass());
        existingUserEntity.setRatingBySolvedCount(baekjoonUserDTO.getRatingBySolvedCount());
        existingUserEntity.setRank(baekjoonUserDTO.getRank());

        // 엔터티를 저장합니다.
        baekjoonUserRepository.save(existingUserEntity);
    }

    // 새로운 엔터티를 생성하여 저장합니다.
    private void saveNewEntity(BaekjoonUserDTO baekjoonUserDTO) {
        BaekjoonUserEntity baekjoonUserEntity = BaekjoonUserDTO.toBaekjoonUserEntity(baekjoonUserDTO);
        baekjoonUserRepository.save(baekjoonUserEntity);
    }

    @Transactional
    public void updateBaekjoonUsers() {
        System.out.println("모든 백준유저 업데이트 시작");

        // user 테이블의 모든 유저의 solved_ac_id 가져오기
        List<String> allSolvedAcIds = userRepository.findAllSolvedAcIds();

        for (String solvedAcId : allSolvedAcIds) {
            try {
                fetchUserAndSaveToDatabase(solvedAcId);
                System.out.println("-------------------------");
                System.out.println(solvedAcId + "유저 업데이트 완료");
            } catch (Exception e) {
                // 예외가 발생한 경우 예외 메시지 출력
                System.err.println("---------------------");
                System.err.println(solvedAcId + "유저 업데이트 중 예외 발생: " + e.getMessage());
            }
        }
    }


}
