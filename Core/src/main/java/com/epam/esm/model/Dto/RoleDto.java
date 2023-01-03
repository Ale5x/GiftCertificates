package com.epam.esm.model.Dto;

import com.epam.esm.model.ERole;
import org.springframework.hateoas.RepresentationModel;

/**
 * The type Role Dto. The Dto class. It is used to transfer data between layers.
 *
 * @author Alexander Pishchala
 */
public class RoleDto extends RepresentationModel<RoleDto> {

    private int roleId;

    private ERole name;

    /**
     * Instantiates a new Role.
     */
    public RoleDto() {
    }

    /**
     * Instantiates a new Role.
     *
     * @param roleId the Role Id.
     * @param name the Role name.
     */
    public RoleDto(int roleId, ERole name) {
        this.roleId = roleId;
        this.name = name;
    }

    /**
     * Getting the role id.
     *
     * @return the role id.
     */
    public int getRoleId() {
        return roleId;
    }

    /**
     * Setting the role id.
     *
     */
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    /**
     * Getting the role name.
     *
     * @return the role name.
     */
    public ERole getName() {
        return name;
    }

    /**
     * Setting the role name.
     *
     */
    public void setName(ERole name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        RoleDto roleDto = (RoleDto) o;

        if (roleId != roleDto.roleId) return false;
        return name == roleDto.name;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + roleId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RoleDto{roleId=").append(roleId).append(", name=").append(name).append('}');
        return builder.toString();
    }
}
