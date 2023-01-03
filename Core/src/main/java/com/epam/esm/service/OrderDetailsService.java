package com.epam.esm.service;

import com.epam.esm.model.Dto.OrderDetailsDto;

import java.util.List;

public interface OrderDetailsService {

    List<OrderDetailsDto> findDetailsByOrder(int orderId);

    List<OrderDetailsDto> findDetailsByOrderAndUser(int orderId, int userId);
}
