package com.example.MyShowConductor_System.ResponseDTOs;

import com.example.MyShowConductor_System.Enums.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponseDto {

    private UserRoleEnum name;
    private List<UserResponseDTO> users;
}
