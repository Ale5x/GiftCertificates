package com.epam.esm.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The type OrderDetails. The class is mapped to a table from the database.
 *
 * @author Alexander Pishchala
 */
@Entity
@Table(name = "order_details")
public class OrderDetails implements Serializable {

    private static final long serialVersionUID = -258854588013226063L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int orderDetailsId;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "id_orders")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "id_certificates")
    private GiftCertificate certificate;

    /**
     * Instantiates a new OrderDetails.
     */
    public OrderDetails() {
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
     * Getting the Order.
     *
     * @return the Order.
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Setting the Order.
     *
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * Getting the GiftCertificate.
     *
     * @return the GiftCertificate.
     */
    public GiftCertificate getCertificate() {
        return certificate;
    }

    /**
     * Setting the GiftCertificate.
     *
     */
    public void setCertificate(GiftCertificate certificate) {
        this.certificate = certificate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderDetails that = (OrderDetails) o;

        if (orderDetailsId != that.orderDetailsId) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (order != null ? !order.equals(that.order) : that.order != null) return false;
        return certificate != null ? certificate.equals(that.certificate) : that.certificate == null;
    }

    @Override
    public int hashCode() {
        int result = orderDetailsId;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + (certificate != null ? certificate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder line = new StringBuilder();
        line.append("OrderDetails{orderDetailsId=").append(orderDetailsId)
                .append(", price=").append(price)
                .append(", order=").append(order)
                .append(", certificate=").append(certificate).append('}');
        return  line.toString();
    }
}
