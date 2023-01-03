package com.epam.esm.dao.impl;

import com.epam.esm.model.User;
import com.epam.esm.dao.UserDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * The type User repository implements methods of the UserDao interface.
 * The class is annotated with as a repository, which qualifies it to be automatically created by component-scanning.
 *
 * @author Alexander Pishchala
 */
@Transactional
@Repository
public class UserDaoImpl implements UserDao {

    private SessionFactory sessionFactory;
    private final static char PERCENT_CHAR = '%';

    @Autowired
    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean create(User user) {
        Session session = getSession();
        session.save(user);
        session.flush();
        return user.getUserId() > 0;
    }

    @Override
    public Optional<User> findUser(int id) {
        Session session = getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> rootUser = criteriaQuery.from(User.class);
        criteriaQuery.select(rootUser).where(criteriaBuilder.equal(rootUser.get("userId"), id));

        return session.createQuery(criteriaQuery).getResultList().stream().findFirst();
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        Session session = getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> rootUser = criteriaQuery.from(User.class);
        criteriaQuery.select(rootUser).where(criteriaBuilder.equal(rootUser.get("email"), email));

        return session.createQuery(criteriaQuery).getResultList().stream().findFirst();
    }

    /**
     Session session = getSession();
     CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

     CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
     Root<GiftCertificate> certificateRoot = criteriaQuery.from(GiftCertificate.class);
     SetJoin<GiftCertificate, Tag> certificateTagSetJoin = certificateRoot.joinSet("tags");
     */
    @Override
    public List<User> findUsersByName(String name, int limit, int offset) {
        Session session = getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> rootUser = criteriaQuery.from(User.class);

        Predicate[] predicates = new Predicate[3];
        predicates[0] = criteriaBuilder.like(rootUser.get("email"), createSearchCriteria(name));
        predicates[1] = criteriaBuilder.like(rootUser.get("firstName"), createSearchCriteria(name));
        predicates[2] = criteriaBuilder.like(rootUser.get("lastName"), createSearchCriteria(name));
        criteriaQuery.select(rootUser).where(criteriaBuilder.or(predicates)).distinct(true)
                .orderBy(criteriaBuilder.desc(rootUser.get("userId")));

        return session.createQuery(criteriaQuery).setMaxResults(limit).setFirstResult(offset).getResultList();
    }

    @Override
    public List<User> findUserList(int limit, int offset) {
        Session session = getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        criteriaQuery.select(userRoot);
        return session.createQuery(criteriaQuery).setMaxResults(limit).setFirstResult(offset).getResultList();
    }

    @Override
    public int findCountUsers() {
        Session session = getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(criteriaBuilder.count(root));
        Query<Long> query = session.createQuery(criteriaQuery);
        return  query.getResultList().get(0).intValue();
    }

    @Override
    public boolean delete(User user) {
        Session session = getSession();
        session.delete(user);
        return true;
    }

    /**
     * The method returns the session from SessionFactory.
     *
     * @return current session.
     */
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public boolean update(User user) {
        Session session = getSession();
        session.update(user);
        return true;
    }

    /**
     * The method builds the query criteria. It wraps the word into characters for SQL Query.
     * @param name The keyword.
     * @return The keyword in characters for SQL Query.
     */
    private String createSearchCriteria(String name) {
        StringBuilder criterion = new StringBuilder();
        criterion.append(PERCENT_CHAR).append(name).append(PERCENT_CHAR);
        return criterion.toString();
    }
}
