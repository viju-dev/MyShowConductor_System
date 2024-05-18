package com.example.MyShowConductor_System.ResponseDTOs.AdminResponses;

import com.example.MyShowConductor_System.ResponseDTOs.MovieResponseDTO;
import com.example.MyShowConductor_System.ResponseDTOs.ShowResponseDTO;

import java.util.List;

public class MovieResponse extends MovieResponseDTO {
    private List<ShowResponseDTO> showList;
}
