package com.example.MyShowConductor_System.Converters;

import com.example.MyShowConductor_System.EntryDTOs.TicketEntryDTO;
import com.example.MyShowConductor_System.Entities.Ticket;
import com.example.MyShowConductor_System.ResponseDTOs.TicketResponseDTO;

import java.util.List;

public class TicketConvertors {

    public static Ticket EntryToEntity(TicketEntryDTO ticketEntryDTO){
        List<String> seatList = ticketEntryDTO.getRequestedSeats();
        String bookedSeats = "";
        for (String bookSeat:seatList){
            bookedSeats += bookSeat;
        }
        Ticket ticket = Ticket.builder()
                .bookedSeats(bookedSeats).build();
        return ticket;
    }
    public static TicketResponseDTO EntityToResponse(Ticket ticket){
        TicketResponseDTO ticketResponseDTO = TicketResponseDTO.builder()
//                .transactionId(ticket.getTransactionId())
//                .status(ticket.isStatus())
                .ticketId(ticket.getTicketId())
                .theaterName(ticket.getTheaterName())
                .showDate(ticket.getShowDate())
                .showTime(ticket.getShowTime())
                .movieName(ticket.getMovieName())
                .bookedSeats(ticket.getBookedSeats())
                .totalAmount(ticket.getTotalAmount())
                .build();
        return ticketResponseDTO;
    }
}
