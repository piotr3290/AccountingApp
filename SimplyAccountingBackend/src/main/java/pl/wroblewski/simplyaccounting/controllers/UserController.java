package pl.wroblewski.simplyaccounting.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.wroblewski.simplyaccounting.models.dtos.UserDto;
import pl.wroblewski.simplyaccounting.services.UserService;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/register")
    public ResponseEntity<String> register(@RequestBody @Validated UserDto user) {
        userService.createUser(user);
        return ResponseEntity.ok("User has been successfully created.");
    }


}
