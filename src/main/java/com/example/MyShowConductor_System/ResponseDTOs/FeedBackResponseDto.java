package com.example.MyShowConductor_System.ResponseDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedBackResponseDto {
    private String message;
    private Double rating;
    private UserResponseDTO user;
}
