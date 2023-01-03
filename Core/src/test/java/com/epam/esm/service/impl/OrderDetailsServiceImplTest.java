package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDetailsDao;
import com.epam.esm.model.Dto.OrderDetailsDto;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Order;
import com.epam.esm.model.OrderDetails;
import com.epam.esm.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OrderDetailsServiceImplTest {

    @InjectMocks
    private OrderDetailsServiceImpl orderDetailsService;

    @Mock
    private OrderDetailsDao orderDetailsDao;

    private List<OrderDetails> orderDetailsList = new ArrayList<>();
    private Order order = new Order();
    private User user = new User(2, "firstName", "lastName", "email");

    @BeforeEach
    void setUp() {
        order.setOrderId(1);
        order.setUser(user);
       for(int i = 0; i < 3; i++) {
           GiftCertificate certificate = new GiftCertificate();
           certificate.setGiftCertificateId(1 + i);
           certificate.setName("Certificate #" + i);
           certificate.setPrice(BigDecimal.valueOf(15));

           OrderDetails orderDetails = new OrderDetails();
           orderDetails.setOrderDetailsId( i + 1);
           orderDetails.setOrder(order);
           orderDetails.setCertificate(certificate);
           orderDetails.setPrice(certificate.getPrice());

           orderDetailsList.add(orderDetails);
       }
    }

    @Test
    void findDetailsByOrder() {
        given(orderDetailsDao.findDetailsByOrder(order.getOrderId())).willReturn(orderDetailsList);
        List<OrderDetailsDto> orderDetailsDtos = orderDetailsService.findDetailsByOrder(order.getOrderId());

        assertFalse(orderDetailsDtos.isEmpty());
        assertEquals(orderDetailsList.size(), orderDetailsDtos.size());
    }

    @Test
    void findDetailsByOrderAndUser() {
        given(orderDetailsDao.findDetailsByOrder(order.getOrderId())).willReturn(orderDetailsList);
        List<OrderDetailsDto> orderDetailsDtos = orderDetailsService.findDetailsByOrderAndUser(order.getOrderId(), order.getUser().getUserId());

        assertFalse(orderDetailsDtos.isEmpty());
        assertEquals(orderDetailsList.size(), orderDetailsDtos.size());
    }

    @Test
    void findDetailsByOrderAndUserNotAccessUser() {
        int wrongUserId = 5;
        given(orderDetailsDao.findDetailsByOrder(order.getOrderId())).willReturn(orderDetailsList);
        List<OrderDetailsDto> orderDetailsDtos = orderDetailsService.findDetailsByOrderAndUser(order.getOrderId(), wrongUserId);

        assertTrue(orderDetailsDtos.isEmpty());
    }
}
