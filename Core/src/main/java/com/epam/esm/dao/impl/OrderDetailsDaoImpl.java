package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDetailsDao;
import com.epam.esm.model.OrderDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor = Exception.class)
@Repository
public class OrderDetailsDaoImpl implements OrderDetailsDao {

    private SessionFactory sessionFactory;

    private static final String GET_ORDER_DETAILS_BY_ORDER_QUERY = "SELECT details FROM OrderDetails AS details JOIN " +
            "details.order AS detail WHERE details.order.orderId=: id_orders group by details.orderDetailsId";

    @Autowired
    public OrderDetailsDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<OrderDetails> findDetailsByOrder(int orderId) {
        Session session = getSession();

        Query<OrderDetails> query = session.createQuery(GET_ORDER_DETAILS_BY_ORDER_QUERY, OrderDetails.class)
                .setParameter("id_orders", orderId);

        return query.getResultList();
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
