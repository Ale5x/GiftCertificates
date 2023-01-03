package com.epam.esm.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Tag. The class is mapped to a table from the database.
 *
 * @author Alexander Pishchala
 */
@Entity
@Table(name = "tags", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Tag implements Serializable {

    private static final long serialVersionUID = -3959125310345631428L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tags")
    private int tagId;

    @Column(name = "name", unique = true)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<GiftCertificate> giftCertificates = new ArrayList<>();

    /**
     * Instantiates a new Tag.
     */
    public Tag() {
    }

    /**
     * Instantiates a new Tag.
     *
     * @param name the Tag name.
     */
    public Tag(String name) {
        this.name = name;
    }

    /**
     * Instantiates a new Tag.
     *
     * @param name the Tag name.
     * @param tagId the Tag id.
     */
    public Tag(int tagId, String name) {
        this.tagId = tagId;
        this.name = name;
    }

    /**
     * Getting the Tag id.
     *
     * @return the Tag id
     */
    public int getTagId() {
        return tagId;
    }

    /**
     * Setting the Tag id.
     *
     */
    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    /**
     * Getting the name.
     *
     * @return the name
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
     * Getting the certificates of Tag.
     *
     * @return the certificates of Tag.
     */
    public List<GiftCertificate> getGiftCertificates() {
        return giftCertificates;
    }

    /**
     * Setting the certificates of Tag.
     *
     */
    public void setGiftCertificates(List<GiftCertificate> giftCertificates) {
        this.giftCertificates = giftCertificates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        if (tagId != tag.tagId) return false;
        if (name != null ? !name.equals(tag.name) : tag.name != null) return false;
        return giftCertificates != null ? giftCertificates.equals(tag.giftCertificates) : tag.giftCertificates == null;
    }

    @Override
    public int hashCode() {
        int result = tagId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (giftCertificates != null ? giftCertificates.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder line = new StringBuilder();
        line.append("Tag{tagId=").append(tagId).append(", name='").append(name)
                .append(", GiftCertificates='").append(giftCertificates).append('}');
        return line.toString();
    }
}
