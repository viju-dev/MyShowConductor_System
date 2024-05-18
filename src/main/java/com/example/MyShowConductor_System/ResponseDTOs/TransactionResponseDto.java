package com.example.MyShowConductor_System.ResponseDTOs;


import com.example.MyShowConductor_System.Entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class TransactionResponseDto {

    private String transactionId = UUID.randomUUID().toString();

    private LocalDate transactionDate;

    private int amount;

    private String type;  // 'PAYMENT' or 'REFUND'

    private TransactionStatusResponseDto transactionStatus;

    private User user;

    private TicketResponseDTO ticket;
}
