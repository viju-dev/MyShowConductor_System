package com.example.MyShowConductor_System.Controllers;

import com.example.MyShowConductor_System.EntryDTOs.TicketEntryDTO;
import com.example.MyShowConductor_System.Payloads.ApiResponse;
import com.example.MyShowConductor_System.Payloads.ResponseData;
import com.example.MyShowConductor_System.ResponseDTOs.TicketResponseDTO;
import com.example.MyShowConductor_System.Services.Impl.TicketServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/tickets")
public class TicketController {
    @Autowired
    TicketServiceImpl ticketServiceImpl;

//    @PreAuthorize("hasRole('USER')") both  can create
    @PostMapping("/create")//name as createTicket
    public ResponseEntity addTicket(@RequestBody TicketEntryDTO ticketEntryDTO) throws MessagingException {
        TicketResponseDTO ticket = ticketServiceImpl.createTicket(ticketEntryDTO);
        return new ResponseEntity<>(new ApiResponse<>("Ticket created successfully",true,new ResponseData<>(ticket)),HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
//    PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @GetMapping("/user/{userId}")
    public ResponseEntity getAllByUser(@PathVariable("userId") @NotNull @Positive int userId){
        List<TicketResponseDTO> tickets = ticketServiceImpl.getTicketsByUser(userId);
        return new ResponseEntity<>(new ApiResponse<>("Ticket created successfully",true,new ResponseData<>(tickets)),HttpStatus.OK);

    }
    @DeleteMapping("/{ticketId}")
    public ResponseEntity deleteById(@PathVariable("ticketId") @NotBlank int ticketId){
        ticketServiceImpl.getTicketById(ticketId);
        return new ResponseEntity<>(new ApiResponse<>("Ticket deleted successfully",true),HttpStatus.OK);
    }

    @PostMapping("/sendEMail")
    public ResponseEntity sendMail(){
        String result = null;
        try {
            result = ticketServiceImpl.sendEMail();
            return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
        } catch (MessagingException e) {
            String response = e.getMessage();
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/show/{showId}") // for admin
    public ResponseEntity getTicketsByShow(@PathVariable int showId) {
        List<TicketResponseDTO> tickets = ticketServiceImpl.getTicketsByShow(showId);
        return new ResponseEntity<>(new ApiResponse<>("Ticket created successfully",true,new ResponseData<>(tickets)),HttpStatus.OK);

    }
}
