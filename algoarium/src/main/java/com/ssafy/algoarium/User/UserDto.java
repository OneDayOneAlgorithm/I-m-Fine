package com.ssafy.algoarium.User;

import jakarta.persistence.*;
import lombok.*;
/**
 * 회원 Collection
 */


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class UserDto {
    private long userId;
    private String kakaoId;
    private String kakaoNickname;
    private String profileImage;
    private int preTier;
    private String refreshToken;
    private int tier;
    private String solvedAcId;

    public static UserEntity toUserEntity(UserEntity userDTO) {
        return UserEntity.builder()
            .kakaoId(userDTO.getKakaoId())
            .kakaoNickname(userDTO.getKakaoNickname())
            .profileImage(userDTO.getProfileImage())
            .preTier(userDTO.getPreTier())
            .refreshToken(userDTO.getRefreshToken())
            .solvedAcId(userDTO.getSolvedAcId())
            .build();
    }

}

