package com.example.MyShowConductor_System.Services;

import com.example.MyShowConductor_System.Entities.Ticket;
import com.example.MyShowConductor_System.Entities.Transaction;
import com.example.MyShowConductor_System.ResponseDTOs.TransactionResponseDto;

import java.util.List;

public interface TransactionService {



    public TransactionResponseDto createTransaction(Ticket ticket);
//    additional
    public List<TransactionResponseDto> getTransactionsByTicket(int ticketId);

    List<TransactionResponseDto> getAllByUser(String email);
}
