package com.ssafy.algoarium.User;

import com.ssafy.algoarium.UserRanking.UserRankingEntity;
import com.ssafy.algoarium.UserStatus.UserStatusEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "kakao_id", nullable = false, length = 100)
    private String kakaoId;

    @Setter
    @Column(name = "kakao_nickname", nullable = false, length = 50)
    private String kakaoNickname;

    @Setter
    @Column(name = "profile_image", nullable = false, length = 500)
    private String profileImage;

    @Column(name = "pre_tier", nullable = false, length = 20)
    private Integer preTier = 1;

    @Column(name = "refresh_token", nullable = false, length = 100)
    private String refreshToken;

    @Column(name = "solved_ac_id" , nullable = true, length = 100)
    private String solvedAcId = "";

    @OneToOne
    @JoinColumn(name = "user_ranking_id",referencedColumnName = "user_ranking_id")
    private UserRankingEntity userRanking;

    @OneToOne
    @JoinColumn(name = "user_status_id" , referencedColumnName = "user_status_id")
    private UserStatusEntity userStatusEntity;

    @Builder
    public UserEntity(Long userId , String kakaoId, String kakaoNickname, String profileImage,
        Integer preTier, String refreshToken , String solvedAcId) {
        this.userId = userId;
        this.kakaoId = kakaoId;
        this.kakaoNickname = kakaoNickname;
        this.profileImage = profileImage;
        this.preTier = preTier;
        this.refreshToken = refreshToken;
        this.solvedAcId = solvedAcId;
    }

    public UserDto toUserDto(){
        return UserDto.builder()
            .userId(this.getUserId())
            .kakaoId(this.getKakaoId())
            .kakaoNickname(this.getKakaoNickname())
            .preTier(this.getPreTier())
            .profileImage(this.getProfileImage())
            .refreshToken(this.getRefreshToken())
            .solvedAcId(this.getSolvedAcId())
            .build();
    }
}
