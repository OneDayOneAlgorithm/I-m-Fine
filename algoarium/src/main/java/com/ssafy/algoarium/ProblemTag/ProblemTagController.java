// ProblemTagController
// 이 클래스는 ProblemTagEntity의 매핑을 수행하는 컨트롤러입니다.
// ProblemTagService를 사용하여 매핑 작업을 수행합니다.

package com.ssafy.algoarium.ProblemTag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/problemTags")
public class ProblemTagController {

    private final ProblemTagService problemTagService;

    @Autowired
    public ProblemTagController(ProblemTagService problemTagService) {
        this.problemTagService = problemTagService;
    }

    // performMapping 메서드를 호출하여 매핑 작업을 수행합니다.
    @PostMapping("/map")
    public void performMapping() {
        problemTagService.performMapping();
    }
}
