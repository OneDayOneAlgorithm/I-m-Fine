// 이 클래스는 JPA를 사용하여 데이터베이스와 상호작용할 수 있도록 설정되어 있으며, 필요한 정보를 관리하는 역할을 수행합니다.
package com.ssafy.algoarium.BaekjoonUser;

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
@Setter
@Table(name = "baekjoon_user") // 이 엔터티가 데이터베이스에서 사용될 테이블의 이름을 "problem"으로 지정합니다.
public class BaekjoonUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bjUserId;

    @Column(name = "bj_id", nullable = false, length = 100)
    private String bjId;

    @Column(name = "solved_count", length = 100)
    private Integer solvedCount;

    @Column(name = "bj_class", length = 100)
    private Integer bjClass;

    @Column(name = "tier", length = 100)
    private Integer tier;

    @Column(name = "rating", length = 100)
    private Integer rating;

    @Column(name = "rating_by_problem_sum", length = 100)
    private Integer ratingByProblemSum;

    @Column(name = "rating_by_class", length = 100)
    private Integer ratingByClass;

    @Column(name = "rating_by_solved_count", length = 100)
    private Integer ratingBySolvedCount;

    @Column(name = "rank", length = 100)
    private Integer rank;

    @Builder
    public BaekjoonUserEntity(String bjId, Integer solvedCount, Integer bjClass, Integer tier, Integer rating, Integer ratingByProblemSum, Integer ratingByClass, Integer ratingBySolvedCount, Integer rank) {
        this.bjId = bjId;
        this.solvedCount = solvedCount;
        this.bjClass = bjClass;
        this.tier = tier;
        this.rating = rating;
        this.ratingByProblemSum = ratingByProblemSum;
        this.ratingByClass = ratingByClass;
        this.ratingBySolvedCount = ratingBySolvedCount;
        this.rank = rank;
    }

}
