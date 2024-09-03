package com.example.MyShowConductor_System.Controllers;


import com.example.MyShowConductor_System.EntryDTOs.FeedBackEntryDTO;
import com.example.MyShowConductor_System.EntryDTOs.UserEntryDTO;
import com.example.MyShowConductor_System.Payloads.ApiResponse;
import com.example.MyShowConductor_System.Payloads.ResponseData;
import com.example.MyShowConductor_System.ResponseDTOs.FeedBackResponseDto;
import com.example.MyShowConductor_System.ResponseDTOs.UserResponseDTO;
import com.example.MyShowConductor_System.Services.Impl.FeedBackServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/feedbacks")
public class FeedBackController {

    @Autowired
    private FeedBackServiceImpl feedBackServiceImpl;

    @PostMapping("/user/{userId}")
    public ResponseEntity create(@PathVariable("userId") @NotNull @Positive int userId,@Valid @RequestBody FeedBackEntryDTO feedBackEntryDTO){
        FeedBackResponseDto result = feedBackServiceImpl.create(userId,feedBackEntryDTO);
        return new ResponseEntity<>(new ApiResponse("Feedback has been created SuccessFully",true,new ResponseData<>(result)), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.id")
    @GetMapping("/users/{userId}}")
    public ResponseEntity getFeedbacksByUserId(@PathVariable("userId") @NotNull @Positive int userId){
        List<FeedBackResponseDto> result = feedBackServiceImpl.getFeedbacksByUser(userId);
        return new ResponseEntity<>(new ApiResponse("Feedbacks retrieved SuccessFully",true,new ResponseData<>(result)), HttpStatus.OK);

    }

}
