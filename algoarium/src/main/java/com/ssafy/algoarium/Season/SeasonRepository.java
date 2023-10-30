package com.ssafy.algoarium.Season;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeasonRepository extends JpaRepository<SeasonEntity, Long>{
}
