package com.bohdans.userbase.service;


import com.bohdans.userbase.model.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> fetchUsersInfo();
}
