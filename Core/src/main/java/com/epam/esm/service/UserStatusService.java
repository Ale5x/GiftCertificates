package com.epam.esm.service;

import com.epam.esm.model.Dto.UserStatusDto;
import com.epam.esm.model.UserStatus;

public interface UserStatusService {

    UserStatus findStatus(String status);
}
