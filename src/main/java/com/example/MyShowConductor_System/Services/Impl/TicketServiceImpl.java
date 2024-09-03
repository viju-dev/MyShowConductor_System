package com.example.MyShowConductor_System.Services.Impl;

import com.example.MyShowConductor_System.EntryDTOs.TicketEntryDTO;
import com.example.MyShowConductor_System.Enums.TicketStatusEnum;
import com.example.MyShowConductor_System.Exceptions.ResourceNotFoundException;
import com.example.MyShowConductor_System.Repositories.*;
import com.example.MyShowConductor_System.Entities.Show;
import com.example.MyShowConductor_System.Entities.ShowSeat;
import com.example.MyShowConductor_System.Entities.Ticket;
import com.example.MyShowConductor_System.Entities.User;
import com.example.MyShowConductor_System.ResponseDTOs.ShowResponseDTO;
import com.example.MyShowConductor_System.ResponseDTOs.TicketResponseDTO;
import com.example.MyShowConductor_System.Services.TicketService;
import com.example.MyShowConductor_System.Services.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private ShowServiceImpl showServiceImpl;

    @Autowired
    ShowRepository showRepository;
    @Autowired
    TransactionService transactionService;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    MailServiceImpl mailServiceImpl;

    @Autowired
    ModelMapper modelMapper;



    @Override
    public List<TicketResponseDTO> getTicketsByUser(int userId) {
        User user = userServiceImpl.getUserEntityById(userId);
        List<TicketResponseDTO> ticketList = ticketRepository.findAll().stream().filter(ticket -> ticket.getUser().equals(ticket)).map(ticket -> this.modelMapper.map(ticket,TicketResponseDTO.class)).collect(Collectors.toList());
        return ticketList;
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    @Override
    public TicketResponseDTO createTicket(TicketEntryDTO ticketEntryDTO) {
        Ticket ticket = new Ticket();
        User user = userServiceImpl.getUserEntityById(ticketEntryDTO.getUserId());
        ticket.setUser(user);
        Show show = showServiceImpl.getShowEntityById(ticketEntryDTO.getShowId());
        ticket.setShow(show);

        ticket.setTicketId(UUID.randomUUID().toString());
//        ticket.setTransactionId(UUID.randomUUID().toString());
//        ticket.setStatus(true);

//        ticket staus set to pending
        ticket.setStatus(TicketStatusEnum.PENDING);

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


        //created transaction its not confirmed yet
        transactionService.createTransaction(savedTicket);
//
//        mailServiceImpl.sendBookingMail(ticket, show);

        return this.modelMapper.map(savedTicket,TicketResponseDTO.class);

    }





    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    @Override
    public String deleteTicketById(String ticketId) throws MessagingException {
        Ticket ticket = ticketRepository.findByTicketId(ticketId).orElseThrow(() -> new ResourceNotFoundException("Ticket", "id", ticketId));
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
    public Ticket getTicketEntityById(String ticketId) {
        Ticket ticket = ticketRepository.findByTicketId(ticketId).orElseThrow(()->new ResourceNotFoundException("ticket","id",ticketId));
        return ticket;
    }

    @Override
    public Ticket saveTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public List<Ticket> findByStatus(TicketStatusEnum status) {
        return ticketRepository.findByStatus(status);
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
        Show show = showServiceImpl.getShowEntityById(showId);
        List<TicketResponseDTO> tickets = show.getTicketList().stream().map(ticket -> this.modelMapper.map(ticket,TicketResponseDTO.class)).collect(Collectors.toList());
        return tickets;
    }

}
