package com.example.MyShowConductor_System.EntryDTOs;

import com.example.MyShowConductor_System.Enums.PaymentMethodEnum;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

@Data
public class PaymentEntryDto {

    @NotEmpty
    private PaymentMethodEnum paymentMethod;

    @NotEmpty
    private String paymentAcc;

    @NotEmpty
    private int userId;

}
