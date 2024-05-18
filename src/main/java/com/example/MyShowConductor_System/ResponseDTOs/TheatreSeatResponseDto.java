package com.example.MyShowConductor_System.ResponseDTOs;

import com.example.MyShowConductor_System.Enums.SeatTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TheatreSeatResponseDto {
  //i guess no need to attach this seats with theatre if screen was there that will be different thing
//    private int id;

    private String seatNo;//a12 b1 c23 maybe thats why seatNo is in string

    private SeatTypeEnum type;//Classic , Premium, etc

}
