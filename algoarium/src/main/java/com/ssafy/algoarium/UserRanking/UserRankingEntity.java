package com.ssafy.algoarium.UserRanking;

import com.ssafy.algoarium.User.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_ranking")
public class UserRankingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_ranking_id", nullable = false)
	private Long userRankingId;

	@Column(name = "score" , nullable = false)
	private Integer score = 0;

	@Column(name = "tier" , nullable = false)
	private Integer tier = 1;

	@Column(name = "ranking", nullable = false)
	private Integer ranking;

	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_id") // 연관되는 컬럼 지정
	private UserEntity user;



	@Builder
	public UserRankingEntity(int score, int tier, int ranking, UserEntity user){
		this.score = score;
		this.tier = tier;
		this.ranking = ranking;
		this.user = user;
	}
}
