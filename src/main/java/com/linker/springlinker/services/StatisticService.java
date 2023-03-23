package com.linker.springlinker.services;

import com.linker.springlinker.models.Link;
import com.linker.springlinker.models.Statistic;
import com.linker.springlinker.repos.StatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;

@Service
public class StatisticService {
    @Autowired
    private StatisticRepository statisticRepository;

    public String addStatistic(Link link, Locale locale) {
        Statistic newStat = new Statistic();
        newStat.setLink(link);
        newStat.setDateTime(LocalDateTime.now());
        newStat.setCountry(locale.getCountry());
        statisticRepository.save(newStat);
        return "Created";
    }
}
