package com.epam.esm.model.Dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Order Dto. The Dto class. It is used to transfer data between layers.
 *
 * @author Alexander Pishchala
 */
public class OrderDto extends RepresentationModel<OrderDto> {

    private int orderId;
    private int userId;
    private BigDecimal cost;

    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDateTime purchaseTime;
    private List<OrderDetailsDto> orderDetails = new ArrayList<>();


    /**
     * Getting the order id.
     *
     * @return the order id.
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * Setting the order id.
     *
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /**
     * Getting the cost.
     *
     * @return the cost.
     */
    public BigDecimal getCost() {
        return cost;
    }

    /**
     * Setting the cost.
     *
     */
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    /**
     * Getting the purchase time.
     *
     * @return the purchase time.
     */
    public LocalDateTime getPurchaseTime() {
        return purchaseTime;
    }

    /**
     * Setting the purchase time.
     *
     */
    public void setPurchaseTime(LocalDateTime purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    /**
     * Getting the user id.
     *
     * @return the user id.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Setting the user id.
     *
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Getting the list of order details.
     *
     * @return the list of order details.
     */
    public List<OrderDetailsDto> getOrderDetails() {
        return orderDetails;
    }

    /**
     * Setting the list of order details.
     *
     */
    public void setOrderDetails(List<OrderDetailsDto> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        OrderDto orderDto = (OrderDto) o;

        if (orderId != orderDto.orderId) return false;
        if (userId != orderDto.userId) return false;
        if (cost != null ? !cost.equals(orderDto.cost) : orderDto.cost != null) return false;
        if (purchaseTime != null ? !purchaseTime.equals(orderDto.purchaseTime) : orderDto.purchaseTime != null)
            return false;
        return orderDetails != null ? orderDetails.equals(orderDto.orderDetails) : orderDto.orderDetails == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + orderId;
        result = 31 * result + userId;
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        result = 31 * result + (purchaseTime != null ? purchaseTime.hashCode() : 0);
        result = 31 * result + (orderDetails != null ? orderDetails.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder buildString = new StringBuilder();
        buildString.append("OrderDto{orderId=").append(orderId)
                .append(", userId=").append(userId)
                .append(", cost=").append(cost)
                .append(", purchaseTime=").append(purchaseTime)
                .append(", orderDetails=").append(orderDetails).append('}');
        return buildString.toString();
    }
}
