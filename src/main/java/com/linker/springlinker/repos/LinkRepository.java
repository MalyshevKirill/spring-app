package com.linker.springlinker.repos;

import com.linker.springlinker.models.Link;
import com.linker.springlinker.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {
    Link findBySource(String source);

    Link findByLink(String link);

    List<Link> findByOwner(User user);

    void deleteById(Long id);
}
