package com.example.MyShowConductor_System.Services;

import com.example.MyShowConductor_System.Converters.UserConvertors;
import com.example.MyShowConductor_System.EntryDTOs.UserEntryDTO;
import com.example.MyShowConductor_System.Entities.User;
import com.example.MyShowConductor_System.Enums.UserRoleEnum;
import com.example.MyShowConductor_System.Repositories.UserRepository;
import com.example.MyShowConductor_System.ResponseDTOs.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }

    public UserResponseDTO createUser(User user) {
//        User user1 = UserConvertors.EntryToEntity(user);
        user.setUserRole(UserRoleEnum.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        UserResponseDTO user2= UserConvertors.EntityToResponse(user);
        return user2;
    }
}
