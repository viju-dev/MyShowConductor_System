package com.example.MyShowConductor_System.Entities;

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
//can add localtime as well
    @Column(nullable = false)
    private int amount;

//    private String message; //regarding transaction or anything liekw hich refund or something

    @Column(nullable = false)
    private String type;  // 'PAYMENT' or 'REFUND'

//    @Column(nullable = false)
//    private TransactionStatusEnum status;
    // Mapping Transaction -> User

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private TransactionStatus transactionStatus;

    @ManyToOne
    @JoinColumn
    private User user;

    // Mapping Transaction -> Ticket
    @ManyToOne
    @JoinColumn
    private Ticket ticket;
}
