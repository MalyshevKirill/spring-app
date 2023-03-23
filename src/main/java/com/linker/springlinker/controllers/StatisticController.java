package com.linker.springlinker.controllers;

import com.linker.springlinker.models.Link;
import com.linker.springlinker.models.Statistic;
import com.linker.springlinker.repos.StatisticRepository;
import com.linker.springlinker.services.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Locale;

@Controller
public class StatisticController {
    @Autowired
    private LinkService linkService;

    @Autowired
    private StatisticRepository statisticRepository;

    @GetMapping("/stats/{url}")
    public String Stats(Model model, @PathVariable String url) {


        Link link = linkService.getLink(url);
        if (link == null) {
            return "error";
        } else {
            List<Statistic> stats = statisticRepository.findByLink(link);
            model.addAttribute("link" , url);
            model.addAttribute("stats", stats);
            model.addAttribute("count", Integer.toString(stats.size()));
        }

        return "stats";
    }
}
