package com.example.MyShowConductor_System.Controllers;

import com.example.MyShowConductor_System.EntryDTOs.UserEntryDTO;
import com.example.MyShowConductor_System.Entities.User;
import com.example.MyShowConductor_System.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody UserEntryDTO userEntryDTO){
        try {
            String result = userService.addUser(userEntryDTO);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        catch (Exception e){
            String response = "User not created";
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateLocationByMob")
    public ResponseEntity updateLocation(@RequestParam("mob") String mob,@RequestParam("location") String location){
        String result = userService.updateLocation(mob,location);
        return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
    }
    @GetMapping("/getAll")
    public ResponseEntity getAll(){
        List<User> userList = userService.getALl();
        return new ResponseEntity<>(userList,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity deleteById(@RequestParam("id") int id){
        try {
            String result = userService.deleteById(id);
            return new ResponseEntity<>(result, HttpStatus.RESET_CONTENT);
        }
        catch (RuntimeException e){
            String response = "Movie did not deleted"+e.getMessage();
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }


}
