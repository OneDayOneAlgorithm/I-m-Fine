package com.ssafy.algoarium.SolvedProblemHistory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SolvedResponse {

	@JsonProperty("count")
	private int count;

	@JsonProperty("items")
	private List<Problem> items;

	// Getter 및 Setter 메서드
	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Problem {

		@JsonProperty("problemId")
		private int problemId;
	}
}
