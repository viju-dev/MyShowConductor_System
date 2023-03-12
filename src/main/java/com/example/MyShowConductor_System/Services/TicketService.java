package com.example.MyShowConductor_System.Services;

import com.example.MyShowConductor_System.Converters.TicketConvertors;
import com.example.MyShowConductor_System.EntryDTOs.TicketEntryDTO;
import com.example.MyShowConductor_System.Entities.*;
import com.example.MyShowConductor_System.Repositories.ShowRepository;
import com.example.MyShowConductor_System.Repositories.TheatreRepository;
import com.example.MyShowConductor_System.Repositories.TicketRepository;
import com.example.MyShowConductor_System.Repositories.UserRepository;
import com.example.MyShowConductor_System.ResponseDTOs.TicketResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TicketService {
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TheatreRepository theatreRepository;
    @Autowired
    ShowRepository showRepository;
    @Autowired
    JavaMailSender javaMailSender;


    public List<TicketResponseDTO> GetAllByUser(int userId) {
        List<TicketResponseDTO> ticketList = new ArrayList<>();
        for (Ticket ticket:ticketRepository.findByUserId(userId)){ //userId or user
            ticketList.add(TicketConvertors.EntityToResponse(ticket));
        }
        return ticketList;
    }

    public String addTicket(TicketEntryDTO ticketEntryDTO) throws MessagingException {
        Ticket ticket = new Ticket();

        User user = userRepository.findById(ticketEntryDTO.getUserId()).get();
        ticket.setUser(user);
        Show show = showRepository.findById(ticketEntryDTO.getShowId()).get();
        ticket.setShow(show);

        ticket.setMovieName(show.getMovie().getMovieName()); //set moviename
        ticket.setTheaterName(show.getTheatre().getName());

        ticket.setShowDate(show.getShowDate());
        ticket.setShowTime(show.getShowTime());

        ticket.setTicketId(UUID.randomUUID().toString());
        String confirmSeats = "";
        List<ShowSeat> seatList = new ArrayList<>();
        int totalPrice = 0;
        boolean invalidSeat = true;
        for (String reqSeat : ticketEntryDTO.getRequestedSeats()){
            invalidSeat = true;
            for (ShowSeat seat:showRepository.findById(ticketEntryDTO.getShowId()).get().getShowSeatList()){
                if (reqSeat.equals(seat.getSeatNo()) ){
                    invalidSeat = false;
                    if(!seat.isBooked()){
                        System.out.println(seat.isBooked()) ;
                        confirmSeats += seat.getSeatNo()+",";
                        seat.setBooked(true);
                        totalPrice += seat.getPrice();
                        seat.setBookedAt(new Date());
                        seatList.add(seat);
                    }
                    else {
                        throw new RuntimeException("Seat is Not Available");
                    }
                }
//                else {
//                    throw new RuntimeException("Invalid Seat Number Or Seat is Not Available");
//                }
            }
            if (invalidSeat){
                throw new RuntimeException("Invalid seat number");
            }
        }

        ticket.setBookedSeats(confirmSeats);
        ticket.setTotalAmount(totalPrice);

        show.setShowSeatList(seatList);
        show.getTicketList().add(ticket);
        showRepository.save(show); //no need to save tickets
//        ticketRepository.save(ticket);


        String mail = ticket.getUser().getEmail();
        String text = "<p>Hey,"+"<b>"+ticket.getUser().getName()+"</b></p>"+"<p> Your ticket for "+ticket.getMovieName()+" with seats <b>"+ticket.getBookedSeats()+"</b> have been Booked SuccessFully...! </p>";
        String subject = "Your Tickets";//Ticket Confirmation
        sendMail(mail,text,subject);
        return "Tickets booked Succefully";

    }

    public String deleteById(String ticketId) throws MessagingException {
        Ticket ticket = ticketRepository.findByTicketId(ticketId);
        String seats = ticket.getBookedSeats();
        Show show = ticket.getShow();
        for (ShowSeat showSeat:show.getShowSeatList()){
            for (String seat:ticket.getBookedSeats().split(",")){
                if (showSeat.getSeatNo().equals(seat)){
                    showSeat.setBooked(false);
                }
            }
        }
        String mail = ticket.getUser().getEmail();
        String text = "Hey,"+ticket.getUser().getName()+" your ticket for "+ticket.getMovieName()+" with seats "+ticket.getBookedSeats()+" have been Cancelled SuccessFully...!";
        String subject = "Ticket Cancellation";

        ticketRepository.deleteById(ticket.getId());


        sendMail(mail,text,subject);

        return "Ticket Cancelled SuccessFully";
    }

    public void sendMail(String email,String text,String subject) throws MessagingException {
        //  String body = "Hi this is to confirm your booking for seat No "+allotedSeats +"for the movie : " + ticketEntity.getMovieName();


        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setFrom("myshowconductor@gmail.com");
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setText(text,true);
//        mimeMessageHelper.setText("my text <img src='cid:myLogo'>", true);
        mimeMessageHelper.setSubject(subject);

        javaMailSender.send(mimeMessage);
    }
}
