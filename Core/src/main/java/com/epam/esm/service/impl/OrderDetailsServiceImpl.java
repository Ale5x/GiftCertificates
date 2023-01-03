package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDetailsDao;
import com.epam.esm.model.Dto.OrderDetailsDto;
import com.epam.esm.model.OrderDetails;
import com.epam.esm.service.OrderDetailsService;
import com.epam.esm.util.LocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    private OrderDetailsDao orderDetails;
    private LocalUtil localUtil;

    @Autowired
    public OrderDetailsServiceImpl(OrderDetailsDao orderDetails, LocalUtil localUtil) {
        this.orderDetails = orderDetails;
        this.localUtil = localUtil;
    }

    @Transactional
    @Override
    public List<OrderDetailsDto> findDetailsByOrder(int orderId) {
        List<OrderDetails> orderDetailsList = orderDetails.findDetailsByOrder(orderId);
        return builderOrderDetailsDto(orderDetailsList);
    }

    @Transactional
    @Override
    public List<OrderDetailsDto> findDetailsByOrderAndUser(int orderId, int userId) {
        List<OrderDetails> orderDetailsList = orderDetails.findDetailsByOrder(orderId);
        if(!orderDetailsList.isEmpty()) {
            if(orderDetailsList.get(0).getOrder().getUser().getUserId() == userId) {
                return builderOrderDetailsDto(orderDetailsList);
            }
        }
        return new ArrayList<>();
    }

    private List<OrderDetailsDto> builderOrderDetailsDto (List<OrderDetails> orderDetails) {
        List<OrderDetailsDto> orderDetailsDtoList = new ArrayList<>();
        for (OrderDetails orderDetail : orderDetails) {
            OrderDetailsDto orderDetailsDto = new OrderDetailsDto();

            orderDetailsDto.setOrderDetailsId(orderDetail.getOrderDetailsId());
            orderDetailsDto.setCertificateId(orderDetail.getCertificate().getGiftCertificateId());
            orderDetailsDto.setCertificateName(orderDetail.getCertificate().getName());
            orderDetailsDto.setOrderId(orderDetail.getOrder().getOrderId());
            orderDetailsDto.setPrice(orderDetail.getPrice());

            orderDetailsDtoList.add(orderDetailsDto);
        }
        return orderDetailsDtoList;
    }
}
