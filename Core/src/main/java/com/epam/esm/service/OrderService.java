package com.epam.esm.service;

import com.epam.esm.model.Dto.OrderDto;

import java.util.List;
import java.util.Optional;

/**
 * The interface Order service contains methods for business logic with order.
 *
 * @author Alexander Pishchala
 */
public interface OrderService {

    /**
     * Create a Order.
     *
     * @param orderDto the entity OrderDto.
     *
     * @return returns a boolean result
     */
    boolean save(OrderDto orderDto);

    /**
     * Delete a order.
     *
     * @param orderId the Order id.
     *
     * @return returns a boolean result
     */
    boolean delete(int orderId);

    /**
     * Show Order by id.
     *
     * @param orderId the Order id.
     *
     * @return the specified Optional Order by id.
     */
    Optional<OrderDto> showOrder(int orderId);

    /**
     * The method will return list of Orders.
     *
     * @param limit the number of rows to get at one time.
     * @param offset the value of the element from which the countdown starts.
     *
     * @return the specified list of orders.
     */
    List<OrderDto> showOrders(int limit, int offset);

    List<OrderDto> showByUser(int limit, int offset, int userId);

    /**
     * The method will return the number of rows in the certificate table.
     *
     * @return The number of rows.
     */
    int showCountOrders();
}
