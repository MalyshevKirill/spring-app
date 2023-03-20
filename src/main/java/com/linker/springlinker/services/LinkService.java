package com.linker.springlinker.services;

import com.linker.springlinker.models.Link;
import com.linker.springlinker.models.User;
import com.linker.springlinker.repos.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LinkService {

    @Autowired
    private LinkRepository linkRepository;

    public Link getLink(String url) {
        Link link = linkRepository.findByLink(url);
        if(link == null) {
            return null;
        }
        return link;
    }

    public String addLink(Link link, User user) {
        Link candidate = linkRepository.findByLink(link.getLink());
        if(candidate != null) {
            return "Link has already been used";
        }
        link.setOwner(user);
        System.out.println(link.toString());
        linkRepository.save(link);
        return "Created";
    }
}
