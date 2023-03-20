package com.linker.springlinker.repos;

import com.linker.springlinker.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Override
    boolean existsById(Long aLong);

    @Override
    Optional<User> findById(Long aLong);

}
