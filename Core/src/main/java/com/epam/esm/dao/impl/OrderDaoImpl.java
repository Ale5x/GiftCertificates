package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.model.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * The type Order repository implements methods of the OrderDao interface.
 * The class is annotated with as a repository, which qualifies it to be automatically created by component-scanning.
 *
 * @author Alexander Pishchala
 */
@Transactional(rollbackFor = Exception.class)
@Repository
public class OrderDaoImpl implements OrderDao {

    private SessionFactory sessionFactory;

    private final static String DELETE_ORDER_QUERY = "DELETE Order WHERE id =: id";
    private final static String GET_ORDER_BY_USER_QUERY = "SELECT orders FROM Order AS orders JOIN " +
            "orders.orderDetails AS order_details WHERE orders.user.userId=: id_users group by orders.orderId";


    @Autowired
    public OrderDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean create(Order order) {
        Session session = getSession();
        session.save(order);
        return order.getOrderId() != 0;
    }

    @Override
    public boolean delete(int orderId) {
        Session session = getSession();
        int rows = session.createQuery(DELETE_ORDER_QUERY).setParameter("id", orderId).executeUpdate();
        return rows > 0;
    }

    @Override
    public Optional<Order> findOrder(int orderId) {
        Session session = getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot).where(criteriaBuilder.equal(orderRoot.get("orderId"), orderId));
        return session.createQuery(criteriaQuery).getResultList().stream().findFirst();
    }

    @Override
    public List<Order> getOrders(int limit, int offset) {
        Session session = getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        return session.createQuery(criteriaQuery).setMaxResults(limit).setFirstResult(offset).getResultList();
    }

    @Override
    public int findCountOrders() {
        Session session = getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(criteriaBuilder.count(root));
        Query<Long> query = session.createQuery(criteriaQuery);
        return  query.getResultList().get(0).intValue();
    }

    @Override
    public List<Order> findByUser(int limit, int offset, int userId) {
        Session session = getSession();

        Query<Order> query = session.createQuery(GET_ORDER_BY_USER_QUERY, Order.class)
                .setParameter("id_users", userId);
        return query.setMaxResults(limit).setFirstResult(offset).getResultList();
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
