package org.wower.hmc.ebayhomecode.bean;

import lombok.Data;

@Data
public class Account {

    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_USER = "user";

    private Long userId;
    private String accountName;
    private String role;
}
