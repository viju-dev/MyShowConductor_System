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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.*;

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

    @Autowired
    MailService mailService;


    public List<TicketResponseDTO> getAllByUser(int userId) {
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

        ticket.setMovieName(show.getMovie().getTitle()); //set moviename
        ticket.setTheaterName(show.getTheatre().getName());

        ticket.setShowDate(show.getShowDate());
        ticket.setShowTime(show.getShowTime());

        ticket.setTicketId(UUID.randomUUID().toString());
//        ticket.setTransactionId(UUID.randomUUID().toString());
//        ticket.setStatus(true);

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
                        seat.setBooked(true); //when that get added in show list that time updated only
                        totalPrice += seat.getPrice();
                        seat.setBookedAt(new Date());
                        seatList.add(seat);
                    }
                    else {
                        throw new RuntimeException("Seat with no "+ seat.getSeatNo()+" is Not Available");
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


//        show.setShowSeatList(seatList);//recheck we are setting or adding ???????????????
//        show.getShowSeatList().addAll(seatList);

        show.getTicketList().add(ticket);
        showRepository.save(show); //no need to save tickets
//        ticketRepository.save(ticket);

        mailService.sendBookingMail(ticket, show);
        return "Tickets booked Succefully";

    }

    public String deleteById(String ticketId) throws MessagingException {
        Ticket ticket = ticketRepository.findByTicketId(ticketId);
        int totalPrice = ticket.getTotalAmount();
        String seats = ticket.getBookedSeats();
        Show show = ticket.getShow();
        for (ShowSeat showSeat:show.getShowSeatList()){
            for (String seat:ticket.getBookedSeats().split(",")){
                if (showSeat.getSeatNo().equals(seat)){
                    showSeat.setBooked(false);
                }
            }
        }

        ticketRepository.deleteById(ticket.getId());
        mailService.sendCancellationMail(ticket);
        return "Ticket Cancelled SuccessFully";
    }

    public void sendMail(String email,String text,String subject) throws MessagingException {
        //  String body = "Hi this is to confirm your booking for seat No "+allotedSeats +"for the movie : " + ticketEntity.getMovieName();


        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setFrom("myshowconductor@gmail.com");
        mimeMessageHelper.setTo(email);
//        mimeMessageHelper.setText("<h1>Hello</h1><img src='cid:image'>",true);
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setText(text,true);
//        mimeMessageHelper.setText("my text <img src='cid:myLogo'>", true);
        mimeMessageHelper.setSubject(subject);

        FileSystemResource res = null;
        try {
            res = new FileSystemResource((new ClassPathResource("/images/myShowlogoGrey.png").getFile()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        helper.addInline("image",res);

        javaMailSender.send(mimeMessage);
    }

    public String sendEMail() throws MessagingException {
        String email = "rvdorugade@gmail.com";
        String text = "<img src='/resources/reports/images/myShowlogoGrey.png' width='200' height='120'>";
        String subject="Checking images";
        sendMail(email,text,subject);
        return  "mail sent successfully";
    }
}
