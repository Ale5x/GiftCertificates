package com.epam.esm.dao;

import com.epam.esm.model.EUserStatus;
import com.epam.esm.model.UserStatus;

import java.util.Optional;

public interface UserStatusDao {

    Optional<UserStatus> findStatus(EUserStatus status);
}
