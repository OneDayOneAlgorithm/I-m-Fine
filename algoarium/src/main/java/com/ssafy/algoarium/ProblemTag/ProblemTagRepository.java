// ProblemTagRepository
package com.ssafy.algoarium.ProblemTag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemTagRepository extends JpaRepository<ProblemTagEntity, Integer> {
    // Additional query methods can be added here if needed
}
