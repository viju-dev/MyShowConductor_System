package com.example.MyShowConductor_System.Services.Impl;

import com.example.MyShowConductor_System.Entities.Payment;
import com.example.MyShowConductor_System.Entities.ShowSeat;
import com.example.MyShowConductor_System.Entities.Ticket;
import com.example.MyShowConductor_System.Entities.User;
import com.example.MyShowConductor_System.EntryDTOs.PaymentEntryDto;
import com.example.MyShowConductor_System.Enums.TicketStatusEnum;
import com.example.MyShowConductor_System.Enums.TransactionStatusEnum;
import com.example.MyShowConductor_System.Repositories.PaymentRepository;
import com.example.MyShowConductor_System.Services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private TicketServiceImpl ticketServiceImpl;

    @Autowired
    private TransactionServiceImpl transactionServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private MailServiceImpl mailServiceImpl;

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void confirmTicketPayment(String ticketId,TransactionStatusEnum status) throws MessagingException {
        if(status.toString().equals("SUCCESSULL")){
            Ticket ticket = ticketServiceImpl.getTicketEntityById(ticketId);
            ticket.setStatus(TicketStatusEnum.CONFIRMED);
            ticketServiceImpl.saveTicket(ticket);

            // Now create the transaction and send the booking mail
            transactionServiceImpl.createTransaction(ticket);
            mailServiceImpl.sendBookingMail(ticket, ticket.getShow());
        }
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void handleFailedPayment(String ticketId) {
        Ticket ticket = ticketServiceImpl.getTicketEntityById(ticketId);
        ticket.setStatus(TicketStatusEnum.FAILED);
        for (ShowSeat seat : ticket.getBookedSeats()) {
            seat.setBooked(false);
        }
        //ensure if tickets of show getting updated
        ticketServiceImpl.saveTicket(ticket);
    }


    @Scheduled(fixedDelay = 60000) // runs every 60 seconds
    public void checkPendingPayments() {
        List<Ticket> pendingTickets = ticketServiceImpl.findByStatus(TicketStatusEnum.PENDING);
        for (Ticket ticket : pendingTickets) {
            if (isPaymentTimeout(ticket)) {
                handleFailedPayment(ticket.getTicketId());
            }
        }
    }

    private boolean isPaymentTimeout(Ticket ticket) {
        // return true if the current time is more than 'x' minutes from the ticket's creation time
        return Duration.between(ticket.getCreatedAt(), LocalDateTime.now()).toMinutes() > 3;
    }

    @Override
    public void makePayment(PaymentEntryDto paymentEntryDto, String ticketId, String email) {
        User user = userServiceImpl.getUserEntityByEmail(email);
        Payment payment = new Payment();
        payment.setPaymentAcc(paymentEntryDto.getPaymentAcc());
        payment.setPaymentStatus(TransactionStatusEnum.SUCCESSULL);
        payment.setPaymentMethod(paymentEntryDto.getPaymentMethod());
        payment.setUser(user);
        Payment savedPayment = paymentRepository.save(payment);

        try {
            this.confirmTicketPayment(ticketId,payment.getPaymentStatus());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return ;
    }
}
