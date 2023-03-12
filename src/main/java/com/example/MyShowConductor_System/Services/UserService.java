package com.example.MyShowConductor_System.Services;

import com.example.MyShowConductor_System.Converters.UserConvertors;
import com.example.MyShowConductor_System.EntryDTOs.UserEntryDTO;
import com.example.MyShowConductor_System.Entities.User;
import com.example.MyShowConductor_System.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public String addUser(UserEntryDTO userEntryDTO){
        User user = UserConvertors.EntryToEntity(userEntryDTO);
        userRepository.save(user);
        return  "User Created Successfully";
    }

    public String deleteById(int id) throws RuntimeException {
        userRepository.deleteById(id);
        return "Movie deleted Successfully";
    }

    public String updateLocation(String mob, String location) {
        User user = userRepository.findByMobNo(mob);
        user.setAddress(location);
        userRepository.save(user);
        return "User Location updated SuccessFully";
    }

    public List<User> getALl() {
        List<User> userList = userRepository.findAll();
        return userList;
    }
}
