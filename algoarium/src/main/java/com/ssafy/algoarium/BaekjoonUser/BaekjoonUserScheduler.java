package com.ssafy.algoarium.BaekjoonUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BaekjoonUserScheduler {

    private final BaekjoonUserService baekjoonUserService;
    private final BaekjoonUserRepository baekjoonUserRepository; // BaekjoonUserRepository 주입

    @Autowired
    public BaekjoonUserScheduler(BaekjoonUserService baekjoonUserService, BaekjoonUserRepository baekjoonUserRepository) {
        this.baekjoonUserService = baekjoonUserService;
        this.baekjoonUserRepository = baekjoonUserRepository; // 주입된 Repository
    }

    // 매일 6시에 실행
    @Scheduled(cron = "0 0 6 * * ?")
//    @Scheduled(cron = "0 * * * * ?")
    public void updateBaekjoonUsers() {
        System.out.println("모든 백준유저 업데이트 시작");
        // 여기에서 모든 bjId를 가져와서 initializeBaekjoonUser() 메서드 호출
        List<String> allBjIds = baekjoonUserRepository.findAllBjIds();

        for (String bjId : allBjIds) {

            try {
                baekjoonUserService.fetchUserAndSaveToDatabase(bjId);
                System.out.println("-------------------------");
                System.out.println(bjId + "유저 업데이트 완료");
            } catch (Exception e) {
                // 예외가 발생한 경우 예외 메시지 출력
                System.err.println("---------------------");
                System.err.println(bjId + "유저 업데이트 중 예외 발생: " + e.getMessage());
            }
        }
    }
}
