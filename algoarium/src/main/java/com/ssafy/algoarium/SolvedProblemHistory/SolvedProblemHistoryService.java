package com.ssafy.algoarium.SolvedProblemHistory;

import com.ssafy.algoarium.KakaoLogin.KakaoOauthToken;
import com.ssafy.algoarium.Problem.ProblemRepository;
import com.ssafy.algoarium.User.UserRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SolvedProblemHistoryService {

	private final SolvedProblemHistoryRepository solvedProblemHistoryRepository;
	private final UserRepository userRepository;
	private final ProblemRepository problemRepository;

	public SolvedProblemHistoryEntity toEntity(SolvedProblemHistoryDTO solvedProblemHistoryDTO){
		return SolvedProblemHistoryEntity.builder()
			.solvedOrNot(solvedProblemHistoryDTO.getSolvedOrNot())
			.userEntity(solvedProblemHistoryDTO.getUser())
			.problemEntity(solvedProblemHistoryDTO.getProblem())
			.build();
	}

	public void saveSolvedProbelm(SolvedProblemHistoryDTO solvedProblemHistoryDTO){
		solvedProblemHistoryRepository.save(toEntity(solvedProblemHistoryDTO));
	}
	public boolean isSolvedByUserIdAndProblemId(Long userId, Integer problemId) {
		Optional<SolvedProblemHistoryEntity> solvedHistory = solvedProblemHistoryRepository
			.findByUserUserIdAndProblemProblemId(userId, problemId);

		return solvedHistory.isPresent(); // 데이터가 존재하면 true, 없으면 false 반환
	}
	public void saveBaekjoonId(String baekjoonId){
		int pageCount = 40;
		int page = 1;
		int count;
		do {
			URI uri = UriComponentsBuilder
					.fromUriString("https://solved.ac/api/v3/search/problem")
					.queryParam("query" , "s@" + baekjoonId)
					.queryParam("page", page)
					.encode()
					.build()
					.toUri();

			RestTemplate rt = new RestTemplate();

			ResponseEntity<String> result = rt.getForEntity(uri, String.class);

			ObjectMapper objectMapper = new ObjectMapper();

			SolvedResponse solvedResponse = null;

			try {
				solvedResponse = objectMapper.readValue(result.getBody(), SolvedResponse.class);
			} catch (JsonProcessingException e) {
				throw new RuntimeException(e);
			}

			count = solvedResponse.getCount();

			List<SolvedResponse.Problem> problemList = solvedResponse.getItems();
			for (SolvedResponse.Problem problem : problemList) {
				int problemId = problem.getProblemId();

				if(problemRepository.findByProblemNumber(problemId) == null){
					continue;
				}

				if(isSolvedByUserIdAndProblemId(
					userRepository.findBySolvedAcId(baekjoonId).getUserId()
					, problemRepository.findByProblemNumber(problemId).getProblemId()
				)){
					continue;
				}
				System.out.println(userRepository.findBySolvedAcId(baekjoonId).getSolvedAcId() + " ,"
				+ problemRepository.findByProblemNumber(problemId) + "saved!!!");

				solvedProblemHistoryRepository.save(SolvedProblemHistoryEntity.builder()
						.userEntity(userRepository.findBySolvedAcId(baekjoonId))
						.problemEntity(problemRepository.findByProblemNumber(problemId))
						.build());
				}
			if (count < page *pageCount){
				break;
			}
			page++;
		}while (true);
	}
}

