package com.example.MyShowConductor_System.Repositories;

import com.example.MyShowConductor_System.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByMobNo(String mob);

    Optional<User> findByEmail(String username);
//        User findByEmail(String username);


}
