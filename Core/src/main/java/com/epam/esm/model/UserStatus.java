package com.epam.esm.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "statuses")
public class UserStatus implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_status")
    private int statusId;

    @Enumerated(EnumType.STRING)
    private EUserStatus name;

    @Transient
    @OneToMany(mappedBy="status", cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<>();

    public UserStatus() {
    }

    public UserStatus(int statusId, EUserStatus name) {
        this.statusId = statusId;
        this.name = name;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public EUserStatus getName() {
        return name;
    }

    public void setName(EUserStatus name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserStatus that = (UserStatus) o;

        if (statusId != that.statusId) return false;
        if (name != that.name) return false;
        return users != null ? users.equals(that.users) : that.users == null;
    }

    @Override
    public int hashCode() {
        int result = statusId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (users != null ? users.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserStatus{" +
                "statusId=" + statusId +
                ", name=" + name +
                ", users=" + users +
                '}';
    }
}
