package com.example.MyShowConductor_System.ResponseDTOs;

import com.example.MyShowConductor_System.Enums.ScreenTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowResponseDTO {
    private LocalDate showDate;
    private LocalTime showTime;
//    private ScreenTypeEnum screenType;
     List<ScreenTypeEnum> screenTypeEnums = new ArrayList<>();
    private String movieName;
    private String theatreName;
//    private int classicSeatPrice;
//    private int premiumSeatPrice;

}
