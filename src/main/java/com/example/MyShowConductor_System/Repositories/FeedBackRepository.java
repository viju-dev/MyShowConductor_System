package com.example.MyShowConductor_System.Repositories;

import com.example.MyShowConductor_System.Entities.FeedBack;
import com.example.MyShowConductor_System.ResponseDTOs.FeedBackResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedBackRepository extends JpaRepository<FeedBack,Integer> {
    List<FeedBackResponseDto> findByUserId(int userId);
}
