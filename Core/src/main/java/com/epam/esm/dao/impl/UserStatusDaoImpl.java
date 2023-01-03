package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserStatusDao;
import com.epam.esm.model.EUserStatus;
import com.epam.esm.model.UserStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class UserStatusDaoImpl implements UserStatusDao {

    private SessionFactory sessionFactory;
    private static final String GET_STATUS_QUERY = "SELECT  * from statuses WHERE name=";

    @Autowired
    public UserStatusDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<UserStatus> findStatus(EUserStatus status) {
        Session session = getSession();
        StringBuilder query = new StringBuilder(GET_STATUS_QUERY);
        query.append("'").append(status.name()).append("';");
        List<Object[]> objects = session.createNativeQuery(query.toString())
                .addScalar("id_status", IntegerType.INSTANCE)
                .addScalar("name", StringType.INSTANCE).list();
        UserStatus userStatus = new UserStatus();
        for (Object[] object : objects) {
            userStatus.setStatusId((Integer) object[0]);
            userStatus.setName(EUserStatus.valueOf((String) object[1]));
        }
        if (userStatus.getName() == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(userStatus);
    }

    /**
     * Method for getting the current session.
     *
     * @return current session.
     */
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
