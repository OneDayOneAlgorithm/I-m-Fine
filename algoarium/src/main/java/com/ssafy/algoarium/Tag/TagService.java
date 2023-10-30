package com.ssafy.algoarium.Tag;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Transactional
    public void fetchTagAndSaveToDatabase() {
        // 태그 조회 URL
        String apiUrl = "https://solved.ac/api/v3/tag/list";

        // RestTemplate을 사용하여 API 호출
        RestTemplate restTemplate = new RestTemplate();
        String apiResponse = restTemplate.getForObject(apiUrl, String.class);

        // API 응답을 DTO로 변환
        List<TagDTO> tagDTOs = convertApiResponseToDTO(apiResponse);

        // DTO를 Entity로 변환하여 DB에 저장
        for (TagDTO tagDTO : tagDTOs) {
            TagEntity tagEntity = TagDTO.toTagEntity(tagDTO);
            tagRepository.save(tagEntity);
        }
    }

    /**
     * API 응답을 DTO 리스트로 변환합니다.
     * @param apiResponse API에서 받아온 JSON 형식의 응답
     * @return ProblemDTO 리스트
     */
    private List<TagDTO> convertApiResponseToDTO(String apiResponse) {
        List<TagDTO> tagDTOList = new ArrayList<>();

        // JSON 문자열을 객체로 변환
        JSONObject jsonObject = new JSONObject(apiResponse);

        // 필요한 정보 추출
        JSONArray items = jsonObject.getJSONArray("items");

        for (int i = 0; i < 100; i++) {
            JSONObject tag = items.getJSONObject(i);
            JSONArray displayNames = tag.getJSONArray("displayNames");

            JSONObject displayName = displayNames.getJSONObject(0);

            String name = displayName.getString("name");
            tagDTOList.add(TagDTO.builder().tagName(name).build());
        }

        return tagDTOList;
    }
}
