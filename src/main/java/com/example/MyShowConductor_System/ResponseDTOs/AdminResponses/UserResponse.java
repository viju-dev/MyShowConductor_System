package com.example.MyShowConductor_System.ResponseDTOs.AdminResponses;

import com.example.MyShowConductor_System.ResponseDTOs.FeedBackResponseDto;
import com.example.MyShowConductor_System.ResponseDTOs.RoleResponseDto;
import com.example.MyShowConductor_System.ResponseDTOs.TicketResponseDTO;
import com.example.MyShowConductor_System.ResponseDTOs.UserResponseDTO;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

public class UserResponse extends UserResponseDTO {

    private List<RoleResponseDto> roles = new ArrayList<>();

    private List<TicketResponseDTO> ticketList = new ArrayList<>();

    private List<FeedBackResponseDto> feedBackList = new ArrayList<>();
}
