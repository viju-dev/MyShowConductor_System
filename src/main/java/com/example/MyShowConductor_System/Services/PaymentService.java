package com.example.MyShowConductor_System.Services;

import com.example.MyShowConductor_System.EntryDTOs.PaymentEntryDto;

public interface PaymentService {
    public void makePayment(PaymentEntryDto paymentEntryDto, String ticketId, String email);

}
