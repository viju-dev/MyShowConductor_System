package com.example.MyShowConductor_System.Entities;

import com.example.MyShowConductor_System.Enums.TransactionStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Table
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionStatus {
    @Id
    private int id;

    @Enumerated(EnumType.STRING)
    private TransactionStatusEnum name;

    @OneToMany(mappedBy = "transactionStatus")
    private List<Transaction> transactions;
}
