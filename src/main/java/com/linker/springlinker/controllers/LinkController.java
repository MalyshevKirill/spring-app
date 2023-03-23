package com.linker.springlinker.controllers;

import com.linker.springlinker.models.Link;
import com.linker.springlinker.models.User;
import com.linker.springlinker.repos.LinkRepository;
import com.linker.springlinker.services.LinkService;
import com.linker.springlinker.services.StatisticService;
import com.linker.springlinker.services.details.AppUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.linker.springlinker.utils.RandomStringGenerator;

import java.util.List;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;

@Controller
public class LinkController {

    @Value("${host.link}")
    private String myHost;

    @Autowired
    private LinkService linkService;

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private StatisticService statisticService;

    @GetMapping("/my-links")
    public String myLinks(Model model) {
        AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = appUserDetails.getUser();

        List<Link> links = linkRepository.findByOwner(user);

        if(links != null) {
            model.addAttribute("links", links);
        }

        return "my-links";
    }

    @PostMapping("/delete-link")
    public String deleteLink(@RequestBody MultiValueMap<String, String> formData, Model model)  {

        linkRepository.deleteById(Long.valueOf(formData.getFirst("id")));

        return myLinks(model);
    }

    @PostMapping("/create-link")
    public String newLink(@RequestBody MultiValueMap<String, String> formData, Model model) {
        AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = appUserDetails.getUser();

        String auto = formData.getFirst("auto");
        Link newLink = new Link();

        if (auto != null) {
            String randomString = RandomStringGenerator.generateRandomString(10);

            newLink.setLink(randomString);
        } else {
            newLink.setLink(formData.getFirst("link"));
        }

        newLink.setSource(formData.getFirst("source"));

        String result = linkService.addLink(newLink, user);
        if(result == "Created") {
            model.addAttribute("created_link", myHost + "/l/" + newLink.getLink());
        } else {
            model.addAttribute("creation_error", result);
        }

        return "create-link";
    }

    @GetMapping("/create-link")
    public String createLink(Model model) {
        return "create-link";
    }

    @GetMapping("/l/{url}")
    public String goToLink(Model model, @PathVariable String url, final Locale locale) {
        Link link = linkService.getLink(url);

        System.out.println(locale.getCountry());

        if(link == null) {
            return "error";
        } else {
            statisticService.addStatistic(link, locale);
        }

        return "redirect:" + link.getSource();
    }
}
