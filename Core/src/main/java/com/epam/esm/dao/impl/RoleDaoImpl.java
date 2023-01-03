package com.epam.esm.dao.impl;

import com.epam.esm.dao.RoleDao;
import com.epam.esm.model.ERole;
import com.epam.esm.model.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * The type Role repository implements methods of the RoleDao interface.
 * The class is annotated with as a repository, which qualifies it to be automatically created by component-scanning.
 *
 * @author Alexander Pishchala
 */
@Repository
@Transactional
public class RoleDaoImpl implements RoleDao {

    private SessionFactory sessionFactory;

    private static final String GET_ROLE_QUERY = "SELECT  * from roles WHERE name=";

    @Autowired
    public RoleDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Role> findRole(ERole eRole) {
        Session session = getSession();
        StringBuilder query = new StringBuilder(GET_ROLE_QUERY);
        query.append("'").append(eRole.name()).append("';");
        List<Object[]> objects = session.createNativeQuery(query.toString())
                .addScalar("id_roles", IntegerType.INSTANCE)
                .addScalar("name", StringType.INSTANCE).list();
        Role role = new Role();
        for (Object[] object : objects) {
            role.setRoleId((Integer) object[0]);
            role.setName(ERole.valueOf((String) object[1]));
        }
        if (role.getName() == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(role);
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
