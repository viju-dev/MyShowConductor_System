package com.example.MyShowConductor_System.Repositories;

import com.example.MyShowConductor_System.Entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Integer> {
    List<Ticket> findByUserId(int userId);


    Ticket findByTicketId(String ticketId);
}
