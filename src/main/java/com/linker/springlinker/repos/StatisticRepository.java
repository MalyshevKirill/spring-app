package com.linker.springlinker.repos;

import com.linker.springlinker.models.Link;
import com.linker.springlinker.models.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Long> {
    List<Statistic> findByLink(Link link);

}
