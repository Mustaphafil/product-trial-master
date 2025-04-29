package com.product.trial.master.service;

import com.product.trial.master.dto.UserDto;
import com.product.trial.master.entities.User;

public interface AccountService {
    User addUser(UserDto appUserDto);
    User findUserByEmail(String email);
}
