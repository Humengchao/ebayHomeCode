package org.wower.hmc.ebayhomecode.service;

public interface UserService {
    public boolean checkPermission(String resource, Long userId);
}
