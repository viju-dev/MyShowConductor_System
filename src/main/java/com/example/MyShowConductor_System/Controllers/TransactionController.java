package com.example.MyShowConductor_System.Controllers;


import com.example.MyShowConductor_System.EntryDTOs.UserEntryDTO;
import com.example.MyShowConductor_System.Payloads.ApiResponse;
import com.example.MyShowConductor_System.Payloads.ResponseData;
import com.example.MyShowConductor_System.ResponseDTOs.TransactionResponseDto;
import com.example.MyShowConductor_System.ResponseDTOs.UserResponseDTO;
import com.example.MyShowConductor_System.Services.Impl.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    @Autowired
    private TransactionServiceImpl transactionServiceImpl;

    @GetMapping("/")
    public ResponseEntity getByTicketId(@PathVariable("ticketId") @NotNull @Positive int ticketId){

        List<TransactionResponseDto> transactions = transactionServiceImpl.getTransactionsByTicket(ticketId);
        return new ResponseEntity<>(new ApiResponse("transactions retrieved",true,new ResponseData<>(transactions)), HttpStatus.OK);

    }
    @GetMapping("/users")
    public ResponseEntity getAll(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntryDTO u =(UserEntryDTO) auth.getPrincipal();
        List<TransactionResponseDto> transactions = transactionServiceImpl.getAllByUser(u.getEmail());
        return new ResponseEntity<>(new ApiResponse("transactions retrieved",true,new ResponseData<>(transactions)), HttpStatus.OK);

    }
}
