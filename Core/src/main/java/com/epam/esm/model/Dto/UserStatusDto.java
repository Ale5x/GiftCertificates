package com.epam.esm.model.Dto;

import com.epam.esm.model.EUserStatus;

public class UserStatusDto {

    private int statusId;

    private EUserStatus status;

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public EUserStatus getStatus() {
        return status;
    }

    public void setStatus(EUserStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserStatusDto that = (UserStatusDto) o;

        if (statusId != that.statusId) return false;
        return status == that.status;
    }

    @Override
    public int hashCode() {
        int result = statusId;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserStatusDto{" +
                "statusId=" + statusId +
                ", status=" + status +
                '}';
    }
}
