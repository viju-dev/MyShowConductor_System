package com.example.MyShowConductor_System.ResponseDTOs;

import com.example.MyShowConductor_System.Enums.SeatTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShowSeatResponseDto {
    private int id;
    private String seatNo;

    private boolean isBooked;

    private SeatTypeEnum seatType;

    private int price;

    private Date bookedAt;

}
