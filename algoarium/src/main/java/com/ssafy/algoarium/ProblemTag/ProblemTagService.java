// ProblemTagService
// 이 클래스는 ProblemTagEntity의 매핑 작업을 수행하는 서비스입니다.
// ProblemTagRepository, ProblemRepository, TagRepository를 사용하여 데이터를 처리합니다.

package com.ssafy.algoarium.ProblemTag;

import com.ssafy.algoarium.Problem.ProblemEntity;
import com.ssafy.algoarium.Problem.ProblemRepository;
import com.ssafy.algoarium.Tag.TagEntity;
import com.ssafy.algoarium.Tag.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemTagService {

    private final ProblemTagRepository problemTagRepository;
    private final ProblemRepository problemRepository;
    private final TagRepository tagRepository;

    @Autowired
    public ProblemTagService(ProblemTagRepository problemTagRepository, ProblemRepository problemRepository, TagRepository tagRepository) {
        this.problemTagRepository = problemTagRepository;
        this.problemRepository = problemRepository;
        this.tagRepository = tagRepository;
    }

    // performMapping 메서드는 모든 문제와 태그를 가져와 문제의 태그와 태그의 이름을 비교하여 매핑합니다.
    public void performMapping() {
        // 모든 문제를 가져옵니다.
        List<ProblemEntity> allProblems = problemRepository.findAll();

        // 모든 태그를 가져옵니다.
        List<TagEntity> allTags = tagRepository.findAll();

        // 각 문제의 태그와 모든 태그의 이름을 비교하여 매핑합니다.
        for (ProblemEntity problem : allProblems) {
            String problemTag = problem.getProblemTag();

            for (TagEntity tag : allTags) {
                if (problemTag.contains(tag.getTagName())) {
                    ProblemTagEntity problemTagEntity = new ProblemTagEntity(problem, tag);
                    problemTagRepository.save(problemTagEntity);
                }
            }
        }
    }
}
