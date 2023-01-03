package com.epam.esm.model.Dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * The type Gift Certificate Dto. The Dto class. It is used to transfer data between layers.
 *
 * @author Alexander Pishchala
 */
public class GiftCertificateDto extends RepresentationModel<GiftCertificateDto> {

    private int giftCertificateDtoId;
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;

    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDateTime createDate;

    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDateTime lastUpdateDate;
    private Set<TagDto> tags;

    /**
     * Instantiates a new Gift certificate.
     */
    public GiftCertificateDto() {
    }

    /**
     * Instantiates a new Gift certificate.
     *
     * @param giftCertificateDtoId the id certificate
     * @param name the name.
     * @param description the description.
     * @param price the price.
     * @param duration the duration.
     * @param createDate the create date.
     * @param lastUpdateDate the last update date.
     * @param tags the tags.
     */
    public GiftCertificateDto(int giftCertificateDtoId, String name, String description, BigDecimal price,
                              int duration, LocalDateTime createDate, LocalDateTime lastUpdateDate, Set<TagDto> tags) {
        this.giftCertificateDtoId = giftCertificateDtoId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tags = tags;
    }

    /**
     * Instantiates a new Gift certificate.
     *
     * @param name the name.
     * @param description the description.
     * @param price the price.
     * @param duration the duration.
     * @param createDate the create date.
     * @param lastUpdateDate the last update date.
     * @param tags the tags.
     */
    public GiftCertificateDto(String name, String description, BigDecimal price, int duration,
                              LocalDateTime createDate, LocalDateTime lastUpdateDate, Set<TagDto> tags) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tags = tags;
    }

    /**
     * Getting the Certificate id.
     *
     * @return the Certificate id.
     */
    public int getGiftCertificateDtoId() {
        return giftCertificateDtoId;
    }

    /**
     * Setting the Certificate id.
     *
     */
    public void setGiftCertificateDtoId(int giftCertificateDtoId) {
        this.giftCertificateDtoId = giftCertificateDtoId;
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
     * Setting the price.
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
     * Getting the tags.
     *
     * @return the tags.
     */
    public Set<TagDto> getTags() {
        return tags;
    }

    /**
     * Setting the tags.
     *
     */
    public void setTags(Set<TagDto> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        GiftCertificateDto that = (GiftCertificateDto) o;

        if (giftCertificateDtoId != that.giftCertificateDtoId) return false;
        if (duration != that.duration) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (lastUpdateDate != null ? !lastUpdateDate.equals(that.lastUpdateDate) : that.lastUpdateDate != null)
            return false;
        return tags != null ? tags.equals(that.tags) : that.tags == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + giftCertificateDtoId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + duration;
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder buildString = new StringBuilder();
        buildString.append("GiftCertificateDto{giftCertificateDtoId=").append(giftCertificateDtoId)
                .append(", name='").append(name)
                .append(", description='").append(description)
                .append(", price=").append(price)
                .append(", duration=").append(duration)
                .append(", createDate=").append(createDate)
                .append(", lastUpdateDate=").append(lastUpdateDate)
                .append(", tags='").append(tags).append('}');
        return buildString.toString();
    }
}
