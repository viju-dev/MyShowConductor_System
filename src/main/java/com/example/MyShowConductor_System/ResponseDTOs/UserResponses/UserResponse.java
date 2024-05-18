package com.example.MyShowConductor_System.ResponseDTOs.UserResponses;

import com.example.MyShowConductor_System.Entities.FeedBack;
import com.example.MyShowConductor_System.Entities.Role;
import com.example.MyShowConductor_System.Entities.Ticket;
import com.example.MyShowConductor_System.ResponseDTOs.UserResponseDTO;

import java.util.List;

public class UserResponse extends UserResponseDTO {
    private boolean isEmailVerified;
    private List<Role> roles;
    private List<Ticket> tickets;
    private List<FeedBack> feedBacks;
}
