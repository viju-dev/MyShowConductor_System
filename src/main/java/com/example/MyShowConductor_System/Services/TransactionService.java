package com.example.MyShowConductor_System.Services;

import com.example.MyShowConductor_System.Entities.Transaction;

import java.util.List;

public interface TransactionService {


//    additional
    public List<Transaction> getTransactionsByTicket(int ticketId);

}
