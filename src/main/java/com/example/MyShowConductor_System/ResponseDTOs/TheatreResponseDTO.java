package com.example.MyShowConductor_System.ResponseDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TheatreResponseDTO {
    private String name;
    private String address;
    private String location;
    private String movieName;
    private String theatreName;
    private int classicSeatCount;
    private int premiumSeatCount;
}
