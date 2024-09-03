package com.example.MyShowConductor_System.Services.Impl;

import com.example.MyShowConductor_System.Entities.Ticket;
import com.example.MyShowConductor_System.Entities.Transaction;
import com.example.MyShowConductor_System.Entities.User;
import com.example.MyShowConductor_System.Enums.TransactionStatusEnum;
import com.example.MyShowConductor_System.Enums.TransactionTypeEnum;
import com.example.MyShowConductor_System.Repositories.TransactionRepository;
import com.example.MyShowConductor_System.ResponseDTOs.TransactionResponseDto;
import com.example.MyShowConductor_System.Services.TransactionService;
import com.example.MyShowConductor_System.Services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TransactionResponseDto createTransaction(Ticket ticket) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setTicket(ticket);
        transaction.setAmount(ticket.getTotalAmount());
        transaction.setType(TransactionTypeEnum.PAYMENT);
        if(ticket.getStatus().equals("CONFIRMED")){
            transaction.setStatus(TransactionStatusEnum.SUCCESSULL);
        }
        else if (ticket.getStatus().equals("FAILED")){
            transaction.setStatus(TransactionStatusEnum.FAILED);
        }
        else {
            transaction.setStatus(TransactionStatusEnum.PENDING);
        }
        Transaction savedTransaction = transactionRepository.save(transaction);

        return this.modelMapper.map(savedTransaction,TransactionResponseDto.class);
    }

    @Override
    public List<TransactionResponseDto> getTransactionsByTicket(int ticketId) {
        List<TransactionResponseDto> transactions = transactionRepository.findByTicketId(ticketId).stream().map(transaction -> (this.modelMapper.map(transaction,TransactionResponseDto.class))).collect(Collectors.toList());
        return transactions;
    }

    @Override
    public List<TransactionResponseDto> getAllByUser(String email) {
        User user = userService.getUserEntityByEmail(email);
        List<TransactionResponseDto> transactions = transactionRepository.findByUserId(user.getId()).stream().map(transaction -> (this.modelMapper.map(transaction,TransactionResponseDto.class))).collect(Collectors.toList());
        return transactions;
    }
}
