package com.ssafy.algoarium.BaekjoonUser;

import com.ssafy.algoarium.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/BaekjoonUser")
public class BaekjoonUserController {

    private final BaekjoonUserService baekjoonUserService;
    private final UserService userService;

    @Autowired
    public BaekjoonUserController(BaekjoonUserService baekjoonUserService, UserService userService) {
        this.baekjoonUserService = baekjoonUserService;
        this.userService = userService;
    }

    @GetMapping("/initialize/{bjId}")
    public Boolean initializeBaekjoonUser(@PathVariable String bjId) {
        baekjoonUserService.fetchUserAndSaveToDatabase(bjId);
        return true;
    }

    @GetMapping("/bio/{bjId}")
    public String bioBaekjoonUser(@PathVariable String bjId) {
        return baekjoonUserService.fetchBio(bjId);
    }

    @GetMapping("/{userId}/{solvedAc}/{code}")
    public String updateUser(@PathVariable long userId, @PathVariable String solvedAc, @PathVariable String code){
        System.out.println(userId);
        System.out.println(solvedAc);
        System.out.println(code);
        return userService.isCodeEquals(userId, solvedAc, code);
    }

    @PostMapping("/update-all")
    public String updateAllBaekjoonUsers() {
        try {
            baekjoonUserService.updateBaekjoonUsers();
            return "모든 백준 유저 업데이트가 시작되었습니다.";
        } catch (Exception e) {
            return "백준 유저 업데이트 중 예외 발생: " + e.getMessage();
        }
    }
}
