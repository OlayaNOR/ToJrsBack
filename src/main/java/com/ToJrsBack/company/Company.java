package com.ToJrsBack.company;

import com.ToJrsBack.user.Role;
import com.ToJrsBack.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
public class Company extends User {

    @Column
    private String description;

    public Company() {
        this.setRole(Role.ROLE_COMPANY);
    }

}
