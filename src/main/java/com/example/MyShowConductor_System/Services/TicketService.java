package com.example.MyShowConductor_System.Services;

import com.example.MyShowConductor_System.Entities.Ticket;
import com.example.MyShowConductor_System.EntryDTOs.TicketEntryDTO;
import com.example.MyShowConductor_System.ResponseDTOs.TicketResponseDTO;
import javax.mail.MessagingException;
import java.util.List;

public interface TicketService {

//    basic

    public TicketResponseDTO createTicket(TicketEntryDTO ticketEntryDTO) throws MessagingException;

    public String deleteTicketById(String ticketId)throws MessagingException;

    public TicketResponseDTO getTicketById(int ticketId);
    public List<TicketResponseDTO> getTicketsByUser(int userId);


//    different methods
    public void sendMail(String email,String text,String subject) throws MessagingException ;

    public String sendEMail() throws MessagingException;

//    additional methods

//    getTicketsByShow(showId), getTicketsByMovie(movieId), getTicketsByTheatre(theatreId);


//    additional methods for absence
    List<TicketResponseDTO> getTicketsByShow(int showId); // for admin to get booked tickets data // name it bookedTickets

}
