package com.epam.esm.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * The type Role. The class is mapped to a table from the database.
 *
 * @author Alexander Pishchala
 */
@Entity
@Table(name = "roles", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Role implements Serializable {

    private static final long serialVersionUID = 6294794750764524328L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_roles")
    private int roleId;

    @Enumerated(EnumType.STRING)
    private ERole name;

    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    /**
     * Instantiates a new Role.
     */
    public Role() {
    }

    /**
     * Instantiates a new Role.
     *
     * @param name the Role name.
     */
    public Role(ERole name) {
        this.name = name;
    }

    /**
     * Instantiates a new Role.
     *
     * @param roleId the Role id.
     * @param name the Role name.
     */
    public Role(int roleId, ERole name) {
        this.roleId = roleId;
        this.name = name;
    }

    /**
     * Getting the Role id.
     *
     * @return the Role id
     */
    public int getRoleId() {
        return roleId;
    }

    /**
     * Setting the Role id.
     *
     */
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    /**
     * Getting the name.
     *
     * @return the name
     */
    public ERole getName() {
        return name;
    }

    /**
     * Setting the name.
     *
     */
    public void setName(ERole name) {
        this.name = name;
    }

    /**
     * Getting the list of Users.
     *
     * @return the list of Users.
     */
    public Set<User> getUsers() {
        return users;
    }

    /**
     * Setting the list of Users.
     *
     */
    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (roleId != role.roleId) return false;
        if (name != null ? !name.equals(role.name) : role.name != null) return false;
        return users != null ? users.equals(role.users) : role.users == null;
    }

    @Override
    public int hashCode() {
        int result = roleId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (users != null ? users.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder line = new StringBuilder();
        line.append("Role{roleId=").append(roleId)
                .append(", name='").append(name).append('}');
        return line.toString();
    }
}
