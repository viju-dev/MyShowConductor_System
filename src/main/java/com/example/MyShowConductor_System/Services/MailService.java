package com.example.MyShowConductor_System.Services;

import com.example.MyShowConductor_System.Entities.Show;
import com.example.MyShowConductor_System.Entities.Ticket;

import javax.mail.MessagingException;


public interface MailService {
//    basic methods
    public void sendBookingMail(Ticket ticket, Show show) throws MessagingException;

    public void sendCancellationMail(Ticket ticket) throws MessagingException;
    public void sendMail(String email,String text,String subject) throws MessagingException;

//    additional methods
//    sendReminderMail, offerMail, showReminderMail

}
