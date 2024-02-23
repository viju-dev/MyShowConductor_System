package com.example.MyShowConductor_System.Controllers;

import com.example.MyShowConductor_System.EntryDTOs.TicketEntryDTO;
import com.example.MyShowConductor_System.ResponseDTOs.TicketResponseDTO;
import com.example.MyShowConductor_System.Services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@Validated
@RequestMapping("/tickets")
public class TicketController {
    @Autowired
    TicketService ticketService;
    @PostMapping("/create")//name as createTicket
    public ResponseEntity addTicket(@RequestBody TicketEntryDTO ticketEntryDTO){

        try {
            String result = ticketService.addTicket(ticketEntryDTO);
            return new ResponseEntity<>(result,HttpStatus.CREATED);
        } catch (MessagingException e) {
//            throw new RuntimeException(e);
            String response = e.getMessage();
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/by-user")
    public ResponseEntity getAllByUser(@RequestParam("userId") @NotNull @Positive int userId){
        List<TicketResponseDTO> ticketList = ticketService.getAllByUser(userId);
        return new ResponseEntity<>(ticketList, HttpStatus.OK);
    }
    @DeleteMapping("/by-id")
    public ResponseEntity deleteById(@RequestParam("ticketId") @NotBlank String ticketId){
        String result = null;
        try {
            result = ticketService.deleteById(ticketId);
            return new ResponseEntity<>(result,HttpStatus.GONE);
        } catch (MessagingException e) {
            String response = e.getMessage();
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/sendEMail")
    public ResponseEntity sendMail(){
        String result = null;
        try {
            result = ticketService.sendEMail();
            return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
        } catch (MessagingException e) {
            String response = e.getMessage();
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }



}
