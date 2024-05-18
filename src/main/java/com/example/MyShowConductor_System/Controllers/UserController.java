package com.example.MyShowConductor_System.Controllers;

import com.example.MyShowConductor_System.EntryDTOs.UserEntryDTO;
import com.example.MyShowConductor_System.Payloads.ApiResponse;
import com.example.MyShowConductor_System.Payloads.ResponseData;
import com.example.MyShowConductor_System.ResponseDTOs.UserResponseDTO;
import com.example.MyShowConductor_System.Services.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;
//https://www.google.com/search?q=how+to+use+email+verification+in+spring+boot
@RestController
@Validated
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserServiceImpl userServiceImpl;

//    @PreAuthorize("hasRole('ADMIN')") // is only for admin

    @PostMapping("/")
    public ResponseEntity addUser(@Valid @RequestBody UserEntryDTO userEntryDTO){
        UserResponseDTO result = userServiceImpl.createUser(userEntryDTO);
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        UserEntryDTO u =(UserEntryDTO) auth.getPrincipal();
        return new ResponseEntity<>(new ApiResponse("user has been created",true,new ResponseData<>(result)), HttpStatus.CREATED);

//        catch (Exception e){
//            String response = "User not created";
//            return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
//        }
    }
    @PutMapping("/")
    public ResponseEntity updateUser(@Valid @RequestBody UserEntryDTO userEntryDTO, @RequestParam(value = "userId") Integer userId){
        UserResponseDTO user = userServiceImpl.updateUser(userEntryDTO, userId);
        return new ResponseEntity<>(new ApiResponse("user updated successfully",true,new ResponseData<>(user)),HttpStatus.OK);
    }



    @PutMapping("/location-by-mob")
    public ResponseEntity updateLocation(@RequestParam("mob") @NotBlank String mob, @RequestParam("location") @NotBlank String location){
        UserResponseDTO user = userServiceImpl.updateUserLocation(mob,location);
        return new ResponseEntity<>(new ApiResponse("location updated to :"+location,true,new ResponseData<>(user)),HttpStatus.OK);
    }

    @PutMapping("/verify_email")
    public ResponseEntity verifyEmail(@RequestParam("email") String email){
// https://www.google.com/search?q=how+to+use+email+verification+in+spring+boot
        return new ResponseEntity<>("",HttpStatus.ACCEPTED);
    }
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    public ResponseEntity getAll(){
        List<UserResponseDTO> userList = userServiceImpl.getALl();
        return new ResponseEntity<>(new ApiResponse("all users retrieved",true,new ResponseData<>(userList)),HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity getById(@PathVariable("userId") @NotNull @Positive int id){

        UserResponseDTO user = userServiceImpl.getUserById(id);
        return new ResponseEntity<>(new ApiResponse("user retrieved",true,new ResponseData<>(user)), HttpStatus.OK);

    }
    @GetMapping("/by-email")
    public ResponseEntity getByEmail(@RequestParam("email") String email){ // specifically for admin so its ok to reveal email
//        https://stackoverflow.com/questions/56914237/is-it-safe-to-expose-the-userid-to-a-client
        UserDetails user = userServiceImpl.loadUserByUsername(email);
        return new ResponseEntity<>(new ApiResponse("all users retrieved",true,new ResponseData<>(user)),HttpStatus.OK);
    }

//    @PreAuthorize("hasRole('ADMIN')")
//https://chat.openai.com/c/bd317035-77c2-4886-9017-e5b04fe073c4
//@PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.id")
//@GetMapping("/user")
//public ResponseEntity<?> getUserById(@RequestParam("id") long userId) {
//    User user = userService.findById(userId);
//    if (user == null) {
//        return ResponseEntity.notFound().build();
//    }
//    return ResponseEntity.ok(user);
//}
    @DeleteMapping("/{userId}")
    public ResponseEntity deleteById(@RequestParam("id") @NotNull @Positive int userId){

            String result = userServiceImpl.deleteUser(userId);
            return new ResponseEntity<>(new ApiResponse(result,true), HttpStatus.OK);

    }


}
