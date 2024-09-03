package com.example.MyShowConductor_System.Entities;

import com.example.MyShowConductor_System.Enums.TransactionStatusEnum;
import com.example.MyShowConductor_System.Enums.TransactionTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    @CreationTimestamp()
    @Column(nullable = false)
    private LocalDateTime createdAt;
//can add localtime as well
    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionTypeEnum type;  // 'PAYMENT' or 'REFUND'

    @Column(nullable = false)
    private TransactionStatusEnum status;

    @ManyToOne
    @JoinColumn
    private User user;

    // Mapping Transaction -> Ticket
    @OneToOne
    @JoinColumn
    private Ticket ticket;

    @OneToMany(mappedBy = "transaction",cascade = CascadeType.ALL)
    private List<Payment> payments = new ArrayList<>();
}
