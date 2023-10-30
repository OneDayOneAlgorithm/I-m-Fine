package com.ssafy.algoarium.Season;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeasonService {
    @Autowired
    private SeasonRepository seasonRepository;

    public Boolean save_season(Boolean promotionSeason) {
        // 데이터베이스에 해당 레코드가 있는지 확인
        SeasonEntity existingSeason = seasonRepository.findById(1L).orElse(null);

        if (existingSeason != null) {
            // 이미 레코드가 존재하면 업데이트
            existingSeason.setPromotionSeason(promotionSeason);
            seasonRepository.save(existingSeason);
        } else {
            // 레코드가 존재하지 않으면 새로 생성
            SeasonEntity newSeason = SeasonEntity.builder()
                    .promotionSeason(promotionSeason)
                    .build();
            seasonRepository.save(newSeason);
        }

        return promotionSeason;
    }
}
