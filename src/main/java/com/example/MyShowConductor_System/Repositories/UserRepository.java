package com.example.MyShowConductor_System.Repositories;

import com.example.MyShowConductor_System.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByMobNo(String mob);

    User findByEmail(String username);
}
