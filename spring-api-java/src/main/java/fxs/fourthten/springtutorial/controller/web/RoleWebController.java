package fxs.fourthten.springtutorial.controller.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/role") @RequiredArgsConstructor
public class RoleWebController {
    @GetMapping("/")
    public String getHome() {
        return "<h1>Welcome</h1>";
    }

    @GetMapping("/users")
    public String getUser() {
        return "<h1>User</h1>";
    }

    @GetMapping("/admins")
    public String getAdmin() {
        return "<h1>Admin</h1>";
    }
}
