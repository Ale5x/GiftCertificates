package com.epam.esm.controller;

import com.epam.esm.model.Dto.OrderDetailsDto;
import com.epam.esm.security.AuthUserDetailsService;
import com.epam.esm.service.OrderDetailsService;
import com.epam.esm.util.LocalUtil;
import com.epam.esm.validator.ValidatorParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin({"http://localhost:3000", "http://localhost:8080"})
public class OrderDetailsController extends PaginationHelper {

    private ValidatorParams validator;
    private OrderDetailsService orderDetailsService;
    private LocalUtil localUtil;
    private AuthUserDetailsService userDetailsService;

    @Autowired
    public OrderDetailsController(ValidatorParams validator,
                                  OrderDetailsService orderDetailsService,
                                  LocalUtil localUtil,
                                  AuthUserDetailsService userDetailsService) {
        this.validator = validator;
        this.orderDetailsService = orderDetailsService;
        this.localUtil = localUtil;
        this.userDetailsService = userDetailsService;
    }


    @GetMapping(value = PathPageConstant.APP_STORE + "/admin/order-details/get")
    public CollectionModel<OrderDetailsDto> getDetailsByOrder(@RequestParam(ConstantController.ID) String orderId) {
        validator.validId(orderId);
        List<OrderDetailsDto> detailsDtoList = orderDetailsService.findDetailsByOrder(Integer.parseInt(orderId));
        return CollectionModel.of(detailsDtoList);
    }

    @GetMapping(value = PathPageConstant.APP_STORE + "/user/order-details/get")
    public CollectionModel<OrderDetailsDto> getUserOrderDetails(@RequestParam(ConstantController.ID) String orderId) {
        validator.validId(orderId);
        List<OrderDetailsDto> detailsDtoList = orderDetailsService.findDetailsByOrderAndUser(Integer.parseInt(orderId), userDetailsService.getIdUser());
        return CollectionModel.of(detailsDtoList);
    }

}
