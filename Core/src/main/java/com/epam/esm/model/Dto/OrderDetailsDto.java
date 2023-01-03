package com.epam.esm.model.Dto;

import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

/**
 * The type Order Details Dto. The Dto class. It is used to transfer data between layers.
 *
 * @author Alexander Pishchala
 */
public class OrderDetailsDto extends RepresentationModel<OrderDetailsDto> {

    private int orderDetailsId;
    private int orderId;
    private int certificateId;
    private BigDecimal price;
    private String certificateName;

    /**
     * Instantiates a new Order Details.
     */
    public OrderDetailsDto() {
    }

    /**
     * Getting the order details id.
     *
     * @return the order details id.
     */
    public int getOrderDetailsId() {
        return orderDetailsId;
    }

    /**
     * Setting the duration.
     *
     */
    public void setOrderDetailsId(int orderDetailsId) {
        this.orderDetailsId = orderDetailsId;
    }

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
     * Getting the gift certificate id.
     *
     * @return the gift certificate id.
     */
    public int getCertificateId() {
        return certificateId;
    }

    /**
     * Setting the gift certificate id.
     *
     */
    public void setCertificateId(int certificateId) {
        this.certificateId = certificateId;
    }

    /**
     * Getting the price.
     *
     * @return the price.
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Setting the price.
     *
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Getting the certificate name.
     *
     * @return the certificate name.
     */
    public String getCertificateName() {
        return certificateName;
    }

    /**
     * Setting the certificate name.
     *
     */
    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        OrderDetailsDto that = (OrderDetailsDto) o;

        if (orderDetailsId != that.orderDetailsId) return false;
        if (orderId != that.orderId) return false;
        if (certificateId != that.certificateId) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        return certificateName != null ? certificateName.equals(that.certificateName) : that.certificateName == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + orderDetailsId;
        result = 31 * result + orderId;
        result = 31 * result + certificateId;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (certificateName != null ? certificateName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder buildString = new StringBuilder();
        buildString.append("OrderDetailsDto{orderDetailsId=").append(orderDetailsId)
                .append(", orderId=").append(orderId)
                .append(", certificateId=").append(certificateId)
                .append(", certificateName=").append(certificateName)
                .append(", price=").append(price).append('}');
        return buildString.toString();
    }
}
