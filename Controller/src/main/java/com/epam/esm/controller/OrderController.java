package com.epam.esm.controller;

import com.epam.esm.exception.AppRequestException;
import com.epam.esm.exception.ErrorMessage;
import com.epam.esm.model.Dto.UserDto;
import com.epam.esm.security.AuthUserDetailsService;
import com.epam.esm.service.UserService;
import com.epam.esm.util.LocalUtil;
import com.epam.esm.model.Dto.OrderDto;
import com.epam.esm.service.OrderService;
import com.epam.esm.validator.ValidatorParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The type Order Controller is a controller which operates requests from clients and generates response in
 * representational forms. Information exchanging are in a JSON forms.
 *
 * @author Alexander Pishchala
 */
@RestController
@CrossOrigin({"http://localhost:3000", "http://localhost:8080"})
public class OrderController extends PaginationHelper {

    private ValidatorParams validator;
    private OrderService orderService;
    private LocalUtil localUtil;
    private UserService userService;
    private AuthUserDetailsService userDetailsService;

    /**
     * Instantiates a new OrderController.
     *
     * @param orderService the order service
     * @param localUtil the Local.
     * @param validator the validator values.
     */
    @Autowired
    public OrderController(OrderService orderService,
                           LocalUtil localUtil,
                           ValidatorParams validator,
                           UserService userService,
                           AuthUserDetailsService userDetailsService) {
        this.orderService = orderService;
        this.localUtil = localUtil;
        this.validator = validator;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Create a Order.
     *
     * @param order the orderDto entity.
     *
     * @return the HttpStatus.
     */
    @PostMapping(value = PathPageConstant.ORDER_CREATE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> createOrder(@RequestBody OrderDto order) {
        order.setUserId(userDetailsService.getIdUser());
        System.out.println("CREATE ORDER");
        orderService.save(order);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Deleting the order.
     *
     * @param orderId the order id.
     *
     * @return the HttpStatus.
     */
    @DeleteMapping(value = PathPageConstant.ORDER_DELETE)
    public ResponseEntity<HttpStatus> deleteOrder(@RequestParam(ConstantController.ID) String orderId) {
        validator.validId(orderId);
        orderService.delete(Integer.parseInt(orderId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Getting order by id.
     *
     * @param id the order id.
     *
     * @return the order by id.
     */
    @GetMapping(value = PathPageConstant.ORDER_GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public OrderDto getOrder(@RequestParam(ConstantController.ID) String id) {
        validator.validId(id);
        Optional<OrderDto> order = orderService.showOrder(Integer.parseInt(id));
        return order.orElseThrow(() -> new AppRequestException(
                (localUtil.getMessage(ErrorMessage.ORDER_BY_ID_NOT_FOUND)) + id, HttpStatus.NOT_FOUND));
    }

    /**
     * Getting orders.
     *
     * @param page the page being viewed.
     * @param size the number of elements per page.
     *
     * @return the list of orders.
     */
    @GetMapping(value = PathPageConstant.ORDER_GET_ALL)
    public CollectionModel<OrderDto> getOrders(@RequestParam(ConstantController.PAGE) String page,
                                               @RequestParam(ConstantController.SIZE) String size) {
        validator.validPage(page);
        validator.validSize(size);
        Link previousLink = linkTo(methodOn(OrderController.class)
                .getOrders(getPreviousPage(page), size))
                .withRel(ConstantController.PREVIOUS_LINK).withType(ConstantController.METHOD_GET);
        Link nextLink = linkTo(methodOn(OrderController.class)
                .getOrders(getNextPage(page), size))
                .withRel(ConstantController.NEXT_LINK).withType(ConstantController.METHOD_GET);
        List<OrderDto> orderDtoList = orderService.showOrders(Integer.parseInt(size), getOffset(page, size));
        return CollectionModel.of(orderDtoList, previousLink, nextLink);
    }

    /**
     * Getting orders by ID user.
     *
     * @param page the page being viewed.
     * @param size the number of elements per page.
     * @param id the id of user.
     *
     * @return the list of orders.
     */
    @GetMapping(value = PathPageConstant.SECTION_ORDER + "/getOrdersByUser")
    public CollectionModel<OrderDto> getOrdersByUser(@RequestParam(ConstantController.PAGE) String page,
                                                     @RequestParam(ConstantController.SIZE) String size,
                                                     @RequestParam(ConstantController.ID) String id) {
        validator.validPage(page);
        validator.validSize(size);
        validator.validId(id);
        Link previousLink = linkTo(methodOn(OrderController.class)
                .getOrdersByUser(getPreviousPage(page), size, id))
                .withRel(ConstantController.PREVIOUS_LINK).withType(ConstantController.METHOD_GET);
        Link nextLink = linkTo(methodOn(OrderController.class)
                .getOrdersByUser(getNextPage(page), size, id))
                .withRel(ConstantController.NEXT_LINK).withType(ConstantController.METHOD_GET);
        List<OrderDto> orderDtoList = orderService.showByUser(Integer.parseInt(size), getOffset(page, size), Integer.parseInt(id));
        return CollectionModel.of(orderDtoList, previousLink, nextLink);
    }

    /**
     * Getting count orders.
     *
     * @return number of orders.
     */
    @GetMapping(value = PathPageConstant.ORDER_GET_COUNT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Integer> getCountOrders() {
        return new ResponseEntity<>(orderService.showCountOrders(), HttpStatus.OK);
    }

    /**
     * Get current user's orders.
     *
     * @return the list of orders.
     */
    @RequestMapping(value = PathPageConstant.ORDER_USER, method = RequestMethod.GET)
    public CollectionModel<OrderDto> currentUserOrders(@RequestParam(ConstantController.PAGE) String page,
                                                       @RequestParam(ConstantController.SIZE) String size) {
        validator.validPage(page);
        validator.validSize(size);
        UserDto user = userService.showUser(userDetailsService.getIdUser()).orElseThrow(() -> new AppRequestException(
                (localUtil.getMessage(ErrorMessage.ORDER_BY_ID_NOT_FOUND)) + userDetailsService.getIdUser() , HttpStatus.NOT_FOUND));
        Link previousLink = linkTo(methodOn(OrderController.class)
                .currentUserOrders(getPreviousPage(page), size))
                .withRel(ConstantController.PREVIOUS_LINK).withType(ConstantController.METHOD_GET);
        Link nextLink = linkTo(methodOn(OrderController.class)
                .currentUserOrders(getNextPage(page), size))
                .withRel(ConstantController.NEXT_LINK).withType(ConstantController.METHOD_GET);
        List<OrderDto> orderDtoList = orderService.showByUser(Integer.parseInt(size),
                getOffset(page, size), user.getUserId());
        System.out.println("Current user" + orderDtoList.toString());
        return CollectionModel.of(orderDtoList, previousLink, nextLink);
    }
}
