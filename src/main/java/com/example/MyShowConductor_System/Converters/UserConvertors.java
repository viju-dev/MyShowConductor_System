package com.example.MyShowConductor_System.Converters;

import com.example.MyShowConductor_System.EntryDTOs.UserEntryDTO;
import com.example.MyShowConductor_System.Entities.User;
import com.example.MyShowConductor_System.ResponseDTOs.UserResponseDTO;

public class UserConvertors {
    public static User EntryToEntity(UserEntryDTO userEntryDTO){
        User user = User.builder()
                .name(userEntryDTO.getName())
                .age(userEntryDTO.getAge())
                .gender(userEntryDTO.getGender())
                .mobNo(userEntryDTO.getMobNo())
                .email(userEntryDTO.getEmail())
                .address(userEntryDTO.getAddress())
                .build();
        //                .location(userEntryDTO.getLocation())
        return user;
    }

    public static UserResponseDTO EntityToResponse(User user){
        UserResponseDTO user1 = UserResponseDTO.builder()
                .name(user.getName())
                .age(user.getAge())
                .gender(user.getGender())
                .mobNo(user.getMobNo())
                .email(user.getEmail())
                .address(user.getAddress())
                .build();
        //                .location(userEntryDTO.getLocation())
        return user1;
    }
}
