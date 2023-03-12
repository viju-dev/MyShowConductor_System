package com.example.MyShowConductor_System.Repositories;

import com.example.MyShowConductor_System.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByMobNo(String mob);
}
