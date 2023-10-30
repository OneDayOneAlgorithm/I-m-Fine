package com.ssafy.algoarium.Season;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/season")
public class SeasonContollor{
    private final SeasonService seasonService;
    @GetMapping("/{season}")
    public Boolean season_change(@PathVariable Boolean season){
        return seasonService.save_season(season);
    }
}
