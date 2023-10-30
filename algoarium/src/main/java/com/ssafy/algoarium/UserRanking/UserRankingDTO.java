package com.ssafy.algoarium.UserRanking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class UserRankingDTO {

	private long userRankingId;
	private long userId;
	private String kakaoNickname;
	private String profileImage;
	private int score;
	private int tier;
	private int ranking;


}
