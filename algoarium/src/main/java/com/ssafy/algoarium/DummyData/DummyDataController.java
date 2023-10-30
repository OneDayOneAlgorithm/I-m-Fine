package com.ssafy.algoarium.DummyData;

import com.ssafy.algoarium.BaekjoonUser.BaekjoonUserEntity;
import com.ssafy.algoarium.BaekjoonUser.BaekjoonUserRepository;
import com.ssafy.algoarium.User.UserEntity;
import com.ssafy.algoarium.User.UserRepository;
import com.ssafy.algoarium.UserStatus.UserStatusEntity;
import com.ssafy.algoarium.UserStatus.UserStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dummy-data")
public class DummyDataController {

    private final BaekjoonUserRepository baekjoonUserRepository;
    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;

    @Autowired
    public DummyDataController(BaekjoonUserRepository baekjoonUserRepository, UserRepository userRepository, UserStatusRepository userStatusRepository) {
        this.baekjoonUserRepository = baekjoonUserRepository;
        this.userRepository = userRepository;
        this.userStatusRepository = userStatusRepository;
    }

    @GetMapping("/BaekjoonUser")
    public String createDummyBaekjoonUsers() {
        // 다섯 명의 더미 백준 유저 데이터 생성 및 저장
        for (int i = 0; i < 5; i++) {
            BaekjoonUserEntity dummyBaekjoonUser = BaekjoonUserEntity.builder()
                    .bjId("사용자" + (i + 1))
                    .solvedCount(i + 50)
                    .bjClass(5)
                    .tier(2)
                    .rating(i + 1500)
                    .ratingByProblemSum(i + 1200)
                    .ratingByClass(i + 1300)
                    .ratingBySolvedCount(i + 1400)
                    .rank(i + 100)
                    .build();

            // 더미 데이터 저장
            baekjoonUserRepository.save(dummyBaekjoonUser);
        }

        return "5명의 더미 백준 유저 데이터가 생성되었습니다.";
    }

    @GetMapping("/users")
    public String createDummyUsers() {
        // 20명의 유저 데이터 생성 및 저장
        for (int i = 0; i < 20; i++) {
            UserEntity dummyUser = UserEntity.builder()
                    .kakaoId("kakao_id_" + i)
                    .kakaoNickname("User" + (i + 1))
                    .profileImage("profile_image_url_" + i)
                    .preTier(1)
                    .refreshToken("refresh_token_" + i)
                    .build();

            // 더미 데이터 저장
            userRepository.save(dummyUser);
        }

        return "20명의 유저 데이터가 생성되었습니다.";
    }

    @GetMapping("/user_status")
    public String createDummyUserStatus() {
        // 10명의 유저 스탯 데이터 생성 및 저장
        for (int i = 0; i < 10; i++) {
            UserEntity user = userRepository.getOne((long)i + 1); // 미리 저장된 유저를 가져와서 연결

            UserStatusEntity dummyUserStatus = UserStatusEntity.builder()
                    .userStatus1(i * 5) // 예시로 각 스탯을 다르게 설정
                    .userStatus2(i * 10)
                    .userStatus3(i * 15)
                    .userStatus4(i * 20)
                    .userStatus5(i * 25)
                    .user(user)
                    .build();

            // 더미 데이터 저장
            userStatusRepository.save(dummyUserStatus);
        }

        return "10명의 유저 스탯 데이터가 생성되었습니다.";
    }
}
