package com.epam.esm.audit;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

/**
 * Class for Auditing for GiftCertificate. It fires before communicating with the database and defines the audit of the data.
 * Saving operations to a file.
 *
 * @author Alexander Pishchala
 */
public class AuditCertificateListener {

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
     * @param object The entity object is a certificate. From it a data message is formed to store.
     *
     * @return line of string.
     */
    private String buildMessage(String operation, Object object) {
        StringBuilder builder = new StringBuilder();
        GiftCertificate certificate = (GiftCertificate) object;
        builder.append(operation.toUpperCase())
                .append(REGEX_DELIMITER).append("Certificate id-").append(certificate.getGiftCertificateId())
                .append(REGEX_DELIMITER).append("name-").append(certificate.getName())
                .append(REGEX_DELIMITER).append("price-").append(certificate.getPrice())
                .append(REGEX_DELIMITER).append("duration-").append(certificate.getDuration())
                .append(REGEX_DELIMITER).append("createDate-").append(certificate.getCreateDate())
                .append(REGEX_DELIMITER).append("lastUpdateDate-").append(certificate.getLastUpdateDate())
                .append(REGEX_DELIMITER).append("Tags: ").append(builderTagsToString(certificate))
                .append(REGEX_DELIMITER).append("Description-").append(certificate.getDescription());
        return builder.toString();
    }

    /**
     * Builder the messages from Tags for a message.
     *
     * @param certificate the certificate entity.
     *
     * @return line of string.
     */
    private String builderTagsToString(GiftCertificate certificate) {
        StringBuilder builderTags = new StringBuilder();
        int size = 0;
        for (Tag tag : certificate.getTags()) {
            size++;
            builderTags.append(tag.getName());
            if (size < certificate.getTags().size()) {
                builderTags.append(", ");
            }
        }
        return builderTags.toString();
    }
}
