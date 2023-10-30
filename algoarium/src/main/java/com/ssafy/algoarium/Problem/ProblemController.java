package com.ssafy.algoarium.Problem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/problems")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @GetMapping("/fetch/{problem_number}")
    public void fetchProblemsAndSaveToDatabase(@PathVariable Integer problem_number) {
        problemService.fetchProblemsAndSaveToDatabase(problem_number);
    }
}
