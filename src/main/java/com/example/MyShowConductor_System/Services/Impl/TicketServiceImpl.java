package com.example.MyShowConductor_System.Services.Impl;

import com.example.MyShowConductor_System.EntryDTOs.TicketEntryDTO;
import com.example.MyShowConductor_System.Exceptions.ResourceNotFoundException;
import com.example.MyShowConductor_System.Repositories.ShowRepository;
import com.example.MyShowConductor_System.Repositories.TheatreRepository;
import com.example.MyShowConductor_System.Repositories.TicketRepository;
import com.example.MyShowConductor_System.Repositories.UserRepository;
import com.example.MyShowConductor_System.Entities.Show;
import com.example.MyShowConductor_System.Entities.ShowSeat;
import com.example.MyShowConductor_System.Entities.Ticket;
import com.example.MyShowConductor_System.Entities.User;
import com.example.MyShowConductor_System.ResponseDTOs.ShowResponseDTO;
import com.example.MyShowConductor_System.ResponseDTOs.TicketResponseDTO;
import com.example.MyShowConductor_System.Services.TicketService;
import org.modelmapper.ModelMapper;
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
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {
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
    MailServiceImpl mailServiceImpl;

    @Autowired
    ModelMapper modelMapper;



    @Override
    public List<TicketResponseDTO> getTicketsByUser(int userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","id",Integer.toString(userId)));
        List<TicketResponseDTO> ticketList = ticketRepository.findAll().stream().filter(ticket -> ticket.getUser().equals(ticket)).map(ticket -> this.modelMapper.map(ticket,TicketResponseDTO.class)).collect(Collectors.toList());
        return ticketList;
    }

    @Override
    public TicketResponseDTO createTicket(TicketEntryDTO ticketEntryDTO) throws MessagingException {
        Ticket ticket = new Ticket();
        User user = userRepository.findById(ticketEntryDTO.getUserId()).orElseThrow(()->new ResourceNotFoundException("user","id",Integer.toString(ticketEntryDTO.getUserId())));
        ticket.setUser(user);
        Show show = showRepository.findById(ticketEntryDTO.getShowId()).orElseThrow(()->new ResourceNotFoundException("user","id",Integer.toString(ticketEntryDTO.getShowId())));
        ticket.setShow(show);

        ticket.setTicketId(UUID.randomUUID().toString());
//        ticket.setTransactionId(UUID.randomUUID().toString());
//        ticket.setStatus(true);

        List<ShowSeat> confirmSeats = new ArrayList<>();
        int totalPrice = 0;
//        boolean invalidSeat = true;
        for (ShowSeat reqSeat : ticketEntryDTO.getRequestedSeats()){
//            invalidSeat = true;
            for (ShowSeat seat:show.getShowSeatList()){
                if (reqSeat.getSeatNo().equals(seat.getSeatNo()) ){
//                    invalidSeat = false;
                    if(!seat.isBooked()){
//                        System.out.println(seat.isBooked()) ;
                        confirmSeats.add(seat);
                        seat.setBooked(true); //when that get added in show list that time updated only
                        totalPrice += seat.getPrice();
                        seat.setBookedAt(new Date());
                    }
                    else {
                        throw new RuntimeException("Seat with no "+ seat.getSeatNo()+" is Not Available");
                    }
                }
                else {
                    throw new RuntimeException("Invalid Seat Number Or Seat is Not Available");
                }
            }
//            if (invalidSeat){
//                throw new RuntimeException("Invalid seat number");
//            }
        }

        ticket.setBookedSeats(confirmSeats);
        ticket.setTotalAmount(totalPrice);

        show.setShowSeatList(confirmSeats);//recheck we are setting or adding ???????????????
//        show.getShowSeatList().addAll(confirmSeats);

        show.getTicketList().add(ticket);
        showRepository.save(show); //no need to save tickets
        Ticket savedTicket = ticketRepository.save(ticket);

        mailServiceImpl.sendBookingMail(ticket, show);
        return this.modelMapper.map(savedTicket,TicketResponseDTO.class);

    }

    @Override
    public String deleteTicketById(String ticketId) throws MessagingException {
        Ticket ticket = ticketRepository.findByTicketId(ticketId);
        int totalPrice = ticket.getTotalAmount();
        List<ShowSeat> seats = ticket.getBookedSeats();
        for(ShowSeat seat : seats){
            seat.setBooked(false);
            totalPrice+=seat.getPrice();
        }
//        Show show = ticket.getShow();
//        show.getShowSeatList().stream().forEach(showSeat -> seats.stream().forEach(seat -> {
//            if (seat.getSeatNo().equals(showSeat.getSeatNo())){
//                showSeat.setBooked(false);
//            }
//
//        }));
//--------------------
//
////        String seats = ticket.getBookedSeats();
//        Show show = ticket.getShow();
//        for (ShowSeat showSeat:show.getShowSeatList()){
////            for (String seat:ticket.getBookedSeats().split(",")){
////                if (showSeat.getSeatNo().equals(seat)){
////                    showSeat.setBooked(false);
////                }
////            }
//        }

        ticketRepository.deleteById(ticket.getId());
        mailServiceImpl.sendCancellationMail(ticket);
        return "Ticket Cancelled SuccessFully";
    }

    @Override
    public TicketResponseDTO getTicketById(int ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(()->new ResourceNotFoundException("ticket","id",Integer.toString(ticketId)));
        return this.modelMapper.map(ticket,TicketResponseDTO.class);
    }


    @Override
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

    @Override
    public String sendEMail() throws MessagingException {
        String email = "rvdorugade@gmail.com";
        String text = "<img src='/resources/reports/images/myShowlogoGrey.png' width='200' height='120'>";
        String subject="Checking images";
        sendMail(email,text,subject);
        return  "mail sent successfully";
    }

    @Override
    public List<TicketResponseDTO> getTicketsByShow(int showId) {
        Show show = showRepository.findById(showId).orElseThrow(()-> new ResourceNotFoundException("show","id",Integer.toString(showId)));
        List<TicketResponseDTO> tickets = show.getTicketList().stream().map(ticket -> this.modelMapper.map(ticket,TicketResponseDTO.class)).collect(Collectors.toList());
        return tickets;
    }

}
