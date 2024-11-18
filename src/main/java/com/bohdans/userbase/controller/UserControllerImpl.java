package com.bohdans.userbase.controller;

import com.bohdans.userbase.api.UsersApi;
import com.bohdans.userbase.model.UserDto;
import com.bohdans.userbase.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserControllerImpl implements UsersApi {

    private final UserService userService;

    @Override
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.fetchUsersInfo());
    }
}
