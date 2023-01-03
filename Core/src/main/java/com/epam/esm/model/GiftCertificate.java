package com.epam.esm.model;

import com.epam.esm.audit.AuditCertificateListener;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The type GiftCertificate. The class is mapped to a table from the database.
 *
 * @author Alexander Pishchala
 */
@EntityListeners(AuditCertificateListener.class)
@Entity
@Table(name = "gift_certificates")
public class GiftCertificate implements Serializable{

    private static final long serialVersionUID = 6077365877900075755L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_certificates")
    private int giftCertificateId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "duration")
    private int duration;

    @Column(name = "create_date")
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDateTime createDate;

    @Column(name = "last_update_date")
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDateTime lastUpdateDate;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "tags_gift_certificates",
            joinColumns = { @JoinColumn(name = "id_certificates") },
            inverseJoinColumns = { @JoinColumn(name = "id_tags")})
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "certificate", cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetails = new ArrayList<>();
    
    /**
     * Instantiates a new GiftCertificate.
     */
    public GiftCertificate() {
    }

    /**
     * Getting the Certificate id.
     *
     * @return the Certificate id.
     */
    public int getGiftCertificateId() {
        return giftCertificateId;
    }

    /**
     * Setting the Certificate id.
     *
     */
    public void setGiftCertificateId(int giftCertificateId) {
        this.giftCertificateId = giftCertificateId;
    }

    /**
     * Getting the name.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Setting the name.
     *
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getting the description.
     *
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setting the description.
     *
     */
    public void setDescription(String description) {
        this.description = description;
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
     * Setting price.
     *
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Getting the duration.
     *
     * @return the duration.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Setting the duration.
     *
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Getting the create date.
     *
     * @return the create date.
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Setting the create date.
     *
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Getting the last update date.
     *
     * @return the last update date.
     */
    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * Setting the last update date.
     *
     */
    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    /**
     * Getting the Set of tags.
     *
     * @return the Set of tags.
     */
    public Set<Tag> getTags() {
        return tags;
    }

    /**
     * Setting the Set of tags.
     *
     */
    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    /**
     * Setting the tag.
     *
     */
    public void addTag(Tag tag) {
        tags.add(tag);
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
    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GiftCertificate that = (GiftCertificate) o;

        if (giftCertificateId != that.giftCertificateId) return false;
        if (duration != that.duration) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (lastUpdateDate != null ? !lastUpdateDate.equals(that.lastUpdateDate) : that.lastUpdateDate != null)
            return false;
        if (tags != null ? !tags.equals(that.tags) : that.tags != null) return false;
        return orderDetails != null ? orderDetails.equals(that.orderDetails) : that.orderDetails == null;
    }

    @Override
    public int hashCode() {
        int result = giftCertificateId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + duration;
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + (orderDetails != null ? orderDetails.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder line = new StringBuilder();
        line.append("GiftCertificate{giftCertificateId=").append(giftCertificateId)
                .append( ", name='").append(name)
                .append(", description='").append(description)
                .append(", price=").append(price)
                .append(", duration=").append(duration)
                .append(", createDate=").append(createDate)
                .append(", lastUpdateDate=").append(lastUpdateDate)
                .append(", TagList={").append(tags)
                .append("}, Order='").append(orderDetails).append('}');
        return line.toString();
    }
}

