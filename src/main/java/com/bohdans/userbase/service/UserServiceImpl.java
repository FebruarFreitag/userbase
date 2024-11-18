package com.bohdans.userbase.service;

import com.bohdans.userbase.entity.User;
import com.bohdans.userbase.model.UserDto;
import com.bohdans.userbase.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final List<UserRepository> userRepositories;

    @Override
    @Transactional(readOnly = true, transactionManager = "chainedTransactionManager")
    public List<UserDto> fetchUsersInfo(){
        log.debug(String.format("About to return users list from %s repositories", userRepositories.size()));
        return userRepositories.stream()
                .flatMap(r->r.findAll().stream()
                        .map(this::getDto))
                .toList();
    }

    private UserDto getDto(User u) {
        var dto = new UserDto();
        dto.setId(u.getId());
        dto.setUsername(u.getUsername());
        dto.setName(u.getName());
        dto.setSurname(u.getSurname());
        return dto;
    }
}
