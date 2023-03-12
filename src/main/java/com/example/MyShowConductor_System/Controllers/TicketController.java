package com.example.MyShowConductor_System.Controllers;

import com.example.MyShowConductor_System.EntryDTOs.TicketEntryDTO;
import com.example.MyShowConductor_System.ResponseDTOs.TicketResponseDTO;
import com.example.MyShowConductor_System.Services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    @Autowired
    TicketService ticketService;
    @PostMapping("/addTicket")
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

    @GetMapping("/GetAllByUser")
    public ResponseEntity GetAllByUser(@RequestParam("userId") int userId){
        List<TicketResponseDTO> ticketList = ticketService.GetAllByUser(userId);
        return new ResponseEntity<>(ticketList, HttpStatus.FOUND);
    }
    @DeleteMapping("/deleteById")
    public ResponseEntity deleteById(@RequestParam("ticketId") String ticketId){
        String result = null;
        try {
            result = ticketService.deleteById(ticketId);
            return new ResponseEntity<>(result,HttpStatus.GONE);
        } catch (MessagingException e) {
            String response = e.getMessage();
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }

    }



}
