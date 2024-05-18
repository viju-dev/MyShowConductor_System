package com.example.MyShowConductor_System.Repositories;

import com.example.MyShowConductor_System.Entities.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionStatusRepository extends JpaRepository<TransactionStatus,Integer> {
}
