package com.example.MyShowConductor_System.Repositories;

import com.example.MyShowConductor_System.Entities.Transaction;
import com.example.MyShowConductor_System.ResponseDTOs.TransactionResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
    List<TransactionResponseDto> findByTicketId(int ticketId);

    List<TransactionResponseDto> findByUserId(int id);
}
