package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.model.PopularTag;
import com.epam.esm.model.Tag;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * The type Tag repository implements methods of the TagDao interface.
 * The class is annotated with as a repository, which qualifies it to be automatically created by component-scanning.
 *
 * @author Alexander Pishchala
 */

@Transactional
@Repository
public class TagDaoImpl implements TagDao {

    private SessionFactory sessionFactory;

    private final static String DELETE_TAG_QUERY = "DELETE Tag WHERE id =: id";

    private final static String GET_ALL_TAG_BY_NAME_QUERY = "from Tag WHERE name LIKE :name";

    private final static String GET_POPULAR_TAG_WITH_MAX_COST_ORDERS_QUERY = "select users.id_users, users.first_name, " +
            "users.last_name, users.email, tags.id_tags, tags.name, count(tags.name) as counts from orders " +
            "join order_details on orders.id_orders=order_details.id_orders join gift_certificates " +
            "on order_details.id_certificates=gift_certificates.id_certificates join tags_gift_certificates " +
            "on gift_certificates.id_certificates=tags_gift_certificates.id_certificates join tags " +
            "on tags_gift_certificates.id_tags=tags.id_tags join users on orders.id_users=users.id_users " +
            "where orders.id_users=(select id_users from orders group by id_users order by sum(cost) desc limit 1) " +
            "group by tags.name order by count(tags.name) desc limit 1";

    private static int COUNT_ROWS_SUCCESSFUL_OPERATION = 0;

    @Autowired
    public TagDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean create(Tag tag) {
        Session session = getSession();
        session.save(tag);
        return tag.getTagId() != COUNT_ROWS_SUCCESSFUL_OPERATION;
    }

    @Override
    public Optional<Tag> findTag(int tagId) {
        Session session = getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> tagRoot = criteriaQuery.from(Tag.class);
        criteriaQuery.select(tagRoot).where(criteriaBuilder.equal(tagRoot.get("tagId"), tagId));

        return session.createQuery(criteriaQuery).getResultList().stream().findFirst();
    }

    @Override
    public boolean delete(int tagId) {
        Session session = getSession();
        int rows = session.createQuery(DELETE_TAG_QUERY).setParameter("id", tagId).executeUpdate();
        return rows != 0;
    }

    @Override
    public List<Tag> findAllTags(int limit, int offset) {
        Session session = getSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Tag> query = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> tagRoot = query.from(Tag.class);
        query.select(tagRoot);

        return session.createQuery(query).setMaxResults(limit).setFirstResult(offset).getResultList();
    }

    @Override
    public List<Tag> findByPartName(int limit, int offset, String name) {
        Session session = getSession();
        Query<Tag> query = session.createQuery(GET_ALL_TAG_BY_NAME_QUERY).setParameter("name", "%" + name + "%");

        return query.setMaxResults(limit).setFirstResult(offset).getResultList();
    }

    @Override
    public Optional<Tag> findByName(String name) {
        Session session = getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("name"), name));

        return session.createQuery(criteriaQuery).getResultList().stream().findFirst();
    }

    @Override
    public Optional<PopularTag> findPopularTagByMaxCostOrdersUser() {
        Session session = getSession();
        List<Object[]> objects = session.createNativeQuery(GET_POPULAR_TAG_WITH_MAX_COST_ORDERS_QUERY)
                .addScalar("id_users", IntegerType.INSTANCE)
                .addScalar("first_name", StringType.INSTANCE)
                .addScalar("last_name", StringType.INSTANCE)
                .addScalar("email", StringType.INSTANCE)
                .addScalar("id_tags", IntegerType.INSTANCE)
                .addScalar("name", StringType.INSTANCE)
                .addScalar("counts", IntegerType.INSTANCE)
                .list();
        return mapPopularTag(objects);
    }

    @Override
    public int findCountTags() {
        Session session = getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);
        criteriaQuery.select(criteriaBuilder.count(root));
        Query<Long> query = session.createQuery(criteriaQuery);
        return  query.getResultList().get(0).intValue();
    }

    /**
     * The method returns the session from SessionFactory.
     *
     * @return current session.
     */
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    private Optional<PopularTag> mapPopularTag(List<Object[]> objects) {
        PopularTag popularTag = new PopularTag();
        for (Object[] object : objects) {
            popularTag.setUserId((Integer) object[0]);
            popularTag.setFirstName((String) object[1]);
            popularTag.setLastName((String) object[2]);
            popularTag.setEmail((String) object[3]);
            popularTag.setTagId((Integer) object[4]);
            popularTag.setTagName((String) object[5]);
            popularTag.setCount((Integer) object[6]);
        }
        if (popularTag == null) {
            return Optional.empty();
        }
        return Optional.of(popularTag);
    }
}
