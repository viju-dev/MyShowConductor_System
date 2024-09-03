package com.example.MyShowConductor_System.Repositories;

import com.example.MyShowConductor_System.Entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {

}
