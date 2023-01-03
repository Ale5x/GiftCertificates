package com.epam.esm.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * The type User. The class is mapped to a table from the database.
 *
 * @author Alexander Pishchala
 */
@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = -9188389373515799688L;

    @Column(name = "id_users")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name="id_status")
    private UserStatus status;

    @Column(name = "create_date")
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDateTime createDate;

    @Column(name = "update_date")
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDateTime updateDate;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = { @JoinColumn(name = "id_users") },
            inverseJoinColumns = { @JoinColumn(name = "id_roles")})
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    /**
     * Instantiates a new User.
     */
    public User() {
    }

    /**
     * Instantiates a new User.
     *
     * @param firstName the User first name.
     * @param lastName the User last name.
     * @param email the User email.
     * @param password the User password.
     * @param status the User status.
     */
    public User(String firstName, String lastName, String email, String password, UserStatus status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.status = status;
    }

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    /**
     * Instantiates a new User.
     *
     * @param userId the User id.
     * @param firstName the User first name.
     * @param lastName the User last name.
     * @param email the User email.
     */
    public User(int userId, String firstName, String lastName, String email) {
        this.userId = userId;
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
     * Getting the orders of User.
     *
     * @return the orders of User.
     */
    public List<Order> getOrders() {
        return orders;
    }

    /**
     * Setting the orders of User.
     *
     */
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (status != null ? !status.equals(user.status) : user.status != null) return false;
        if (createDate != null ? !createDate.equals(user.createDate) : user.createDate != null) return false;
        if (updateDate != null ? !updateDate.equals(user.updateDate) : user.updateDate != null) return false;
        if (roles != null ? !roles.equals(user.roles) : user.roles != null) return false;
        return orders != null ? orders.equals(user.orders) : user.orders == null;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        result = 31 * result + (orders != null ? orders.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder line = new StringBuilder();
        line.append("User{userId=").append(userId)
                .append(", firstName='").append(firstName)
                .append(", lastName='").append(lastName)
                .append(", email='").append(email)
                .append(", role=").append(roles)
                .append(", status=").append(status)
                .append(", createDate=").append(createDate)
                .append(", updateDate=").append(updateDate)
                .append(", Orders={").append(orders).append('}');
        return line.toString();
    }
}
