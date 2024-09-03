package com.example.MyShowConductor_System.Repositories;

import com.example.MyShowConductor_System.Entities.Ticket;
import com.example.MyShowConductor_System.Enums.TicketStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket,Integer> {
    List<Ticket> findByUserId(int userId);


    Optional<Ticket> findByTicketId(String ticketId);

    List<Ticket> findByStatus(TicketStatusEnum ticketStatusEnum);
}