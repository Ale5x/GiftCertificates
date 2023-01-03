package com.epam.esm.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The type Popular tag. The class includes user data and tag data.
 *
 * @author Alexander Pishchala
 */
public class PopularTag implements Serializable {

    private static final long serialVersionUID = 2643965587483798981L;

    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private int tagId;
    private String tagName;
    private int count;

    /**
     * Instantiates a new PopularTag.
     */
    public PopularTag() {
    }

    /**
     * Getting the User id.
     *
     * @return the User id.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Setting the User id.
     *
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Getting the first name.
     *
     * @return the firstName.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setting the first name.
     *
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getting the last name.
     *
     * @return the lastName.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setting the last name.
     *
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getting the email.
     *
     * @return the email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setting the email.
     *
     */
    public void setEmail(String email) {
        this.email = email;
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
    public String getTagName() {
        return tagName;
    }

    /**
     * Setting the name.
     *
     */
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    /**
     * Getting the count of tag.
     *
     * @return the count of tag
     */
    public int getCount() {
        return count;
    }

    /**
     * Setting the count of tag.
     *
     */
    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PopularTag that = (PopularTag) o;

        if (userId != that.userId) return false;
        if (tagId != that.tagId) return false;
        if (count != that.count) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        return tagName != null ? tagName.equals(that.tagName) : that.tagName == null;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + tagId;
        result = 31 * result + (tagName != null ? tagName.hashCode() : 0);
        result = 31 * result + count;
        return result;
    }

    @Override
    public String toString() {
        StringBuilder buildString = new StringBuilder();
        buildString.append("PopularTag{userId=").append(userId)
                .append(", firstName='").append(firstName)
                .append(", lastName='").append(lastName)
                .append(", email='").append(email)
                .append(", tagId=").append(tagId)
                .append(", tagName='").append(tagName)
                .append(", count=").append(count).append('}');
        return buildString.toString();
    }
}
