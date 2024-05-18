package com.example.MyShowConductor_System.Services.Impl;

import com.example.MyShowConductor_System.EntryDTOs.UserEntryDTO;
import com.example.MyShowConductor_System.Entities.User;
import com.example.MyShowConductor_System.Exceptions.ResourceNotFoundException;
import com.example.MyShowConductor_System.Repositories.UserRepository;
import com.example.MyShowConductor_System.ResponseDTOs.UserResponseDTO;
import com.example.MyShowConductor_System.Services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserResponseDTO createUser(UserEntryDTO userEntryDTO){
        User user = this.modelMapper.map(userEntryDTO,User.class);
//        user.setRoles(Role); // only after email verfied
        User savedUser = userRepository.save(user);
        return  this.modelMapper.map(savedUser,UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO updateUser(UserEntryDTO userEntryDTO, Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user","id",Integer.toString(userId)));
        user.setName(userEntryDTO.getName());
        user.setGender(userEntryDTO.getGender());
        user.setAddress(userEntryDTO.getAddress());
        user.setEmail(userEntryDTO.getEmail());
        user.setMobNo(userEntryDTO.getMobNo());
        user.setPassword(userEntryDTO.getPassword());
        User savedUser = userRepository.save(user);
        return this.modelMapper.map(user,UserResponseDTO.class);
    }

    @Override
    public List<UserResponseDTO> getALl() {
        List<User> userList = userRepository.findAll();
        List<UserResponseDTO> users = userList.stream().map(user -> this.modelMapper.map(user,UserResponseDTO.class)).collect(Collectors.toList());
        return users;
    }

    @Override
    public UserResponseDTO getUserById(int id) {
        User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User","id",Integer.toString(id)));
        UserResponseDTO result = this.modelMapper.map(user,UserResponseDTO.class);
        return result;
    }

    @Override
    public String deleteUser(int id) throws RuntimeException {
        userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User","id",Integer.toString(id)));
        userRepository.deleteById(id);
        return "User deleted Successfully";
    }

    @Override
    public boolean verifyUserEmail() {
        return false;
    }

    @Override
    public UserResponseDTO updateUserLocation(String mob, String location) {//here logic will be we'll update location in constant and use that location in apll apis
        User user = userRepository.findByMobNo(mob);
        user.setAddress(location);
        userRepository.save(user);
        UserResponseDTO userResponseDTO = this.modelMapper.map(user,UserResponseDTO.class);
        return userResponseDTO;
    }


//    we'll do this both later
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("user","username",username));//update with username
        return user;
    }

    @Override
    public UserResponseDTO registerUser(UserEntryDTO userDto) {
        User user = this.modelMapper.map(userDto,User.class);
//        user.setUserRole(UserRoleEnum.USER); // this will be only after email verfication
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
        UserResponseDTO savedUser= this.modelMapper.map(user,UserResponseDTO.class);
        return savedUser;
    }
}
