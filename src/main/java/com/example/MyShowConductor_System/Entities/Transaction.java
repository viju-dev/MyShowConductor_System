package com.example.MyShowConductor_System.Entities;

import com.example.MyShowConductor_System.Enums.TransactionStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String transactionId = UUID.randomUUID().toString();
//    https://stackoverflow.com/questions/32519391/while-logging-a-payment-transaction-should-i-use-ids-of-tables-or-directly-the

    @Column(nullable = false)
    private LocalDate transactionDate;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private String type;  // 'PAYMENT' or 'REFUND'

    @Column(nullable = false)
    private TransactionStatusEnum status;
    // Mapping Transaction -> User
    @ManyToOne
    @JoinColumn
    private User user;

    // Mapping Transaction -> Ticket
    @OneToOne
    @JoinColumn
    private Ticket ticket;
}
