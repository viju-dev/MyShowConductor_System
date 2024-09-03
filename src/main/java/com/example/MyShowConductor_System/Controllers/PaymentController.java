package com.example.MyShowConductor_System.Controllers;

import com.example.MyShowConductor_System.EntryDTOs.PaymentEntryDto;
import com.example.MyShowConductor_System.Payloads.ApiResponse;
import com.example.MyShowConductor_System.Payloads.ResponseData;
import com.example.MyShowConductor_System.ResponseDTOs.TransactionResponseDto;
import com.example.MyShowConductor_System.Services.Impl.PaymentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.security.Principal;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/payments")
public class PaymentController {
    @Autowired
    private PaymentServiceImpl paymentServiceImpl;

    @PostMapping("/tickets/{ticketId}")
    public ResponseEntity makePayment(@RequestBody()PaymentEntryDto paymentEntryDto,@PathVariable("ticketId") @NotNull @Positive String ticketId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Principal principal = (Principal) auth.getPrincipal();
        paymentServiceImpl.makePayment(paymentEntryDto,ticketId,principal.getName());
        return new ResponseEntity<>(new ApiResponse("transactions retrieved",true), HttpStatus.CREATED);

    }
}
