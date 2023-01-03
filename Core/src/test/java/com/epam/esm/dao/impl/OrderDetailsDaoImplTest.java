package com.epam.esm.dao.impl;

import com.epam.esm.configuration.DevelopmentConfig;
import com.epam.esm.dao.OrderDetailsDao;
import com.epam.esm.model.OrderDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DevelopmentConfig.class)
@ActiveProfiles("development")
class OrderDetailsDaoImplTest {

    @Autowired
    OrderDetailsDao orderDetailsDao;

    @Test
    void findDetailsByOrder() {
        int id = 1;
        List<OrderDetails> details = orderDetailsDao.findDetailsByOrder(id);
        assertFalse(details.isEmpty());
        assertTrue(details.size() == 1);
    }

    @Test
    void findDetailsByOrderWrongTest() {
        int id = 5;
        List<OrderDetails> details = orderDetailsDao.findDetailsByOrder(id);
        assertTrue(details.isEmpty());
    }
}