package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.Dto.OrderDetailsDto;
import com.epam.esm.model.Dto.OrderDto;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Order;
import com.epam.esm.model.OrderDetails;
import com.epam.esm.model.User;
import com.epam.esm.service.OrderDetailsService;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.LocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * The type Order service implements methods of the Order interface.
 * The class is annotated as a service, which qualifies it to be automatically created by component-scanning.
 *
 * @author Alexander Pishchala
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private static final String ISO_FORMAT_DATE = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String ORDER_BY_ID_NOT_FOUND_CODE = "code.order.not_found";
    public static final String ORDER_BY_ID_NOT_FOUND = "Order not found by ID. ID: ";

    private OrderDao orderDao;
    private LocalUtil localUtil;
    private OrderDetailsService orderDetailsService;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, LocalUtil localUtil, OrderDetailsService orderDetailsService) {
        this.orderDao = orderDao;
        this.localUtil = localUtil;
        this.orderDetailsService = orderDetailsService;
    }

    @Override
    public boolean save(OrderDto orderDto) {
        Order order = new Order();
        order.setPurchaseTime(LocalDateTime.parse(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(ISO_FORMAT_DATE))));
        BigDecimal cost = new BigDecimal(0);
        for (OrderDetailsDto detailDto : orderDto.getOrderDetails()) {
            cost = cost.add(detailDto.getPrice());
            order.setSingleOrderDetail(builderOrderDetail(detailDto));
        }
        order.setCost(cost);
        User user = new User();
        user.setUserId(orderDto.getUserId());
        order.setUser(user);

        return orderDao.create(order);
    }

    @Override
    public boolean delete(int orderId) {
        Optional<Order> order = orderDao.findOrder(orderId);
        if (order.isPresent()) {
            return orderDao.delete(orderId);
        } else {
            logger.error(ORDER_BY_ID_NOT_FOUND + orderId);
            throw new ServiceException((localUtil.getMessage(ORDER_BY_ID_NOT_FOUND_CODE)) + orderId);
        }
    }

    @Override
    @Transactional
    public Optional<OrderDto> showOrder(int orderId) {
        Optional<Order> order = orderDao.findOrder(orderId);
        return order.map(value -> builderOrderDto(Collections.singletonList(value)).get(0));
    }

    @Override
    @Transactional
    public List<OrderDto> showOrders(int limit, int offset) {
        return builderOrderDto(orderDao.getOrders(limit, offset));
    }

    @Override
    @Transactional
    public List<OrderDto> showByUser(int limit, int offset, int userId) {
        return builderOrderDto(orderDao.findByUser(limit, offset, userId));
    }

    @Override
    public int showCountOrders() {
        return orderDao.findCountOrders();
    }

    /**
     * Builder list of Order Dto by list of Order entity. This is a helper method needed to avoid
     * code duplication.
     *
     * @param orders the list of orders.
     *
     * @return list of Order Dto.
     */
    private List<OrderDto> builderOrderDto(List<Order> orders){
        List<OrderDto> orderList = new ArrayList<>();

        for (Order order : orders) {
            OrderDto orderDto = new OrderDto();

            orderDto.setOrderId(order.getOrderId());
            orderDto.setUserId(order.getUser().getUserId());
            orderDto.setCost(order.getCost());
            orderDto.setPurchaseTime(order.getPurchaseTime());
            orderDto.setOrderDetails(orderDetailsService.findDetailsByOrder(order.getOrderId()));

            orderList.add(orderDto);
        }
        return orderList;
    }

//    /**
//     * Builder list of OrderDetails Dto by list of OrderDetails entity. This is a helper method needed to avoid
//     * code duplication.
//     *
//     * @param orderDetails the list of orderDetails.
//     *
//     * @return list of OrderDetails Dto.
//     */
//    private List<OrderDetailsDto> builderOrderDetailsDto(List<OrderDetails> orderDetails) {
//        List<OrderDetailsDto> orderDetailsDtoList = new ArrayList<>();
//        for (OrderDetails orderDetail : orderDetails) {
//            OrderDetailsDto orderDetailsDto = new OrderDetailsDto();
//
//            orderDetailsDto.setOrderDetailsId(orderDetail.getOrderDetailsId());
//            orderDetailsDto.setCertificateId(orderDetail.getCertificate().getGiftCertificateId());
//            orderDetailsDto.setOrderId(orderDetail.getOrder().getOrderId());
//            orderDetailsDto.setPrice(orderDetail.getPrice());
//
//            orderDetailsDtoList.add(orderDetailsDto);
//        }
//        return orderDetailsDtoList;
//    }

    private OrderDetails builderOrderDetail(OrderDetailsDto orderDetailsDto) {
        OrderDetails orderDetail = new OrderDetails();
        GiftCertificate certificate = new GiftCertificate();
        certificate.setGiftCertificateId(orderDetailsDto.getCertificateId());
        orderDetail.setCertificate(certificate);
        orderDetail.setPrice(orderDetailsDto.getPrice());

        return orderDetail;
    }
}
