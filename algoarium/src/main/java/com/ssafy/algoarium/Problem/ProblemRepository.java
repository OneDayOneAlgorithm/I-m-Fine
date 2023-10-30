/**
 * 이 인터페이스는 Spring Data JPA의 JpaRepository를 확장하여 ProblemEntity 클래스와 연동합니다. JpaRepository를 상속하면 기본적인 CRUD(Create, Read, Update, Delete) 메서드를 자동으로 제공받을 수 있습니다. 필요에 따라 추가적인 쿼리 메서드를 작성할 수도 있습니다.
 * @Repository 어노테이션은 해당 클래스가 데이터베이스에 접근하는 레포지토리 역할을 한다는 것을 나타냅니다. 이제 ProblemRepository를 사용하여 데이터베이스와 상호작용할 수 있게 됩니다.
 */
package com.ssafy.algoarium.Problem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional; // Optional을 추가합니다.

@Repository
public interface ProblemRepository extends JpaRepository<ProblemEntity, Integer> {
    // 추가적인 쿼리 메서드가 필요하다면 여기에 작성할 수 있습니다.

    @Query("SELECT p FROM ProblemEntity p WHERE p.problemNumber = :problemNumber")
    ProblemEntity findByProblemNumber(Integer problemNumber);

    boolean existsByProblemNumber(Integer problemNumber);
}
