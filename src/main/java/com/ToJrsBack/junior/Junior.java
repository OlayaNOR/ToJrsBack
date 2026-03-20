package com.ToJrsBack.junior;

import com.ToJrsBack.user.Role;
import com.ToJrsBack.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
public class Junior extends User {

    @Column
    private String githubUrl;

    @Column
    private String portfolioUrl;

    @Column
    private String cvUrl;

    @Column
    private String primaryField; 

    public Junior() {
        this.setRole(Role.ROLE_JUNIOR);
    }

}