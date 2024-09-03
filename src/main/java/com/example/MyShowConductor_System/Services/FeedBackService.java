package com.example.MyShowConductor_System.Services;

import com.example.MyShowConductor_System.Entities.FeedBack;
import com.example.MyShowConductor_System.EntryDTOs.FeedBackEntryDTO;
import com.example.MyShowConductor_System.ResponseDTOs.FeedBackResponseDto;

import java.util.List;

public interface FeedBackService {

    public FeedBackResponseDto create(int userId,FeedBackEntryDTO feedBackEntryDTO);

    public List<FeedBackResponseDto> getFeedbacksByUser(int userId);

}
