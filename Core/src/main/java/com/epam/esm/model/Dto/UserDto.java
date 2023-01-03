package com.epam.esm.model.Dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

/**
 * The type User Dto. The Dto class. It is used to transfer data between layers.
 *
 * @author Alexander Pishchala
 */
public class UserDto extends RepresentationModel<UserDto> {

    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private Collection<String> roles = new ArrayList<>();
    private String password;
    private String status;

    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDateTime createDate;

    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDateTime lastUpdateDate;

    /**
     * Instantiates a new User.
     */
    public UserDto() {
    }

    /**
     * Instantiates a new User.
     *
     * @param firstName the User first name.
     * @param lastName the User last name.
     * @param email the User email.
     */
    public UserDto(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
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
     * Getting roles.
     *
     * @return roles.
     */
    public Collection<String> getRoles() {
        return roles;
    }

    /**
     * Setting roles.
     *
     */
    public void setRoles(Collection<String> roles) {
        this.roles = roles;
    }

    /**
     * Getting the password.
     *
     * @return the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setting the password.
     *
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getting the status.
     *
     * @return the status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setting the status.
     *
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UserDto userDto = (UserDto) o;

        if (userId != userDto.userId) return false;
        if (firstName != null ? !firstName.equals(userDto.firstName) : userDto.firstName != null) return false;
        if (lastName != null ? !lastName.equals(userDto.lastName) : userDto.lastName != null) return false;
        if (email != null ? !email.equals(userDto.email) : userDto.email != null) return false;
        if (roles != null ? !roles.equals(userDto.roles) : userDto.roles != null) return false;
        if (password != null ? !password.equals(userDto.password) : userDto.password != null) return false;
        if (status != null ? !status.equals(userDto.status) : userDto.status != null) return false;
        if (createDate != null ? !createDate.equals(userDto.createDate) : userDto.createDate != null) return false;
        return lastUpdateDate != null ? lastUpdateDate.equals(userDto.lastUpdateDate) : userDto.lastUpdateDate == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + userId;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder buildString = new StringBuilder();
        buildString.append("UserDto{userId=").append(userId)
                .append(", firstName='").append(firstName)
                .append(", lastName='").append(lastName)
                .append(", email='").append(email)
                .append(", password='").append(password)
                .append(", status='").append(status)
                .append(", createDate=").append(createDate)
                .append(", updateDate=").append(lastUpdateDate)
                .append(", roles='").append(roles).append('}');
        return buildString.toString();
    }
}
