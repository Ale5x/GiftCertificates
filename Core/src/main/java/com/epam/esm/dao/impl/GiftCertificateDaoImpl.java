package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

/**
 * The type GiftCertificate repository implements methods of the GiftCertificateDao interface.
 * The class is annotated with as a repository, which qualifies it to be automatically created by component-scanning.
 *
 * @author Alexander Pishchala
 */
@Transactional
@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private SessionFactory sessionFactory;

    private final static String DELETE_CERTIFICATE_QUERY = "DELETE GiftCertificate WHERE id =: id";

    private final static String GET_CERTIFICATES_BY_TAG_NAME_QUERY = "SELECT certificates FROM Tag AS tags " +
            "JOIN tags.giftCertificates AS certificates WHERE tags.name = :tag_name";

    private final static String GET_BY_ORDER_QUERY = "SELECT certificates FROM OrderDetails AS orders JOIN " +
            "orders.certificate AS certificates WHERE orders.order.orderId=: id_orders";

    private final static char PERCENT_CHAR = '%';

    @Autowired
    public GiftCertificateDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean create(GiftCertificate giftCertificate) {
        Session session = getSession();
        session.save(giftCertificate);
        return giftCertificate.getGiftCertificateId() != 0;
    }

    @Override
    public boolean update(GiftCertificate giftCertificate) {
        Session session = getSession();
        session.saveOrUpdate(giftCertificate);
        return true;
    }

    @Override
    public boolean delete(int id) {
        Session session = getSession();
        return session.createQuery(DELETE_CERTIFICATE_QUERY).setParameter("id", id).executeUpdate() != 0;
    }

    @Override
    public Optional<GiftCertificate> findGiftCertificate(int id) {
        Session session = getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = criteriaQuery.from(GiftCertificate.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("giftCertificateId"), id));
//        GiftCertificate certificate = session.get(GiftCertificate.class, id);

        return session.createQuery(criteriaQuery).getResultList().stream().findFirst();
    }

    @Override
    public List<GiftCertificate> findGiftCertificates(int limit, int offset) {
        Session session = getSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> certificateRoot = criteriaQuery.from(GiftCertificate.class);

        criteriaQuery.select(certificateRoot).orderBy(criteriaBuilder.desc(certificateRoot.get("giftCertificateId")));

        return session.createQuery(criteriaQuery).setMaxResults(limit).setFirstResult(offset).getResultList();
    }


    @Override
    public List<GiftCertificate> findByPartName(int limit, int offset, String name) {
        Session session = getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> certificateRoot = criteriaQuery.from(GiftCertificate.class);
        SetJoin<GiftCertificate, Tag> certificateTagSetJoin = certificateRoot.joinSet("tags");

        Predicate [] predicates  = new Predicate[3];
        predicates[0] = criteriaBuilder.like(certificateRoot.get("name"), createSearchCriteria(name));
        predicates[1]= criteriaBuilder.like(certificateRoot.get("description"), createSearchCriteria(name));
        predicates[2]= criteriaBuilder.like(certificateTagSetJoin.get("name"), createSearchCriteria(name));
        criteriaQuery.select(certificateRoot).where(criteriaBuilder.or(predicates)).distinct(true)
                .orderBy(criteriaBuilder.desc(certificateRoot.get("giftCertificateId")));

        Query<GiftCertificate> query = session.createQuery(criteriaQuery).setMaxResults(limit).setFirstResult(offset);
        return query.getResultList();
    }

    @Override
    public List<GiftCertificate> findByTagName(int limit, int offset, String tagName) {
        Session session = getSession();

        Query<GiftCertificate> query = session.createQuery(GET_CERTIFICATES_BY_TAG_NAME_QUERY, GiftCertificate.class)
                .setParameter("tag_name", tagName);
        return query.setMaxResults(limit).setFirstResult(offset).getResultList();
    }

    @Override
    public List<GiftCertificate> findAllBySortName(int limit, int offset) {
        Session session = getSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> query = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> certificateRoot = query.from(GiftCertificate.class);
        query.select(certificateRoot).orderBy(criteriaBuilder.asc(certificateRoot.get("name")));

        return session.createQuery(query).setMaxResults(limit).setFirstResult(offset).getResultList();
    }

    @Override
    public List<GiftCertificate> findAllBySortNameReverse(int limit, int offset) {
        Session session = getSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> query = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> certificateRoot = query.from(GiftCertificate.class);
        query.select(certificateRoot).orderBy(criteriaBuilder.desc(certificateRoot.get("name")));

        return session.createQuery(query).setMaxResults(limit).setFirstResult(offset).getResultList();
    }

    @Override
    public List<GiftCertificate> findByDate(int limit, int offset) {
        Session session = getSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> query = criteriaBuilder.createQuery(GiftCertificate.class);

        Root<GiftCertificate> certificateRoot = query.from(GiftCertificate.class);
        query.select(certificateRoot).orderBy(criteriaBuilder.asc(certificateRoot.get("createDate")));

        return session.createQuery(query).setMaxResults(limit).setFirstResult(offset).getResultList();
    }

    @Override
    public List<GiftCertificate> findByDateReverse(int limit, int offset) {
        Session session = getSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> query = criteriaBuilder.createQuery(GiftCertificate.class);

        Root<GiftCertificate> certificateRoot = query.from(GiftCertificate.class);
        query.select(certificateRoot).orderBy(criteriaBuilder.desc(certificateRoot.get("createDate")));

        return session.createQuery(query).setMaxResults(limit).setFirstResult(offset).getResultList();
    }

    @Override
    public List<GiftCertificate> findByOrder(int limit, int offset, int orderId) {
        Session session = getSession();

        Query<GiftCertificate> query = session.createQuery(GET_BY_ORDER_QUERY, GiftCertificate.class)
                .setParameter("id_orders", orderId);
        return query.setMaxResults(limit).setFirstResult(offset).getResultList();
    }

    @Override
    public int findCountCertificates() {
        Session session = getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<GiftCertificate> certificateRoot = criteriaQuery.from(GiftCertificate.class);
        criteriaQuery.select(criteriaBuilder.count(certificateRoot));
        Query<Long> query = session.createQuery(criteriaQuery);
        return query.getResultList().get(0).intValue();
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

    /**
     * The method returns the session from SessionFactory.
     *
     * @return current session.
     */
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
