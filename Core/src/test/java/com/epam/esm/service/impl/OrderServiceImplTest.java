package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.OrderDaoImpl;
import com.epam.esm.model.*;
import com.epam.esm.model.Dto.OrderDetailsDto;
import com.epam.esm.model.Dto.OrderDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderDaoImpl orderDao;

    private OrderDto orderDto = new OrderDto();

    private List<Order> orderList = new ArrayList<>();
    private Order order = new Order();

    private int limit = 10;
    private int offset = 0;

    @BeforeEach
    void setUp() {
        orderDto.setUserId(1);
        List<OrderDetailsDto> orderDetails = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            OrderDetailsDto order = new OrderDetailsDto();

            order.setCertificateId(i + 1);
            order.setPrice(BigDecimal.valueOf(55));

            orderDetails.add(order);
        }

        orderDto.setOrderDetails(orderDetails);

        User user = new User();
        user.setUserId(1);

        order.setUser(user);
        order.setCost(BigDecimal.valueOf(5));

        OrderDetails orderDetail = new OrderDetails();
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        for(int j = 0; j < 5; j++) {
            orderDetail.setOrder(order);
            orderDetail.setPrice(BigDecimal.valueOf(1));
            orderDetail.setCertificate(new GiftCertificate());
            orderDetailsList.add(orderDetail);
        }
        for (int i = 0; i < 5; i++) {
            order.setOrderId(i + 1);
            for (OrderDetails orderDetail1 : orderDetailsList) {
                order.setSingleOrderDetail(orderDetail1);
            }
            orderList.add(order);
        }
    }

    @Test
    void save() {
        when(orderDao.create(any(Order.class))).thenReturn(true);
        boolean condition = orderService.save(orderDto);
        assertTrue(condition);
    }

    @Test
    void deleteRightTest() {
        int id = 1;
        given(orderDao.findOrder(id)).willReturn(Optional.of(order));
        given(orderDao.delete(id)).willReturn(true);
        boolean condition = orderService.delete(id);
        assertTrue(condition);
    }

    @Test
    void showOrderRightTest() {
        int id = 1;
        given(orderDao.findOrder(id)).willReturn(Optional.of(order));
        Optional<OrderDto> order = orderService.showOrder(id);
        assertTrue(order.isPresent());
    }

    @Test
    void showOrderWrongTest() {
        int id = 111111;
        given(orderDao.findOrder(id)).willReturn(Optional.empty());
        Optional<OrderDto> order = orderService.showOrder(id);
        assertTrue(order.isEmpty());
    }

    @Test
    void showOrders() {
        given(orderDao.getOrders(limit, offset)).willReturn(orderList);
        List<OrderDto> orderDtoList = orderService.showOrders(limit, offset);
        assertFalse(orderDtoList.isEmpty());
        assertTrue(orderDtoList.size() > 0);
    }

    @Test
    void showOrdersByUser() {
        int userId = 1;
        given(orderDao.findByUser(limit, offset, userId)).willReturn(orderList);
        List<OrderDto> orderDtoList = orderService.showByUser(limit, offset, userId);
        assertFalse(orderDtoList.isEmpty());
        assertTrue(orderDtoList.size() > 0);
    }

    @Test
    void showCountOrders() {
        given(orderDao.findCountOrders()).willReturn(5);
        int countRows = orderService.showCountOrders();
        assertTrue(countRows > 0);
    }
}