package com.example.MyShowConductor_System.Converters;

import com.example.MyShowConductor_System.EntryDTOs.UserEntryDTO;
import com.example.MyShowConductor_System.Entities.User;

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
}
