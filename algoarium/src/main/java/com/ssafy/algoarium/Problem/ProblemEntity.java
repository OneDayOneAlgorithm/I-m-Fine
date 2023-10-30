// 이 클래스는 JPA를 사용하여 데이터베이스와 상호작용할 수 있도록 설정되어 있으며, 필요한 정보를 관리하는 역할을 수행합니다.
package com.ssafy.algoarium.Problem; // 이 클래스가 속한 패키지를 정의

import jakarta.persistence.Column; // 다른 클래스나 패키지에서 필요한 라이브러리들을 가져옵니다. 여기서는 jakarta.persistence와 lombok 등을 사용하고 있습니다.
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;


@Entity // 이 어노테이션은 이 클래스가 JPA(Java Persistence API)를 사용하여 데이터베이스에 저장될 엔터티임을 나타냅니다.
@NoArgsConstructor(access = AccessLevel.PROTECTED) // Lombok을 사용하여 파라미터 없는 생성자를 자동으로 생성하며, 이 생성자의 접근 제한자를 protected로 지정합니다.
@Getter // Lombok 라이브러리를 사용하여 자동으로 getter 메서드를 생성하도록 지정합니다. getter는 클래스의 필드 값을 외부에서 읽을 수 있게 해줍니다.
@Table(name = "problem") // 이 엔터티가 데이터베이스에서 사용될 테이블의 이름을 "problem"으로 지정합니다.
public class ProblemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer problemId; // 유저의 고유한 식별자인 ID를 나타냅니다.

    @Column(name = "problem_number", nullable = false, length = 1000)
    private Integer problemNumber; // 문제 번호를 나타냅니다.

    @Column(name = "title", nullable = false, length = 1000)
    private String title; // 문제 제목을 나타냅니다.

    @Column(name = "problem_tag", nullable = false, length = 1000)
    private String problemTag; // 문제 태그를 나타냅니다.

    @Column(name = "problem_level", nullable = false, length = 1000)
    private Integer problemLevel; // 문제의 난이도를 나타냅니다.

    @Column(name = "solved_user_count", nullable = false, length = 1000)
    private Integer solvedUserCount; // 문제를 해결한 사용자 수를 나타냅니다.

    @Builder // Lombok을 사용하여 빌더 패턴을 생성하도록 지정합니다. 빌더 패턴은 객체를 생성할 때 각 필드를 일일이 설정하지 않고 더 간편하게 생성할 수 있게 해줍니다.
    public ProblemEntity(Integer problemNumber, String title, String problemTag, // 해당 생성자는 파라미터로 받은 값을 사용하여 객체를 초기화하는 역할을 합니다.
                         Integer problemLevel, Integer solvedUserCount) {
        this.problemNumber = problemNumber;
        this.title = title;
        this.problemTag = problemTag;
        this.problemLevel = problemLevel;
        this.solvedUserCount = solvedUserCount;
    }
}
