package co.istad.springsecuritybasic.restcontrollers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/admins")
public class AdminRestController {

    @GetMapping
    public String getAllUsers(){
        return "These are users";
    }

    @DeleteMapping("/delete/{id}")
    public String deteleUser(@PathVariable int id){
        return "Delete user "+id+" success";
    }
}
