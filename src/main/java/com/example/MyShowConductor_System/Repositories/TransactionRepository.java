package com.example.MyShowConductor_System.Repositories;

import com.example.MyShowConductor_System.Entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
}
