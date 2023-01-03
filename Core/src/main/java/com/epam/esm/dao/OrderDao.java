package com.epam.esm.dao;

import com.epam.esm.model.Order;

import java.util.List;
import java.util.Optional;

/**
 * Interface {@link OrderDao} provides operation with data of database table 'orders'.
 *
 * @author Alexander Pishchala
 */
public interface OrderDao {

    /**
     * The method creates new record in database table.
     *
     * @param order entity that specifies creation of new records in database table.
     *
     * @return Returns true if the operation was successful.
     */
    boolean create(Order order);

    /**
     * The method deletes specified record in database table.
     *
     * @param orderId that will be deleted in database table.
     *
     * @return Returns true if the operation was successful.
     */
    boolean delete(int orderId);

    /**
     * The method returns specified Order by id.
     *
     * @param orderId the order id.
     *
     * @return specified Optional Order by id.
     */
    Optional<Order> findOrder(int orderId);

    /**
     * The method will return list of Orders.
     *
     * @param limit the number of rows to get at one time.
     * @param offset the value of the element from which the countdown starts.
     *
     * @return the specified list of orders.
     */
    List<Order> getOrders(int limit, int offset);

    /**
     * The method will return the number of rows in the orders table.
     *
     * @return The number of rows.
     */
    int findCountOrders();

    /**
     * The method will return the user's order list.
     *
     * @param limit the number of rows to get at one time.
     * @param offset the value of the element from which the countdown starts.
     * @param userId the User id.
     * @return the specified list of orders.
     */
    List<Order> findByUser(int limit, int offset, int userId);
}
