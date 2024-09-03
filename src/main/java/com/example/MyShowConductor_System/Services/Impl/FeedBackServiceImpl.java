package com.example.MyShowConductor_System.Services.Impl;

import com.example.MyShowConductor_System.Entities.FeedBack;
import com.example.MyShowConductor_System.Entities.User;
import com.example.MyShowConductor_System.EntryDTOs.FeedBackEntryDTO;
import com.example.MyShowConductor_System.Exceptions.ResourceNotFoundException;
import com.example.MyShowConductor_System.Repositories.FeedBackRepository;
import com.example.MyShowConductor_System.ResponseDTOs.FeedBackResponseDto;
import com.example.MyShowConductor_System.Services.FeedBackService;
import com.example.MyShowConductor_System.Services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedBackServiceImpl implements FeedBackService {

    @Autowired
    private FeedBackRepository feedBackRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public FeedBackResponseDto create(int userId,FeedBackEntryDTO feedBackEntryDTO) {
        User user = userService.getUserEntityById(userId);
        FeedBack feedBack = this.modelMapper.map(feedBackEntryDTO, FeedBack.class);
        feedBack.setUser(user);
        FeedBack savedFeedback = feedBackRepository.save(feedBack);
        return this.modelMapper.map(savedFeedback,FeedBackResponseDto.class);
    }

    @Override
    public List<FeedBackResponseDto> getFeedbacksByUser(int userId) {
        List<FeedBackResponseDto> feedbacks = feedBackRepository.findByUserId(userId).stream().map(feedback -> (this.modelMapper.map(feedback,FeedBackResponseDto.class))).collect(Collectors.toList());
        return feedbacks;
    }
}
