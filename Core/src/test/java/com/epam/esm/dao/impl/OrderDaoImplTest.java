package com.epam.esm.dao.impl;

import com.epam.esm.configuration.DevelopmentConfig;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Order;
import com.epam.esm.model.OrderDetails;
import com.epam.esm.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DevelopmentConfig.class)
@ActiveProfiles("development")
class OrderDaoImplTest {

    private Order order= new Order();

    @Autowired
    private OrderDao orderDao;

    private int limit = 10;
    private int offset = 0;

    @BeforeEach
    void setUp() {
        LocalDateTime date = LocalDateTime.of(2022,1,1,1,1,1);
        User user = new User();
        user.setUserId(1);

        order.setUser(user);
        order.setCost(BigDecimal.valueOf(33));
        order.setPurchaseTime(date);

        GiftCertificate certificate = new GiftCertificate();
        certificate.setGiftCertificateId(1);

        OrderDetails details = new OrderDetails();
        details.setPrice(BigDecimal.valueOf(15555));
        details.setCertificate(certificate);

        order.setSingleOrderDetail(details);

//        List<Tag> tagList = Arrays.asList(new Tag("Tag"));
//        List<GiftCertificateDto> certificateList = Arrays.asList(
//                new GiftCertificateDto(1, "Auto", tagList, "description", BigDecimal.valueOf(3),
//                        11, date, date),
//                new GiftCertificateDto(2, "Auto", tagList, "description", BigDecimal.valueOf(3),
//                        11, date, date));
    }

    @Test
    void create() {
        int startPosition = orderDao.findCountOrders();
        boolean condition = orderDao.create(order);
        int finishPosition = orderDao.findCountOrders();

        assertTrue(condition);
        assertTrue(finishPosition > startPosition);
    }

    @Test
    void delete() {
        int id = 1;
        int startPosition = orderDao.findCountOrders();
        boolean condition = orderDao.delete(id);
        int finishPosition = orderDao.findCountOrders();
        assertTrue(startPosition > finishPosition);
        assertTrue(condition);
    }

//    @Test
//    void getOrder() {
//        int orderId = 1;
//        Optional<Order> order = orderDao.findOrder(orderId);
//        assertTrue(order.isPresent());
//    }

    @Test
    void getOrders() {
        List<Order> orderList = orderDao.getOrders(limit, offset);
        assertFalse(orderList.isEmpty());
        assertTrue(orderList.size() > 0);
    }

    @Test
    void getOrdersByUser() {
        int userId = 1;
        List<Order> orderList = orderDao.findByUser(limit, offset, userId);
        assertFalse(orderList.isEmpty());
        assertTrue(orderList.size() > 0);
    }

    @Test
    void getCountOrders() {
        int count = orderDao.findCountOrders();
        assertTrue(count > 0);
    }
}