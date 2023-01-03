package com.epam.esm.service.impl;

import com.epam.esm.dao.UserStatusDao;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.Dto.UserStatusDto;
import com.epam.esm.model.EUserStatus;
import com.epam.esm.model.UserStatus;
import com.epam.esm.service.UserStatusService;
import com.epam.esm.util.LocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserStatusServiceImpl implements UserStatusService {

    private static final Logger logger = LoggerFactory.getLogger(UserStatusServiceImpl.class);

    private UserStatusDao userStatusDao;
    private LocalUtil localUtil;

    private static final String STATUS_NOT_FOUND_CODE = "code.status.not_found";
    private static final String STATUS_NOT_FOUND_IN_DATABASE = "The status is not in the database. Status: ";

    @Autowired
    public UserStatusServiceImpl(UserStatusDao userStatusDao, LocalUtil localUtil) {
        this.userStatusDao = userStatusDao;
        this.localUtil = localUtil;
    }

    @Override
    public UserStatus findStatus(String status) {
        Optional<UserStatus> userStatus = userStatusDao.findStatus(checkStatus(status));
        if(userStatus.isEmpty()) {
            logger.error(STATUS_NOT_FOUND_IN_DATABASE + status);
        }
        return userStatus.orElseThrow(() -> new ServiceException(localUtil.getMessage(STATUS_NOT_FOUND_CODE) + status));
    }

    private EUserStatus checkStatus(String status) {
        if(!(status.equalsIgnoreCase(EUserStatus.ACTIVE.name())) && !(status.equalsIgnoreCase(EUserStatus.BLOCKED.name()))) {
            logger.error(STATUS_NOT_FOUND_IN_DATABASE + status);
            throw new ServiceException((localUtil.getMessage(STATUS_NOT_FOUND_CODE) + status));
        }
        return EUserStatus.valueOf(status.toUpperCase());
    }
}
