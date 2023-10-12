package com.example.MyShowConductor_System.ResponseDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponseDTO {

    private String movieName;
    private LocalDate showDate;
    private LocalTime showTime;
    private int totalAmount;
//    private String transactionId;
//    private boolean status;
    private String ticketId;
    private String theaterName;
    private String bookedSeats;

//    private User user;
//
//    private Show show;
}
