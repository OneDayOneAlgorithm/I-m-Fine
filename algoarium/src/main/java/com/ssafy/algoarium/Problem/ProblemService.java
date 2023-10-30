package com.ssafy.algoarium.Problem;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProblemService {

    @Autowired
    private ProblemRepository problemRepository;

    @Transactional
    public void fetchProblemsAndSaveToDatabase(Integer problem_number) {
        // 문제 조회 URL
        String apiUrl = "https://solved.ac/api/v3/problem/lookup?problemIds=";

        for (int cnt = problem_number; cnt <= problem_number + 500; cnt++) {
            // RestTemplate을 사용하여 API 호출
            RestTemplate restTemplate = new RestTemplate();
            String apiResponse = restTemplate.getForObject(apiUrl + cnt, String.class);

            // API 응답이 빈 배열인 경우 처리
            if (apiResponse.equals("[]")) {
                System.out.println("API 응답이 빈 배열입니다.");
                continue;
            }

            // API 응답을 DTO로 변환
            List<ProblemDTO> problemDTOs = convertApiResponseToDTO(apiResponse);

            // DTO를 Entity로 변환하여 DB에 저장
            for (ProblemDTO problemDTO : problemDTOs) {
                ProblemEntity problemEntity = ProblemDTO.toProblemEntity(problemDTO);
                problemRepository.save(problemEntity);
            }
        }

    }


    /**
     * API 응답을 DTO 리스트로 변환합니다.
     * @param apiResponse API에서 받아온 JSON 형식의 응답
     * @return ProblemDTO 리스트
     */
    private List<ProblemDTO> convertApiResponseToDTO (String apiResponse){
        List<ProblemDTO> problemDTOList = new ArrayList<>();

        // JSON 문자열을 배열로 변환
        JSONArray jsonArray = new JSONArray(apiResponse);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            System.out.println("--------------------------");
            System.out.println("문제번호 : " + jsonObject.getInt("problemId"));
            // 필요한 정보 추출
            Integer problemId = jsonObject.getInt("problemId");
            String titleKo = jsonObject.getString("titleKo");
            Integer level = jsonObject.getInt("level");
            Integer acceptedUserCount = jsonObject.getInt("acceptedUserCount");

            JSONArray tags = jsonObject.getJSONArray("tags");
            List<String> problemTags = new ArrayList<>();

            for (int j = 0; j < tags.length(); j++) {
                JSONObject tag = tags.getJSONObject(j);
                JSONArray displayNames = tag.getJSONArray("displayNames");

                JSONObject displayName = displayNames.getJSONObject(0);
                String name = displayName.getString("name");
                problemTags.add(name);
            }

            // ProblemDTO 생성
            ProblemDTO problemDTO = ProblemDTO.builder()
                    .problemNumber(problemId)
                    .title(titleKo)
                    .problemLevel(level)
                    .solvedUserCount(acceptedUserCount)
                    .problemTag(String.join(", ", problemTags))
                    .build();

            problemDTOList.add(problemDTO);
        }

        return problemDTOList;
    }

    // 문제 ID로 문제를 찾아옵니다.
    public ProblemEntity getProblemById(Integer problemId) {
        Optional<ProblemEntity> problemOptional = problemRepository.findById(problemId);
        return problemOptional.orElse(null);
    }
}
