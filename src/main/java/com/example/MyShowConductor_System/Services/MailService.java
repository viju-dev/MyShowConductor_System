package com.example.MyShowConductor_System.Services;

import com.example.MyShowConductor_System.Entities.Show;
import com.example.MyShowConductor_System.Entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service


public class MailService {
    @Autowired
    JavaMailSender javaMailSender;

    public void sendBookingMail(Ticket ticket, Show show) throws MessagingException {
        String text = "<table  style='width:60%;height:auto;border:1px solid #e5e5e5;border-radius: 1px;background-color:Grey#fff;margin: 0 auto'>" +
                "<tr>" +
                "      <td>" +
                "    <table  style='width:100%'>" +
                "    <tbody style='text-align:center'>" +
                "      <tr>" +
                "        <td>" +
                "          <img src='cid:image' width='200' height='120'>" +
                "        </td>" +
                "      </tr>" +
                "      <tr >" +
                "      <td>" +
                "       <h3> Hey, <b>"+ticket.getUser().getName()+"</b></h3>" +
                "      </td>" +
                "      </tr>" +
                "      <tr>" +
                "      <td>" +
                "        <h2 style='color:green'><b>Your Booking is Confirmed...!</b></h2>" +
                "      </td>" +
                "      </tr>" +
                "      <tr>" +
                "        <td><h2>Booking id :<b style='color:black;'> "+ ticket.getTicketId()+"</b></h2></td>" +
                "      </tr>" +
                "      <tr>" +
                "        <td>" +
                "          <h1 style='border:1px dashed  #8c8c8c; border-width: 1px;'>" +
                "          </h1>" +
                "        </td>" +
                "      </tr>" +
                "     <!--  <tr>" +
                "        <td>" +
                "        " +
                "          <span>Details :</span>" +
                "         " +
                "        </td>" +
                "      </tr> -->" +
                "      " +
                "     " +
                "    </tbody>" +
                "    </table>" +
                "    <table style='background-color:#f5f5f5;border:1px solid transparent;border-radius: 5px;width: 100%;color: #737373;text-align: center;'>" +
                "    <tbody>" +
                "        <tr>" +
                "        <td><h2>Movie : <b style='color:black;'>"+ticket.getMovieName()+"</b></h2></td>" +
                "      </tr>  " +
                "      <tr>" +
                "        <td><h3>Date and time : <span style='color: black;'>"+ show.getShowDate()+" | "+show.getShowTime()+"</span> " +
                "        </h3>    </td>" +
                "      </tr>  " +
                "      <tr>" +
                "        <td><h4>Theatre : <span>"+show.getTheatre().getName()+", "+show.getTheatre().getAddress()+", "+show.getTheatre().getLocation()+"</span></h4>" +
                "    </td>" +
                "      </tr>  " +
                "      <tr>" +
                "        <td><h3>Seats : <span style='color:black;'>"+ticket.getBookedSeats()+"</span></h3></td>" +
                "      </tr>  " +
                "      <!-- <tr>" +
                "        <td>" +
                "          <p>" +
                "          <span>" +
                "            Bill :" +
                "          </span>" +
                "          </p>" +
                "        </td>" +
                "      </tr>  -->" +
                "    </tbody>" +
                "    </table>" +
                "    <h3 style=' color:#828282;margin: 15px;';>" +
                "     Order Summary :" +
                "    </h3>" +
                "    <table style='width:100%;text-align:center;border:2px solid #f1f1f1; margin-bottom: 5%;'>" +
                "            <tr>" +
                "            <th>" +
                "              Ticket Qnty" +
                "            </th>" +
                "            <th>" +
                "              Total" +
                "            </th>" +
                "          </tr>  " +
                "          " +
                "            <tbody style='color:black'>" +
                "              <tr>" +
                "              <td>" +ticket.getBookedSeats().split(",").length+
                "              </td>" +
                "              <td><b>"+ticket.getTotalAmount()+" Rs.</b>" +
                "              </td>" +
                "            </tr>" +
                "            </tbody>" +
                "          </table>" +
                "      <tr>" +
                "        <td><h4 style='color:#737373; margin-left: 10px'>Booking Date and  Time : <span style='color: black;'>"+ LocalDate.now()+" | "+ LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))+"</span> " +
                "        </h4>    </td>" +
                "      </tr>  " +
                "      </td>" +
                "    </tr>" +
                "    </table>";

        //        System.out.println(show.getTicketList().size());
        String mail = ticket.getUser().getEmail();
//        String text = "<p>Hey,"+"<b>"+ticket.getUser().getName()+"</b></p>"+"<p> Your ticket for "+ticket.getMovieName()+" with seats <b>"+ticket.getBookedSeats()+"</b> have been Booked SuccessFully...! </p>";
        String subject = "Your Tickets";//Ticket Confirmation

        sendMail(mail,text,subject);
        return ;
    }

    public void sendCancellationMail(Ticket ticket) throws MessagingException {
        String text =

                "    <table" +
                        "  style='width:50%;height:auto;border:1px solid #e5e5e5;border-radius: 1px;background-color:Grey#fff;margin: 0 auto'>" +
                        "  <tr>" +
                        "    <td>" +
                        "      <table style='width:100%'>" +
                        "        <tbody style='text-align:center'>" +
                        "          <tr>" +
                        "            <td>" +
                        "              <img src='cid:image' width='200' height='120'>" +
                        "            </td>" +
                        "          </tr>" +
                        "          <tr>" +
                        "            <td>" +
                        "              <h3> Hey, <b>"+ticket.getUser().getName()+"</b></h3>" +
                        "            </td>" +
                        "          </tr>" +
                        "          <tr>" +
                        "            <td>" +
                        "              <h2 style='color:red'><b>Your Tickets have been Successfully cancelled...!<br>Too bad you couldn't make it" +
                        "                  to this show :(</br></h2>" +
                        "            </td>" +
                        "          </tr>" +
                        "          <tr>" +
                        "            <td>" +
                        "              <h4>Your refund worth <b> "+ticket.getTotalAmount()+" Rs. </b> will be credited to your <br> Bank account in 5 to 12" +
                        "                working" +
                        "                days.</h4>" +
                        "            </td>" +
                        "          </tr>" +
                        "          <tr>" +
                        "            <td>" +
                        "              <h1 style='border:1px dashed  #8c8c8c; border-width: 1px;'>" +
                        "              </h1>" +
                        "            </td>" +
                        "          </tr>" +
                        "        </tbody>" +
                        "      </table>" +
                        "  <tr>" +
                        "    <td>" +
                        "      <h4 style='color:#737373; margin-left: 10px;'>Cancellation Date and Time : <span style='color: black;'>" + LocalDate.now()+" | "+LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))+"</span></h4>" +
                        "    </td>" +
                        "  </tr>" +
                        "  </td>" +
                        "  </tr>" +
                        "</table>";
        String mail = ticket.getUser().getEmail();
//        String text = "Hey,"+ticket.getUser().getName()+" your ticket for "+ticket.getMovieName()+" with seats "+ticket.getBookedSeats()+" have been Cancelled SuccessFully...!";
        String subject = "Ticket Cancellation";
//        ticketRepository.deleteById(ticket.getId());

        sendMail(mail,text,subject);

        return;
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
}
