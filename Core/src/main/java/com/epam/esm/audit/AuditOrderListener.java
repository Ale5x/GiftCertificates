package com.epam.esm.audit;

import com.epam.esm.model.Order;
import com.epam.esm.model.OrderDetails;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

/**
 * Class for Auditing for Order. It fires before communicating with the database and defines the audit of the data.
 * Saving operations to a file.
 *
 * @author Alexander Pishchala
 */
public class AuditOrderListener {

    private final static String REGEX_DELIMITER = " | ";
    private final static String CREATE_OPERATION = "Create";
    private final static String UPDATE_OPERATION = "Update";
    private final static String DELETE_OPERATION = "Remove";

    @Autowired
    private Save save;

    @PrePersist
    public void preCreate(Object object){
        String message = buildMessage(CREATE_OPERATION, object);
        save.save(message);
    }
    @PreUpdate
    public void preUpdate(Object object){
        String message = buildMessage(UPDATE_OPERATION, object);
        save.save(message);
    }

    @PreRemove
    public void preRemove(Object object){
        String message = buildMessage(DELETE_OPERATION, object);
        save.save(message);
    }


    /**
     * Builder the message to save from the object.
     *
     * @param operation Actions with the object. Is part of the message.
     * @param object The entity object is a order. From it a data message is formed to store.
     *
     * @return line of string.
     */
    private String buildMessage(String operation, Object object) {
        StringBuilder builder = new StringBuilder();
        Order order = (Order) object;
        builder.append(operation.toUpperCase())
                .append(REGEX_DELIMITER).append("Order id-").append(order.getOrderId())
                .append(REGEX_DELIMITER).append("cost-").append(order.getCost())
                .append(REGEX_DELIMITER).append("purchaseTime-").append(order.getPurchaseTime())
                .append(REGEX_DELIMITER).append("id_users-").append(order.getUser().getUserId())
                .append(REGEX_DELIMITER).append("OrderDetails: ").append(builderOrderDetailsToString(order));
        return builder.toString();
    }

    /**
     * Builder the messages from Tags for a message.
     *
     * @param order the order entity.
     *
     * @return line of string.
     */
    private String builderOrderDetailsToString(Order order) {
        StringBuilder builderOrderDetails = new StringBuilder();
        int size = 0;
        for (OrderDetails detail : order.getOrderDetails()) {
            size++;
            builderOrderDetails.append("orderId-").append(detail.getOrder().getOrderId())
            .append(" price-").append(detail.getPrice())
            .append(" id_certificates-").append(detail.getCertificate().getGiftCertificateId());
            if (size < order.getOrderDetails().size()) {
                builderOrderDetails.append(", ");
            }
        }
        return builderOrderDetails.toString();
    }
}
