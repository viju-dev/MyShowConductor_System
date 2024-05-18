package com.example.MyShowConductor_System.EntryDTOs;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class FeedBackEntryDTO {
//    @NotBlank
//    private String name;
//
//    @NotBlank
//    @Email
//    private String email;

    @NotEmpty
    private String message;

    @NotEmpty
    @Min(value = 0)
    @Max(value = 10)
    private Double rating;


}
