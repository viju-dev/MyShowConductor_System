package com.example.MyShowConductor_System.ResponseDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TheatreShowsResponnseDTO {
    private String name;
    private String address;
    private String location;
    private List<ShowResponseDTO> showList;
}
