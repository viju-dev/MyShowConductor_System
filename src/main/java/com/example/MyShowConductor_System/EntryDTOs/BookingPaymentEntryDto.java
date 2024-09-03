package com.example.MyShowConductor_System.EntryDTOs;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class BookingPaymentEntryDto {

    @Valid
    @NotNull
    private TicketEntryDTO ticketEntryDTO;

    @Valid
    @NotNull
    private PaymentEntryDto paymentEntryDto;
}
