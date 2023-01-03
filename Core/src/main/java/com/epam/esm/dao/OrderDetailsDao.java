package com.epam.esm.dao;

import com.epam.esm.model.OrderDetails;

import java.util.List;

/**
 * Interface {@link OrderDetailsDao} provides operation with data of database table 'orderDetails'.
 *
 * @author Alexander Pishchala
 */

public interface OrderDetailsDao {

    List<OrderDetails> findDetailsByOrder(int orderId);
}
