package com.epam.esm.model;

import com.epam.esm.audit.AuditOrderListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Order. The class is mapped to a table from the database.
 *
 * @author Alexander Pishchala
 */
@Entity
@Table(name = "orders")
@EntityListeners(AuditOrderListener.class)
public class Order implements Serializable {

    private static final long serialVersionUID = -3087949333499670905L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orders")
    private int orderId;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "purchase_time")
    private LocalDateTime purchaseTime;

    @ManyToOne
    @JoinColumn(name = "id_users")
    private User user;

    @OneToMany(cascade=CascadeType.ALL, mappedBy = "order")
    private List<OrderDetails> orderDetails = new ArrayList<>();

    /**
     * Instantiates a new Order.
     */
    public Order() {
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
     * Getting the User.
     *
     * @return the User.
     */
    public User getUser() {
        return user;
    }

    /**
     * Setting the User.
     *
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Getting the list of OrderDetails.
     *
     * @return the list of OrderDetails.
     */
    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    /**
     * Setting the list of OrderDetails.
     *
     */
    public void setSingleOrderDetail(OrderDetails orderDetail) {
        this.orderDetails.add(orderDetail);
        orderDetail.setOrder(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (orderId != order.orderId) return false;
        if (cost != null ? !cost.equals(order.cost) : order.cost != null) return false;
        if (purchaseTime != null ? !purchaseTime.equals(order.purchaseTime) : order.purchaseTime != null) return false;
        if (user != null ? !user.equals(order.user) : order.user != null) return false;
        return orderDetails != null ? orderDetails.equals(order.orderDetails) : order.orderDetails == null;
    }

    @Override
    public int hashCode() {
        int result = orderId;
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        result = 31 * result + (purchaseTime != null ? purchaseTime.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (orderDetails != null ? orderDetails.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder line = new StringBuilder();
        line.append("Order{orderId=").append(orderId)
                .append(", cost=").append(cost)
                .append(", purchaseTime=").append(purchaseTime)
                .append(", user=").append(user)
                .append(", orderDetails=").append(orderDetails).append('}');

        return line.toString();
    }
}
