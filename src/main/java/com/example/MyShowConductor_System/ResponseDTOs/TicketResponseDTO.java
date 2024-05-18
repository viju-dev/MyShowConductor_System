package com.example.MyShowConductor_System.ResponseDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponseDTO {

//    private String movieName;
//    private LocalDate showDate;
//    private LocalTime showTime;

//    private String transactionId;
//    private boolean status;

//    private String ticketId;
//    private int totalAmount;
////    private TheatreResponseDTO theatre;
//    private List<ShowSeat> bookedSeats; // string or create showseatResponseDto
//    private ShowResponseDTO show;
//    private UserResponseDTO user;

//    private User user;
//
//    private Show show;


    private String ticketId;
    private int totalAmount;
    //    private TheatreResponseDTO theatre;
    private List<ShowSeatResponseDto> bookedSeats; // string or create showseatResponseDto
    private ShowResponseDTO show;
    private UserResponseDTO user;
}
