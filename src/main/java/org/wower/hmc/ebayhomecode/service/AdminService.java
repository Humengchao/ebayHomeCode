package org.wower.hmc.ebayhomecode.service;

import org.wower.hmc.ebayhomecode.bean.User;
import org.wower.hmc.ebayhomecode.exception.BusinessException;

public interface AdminService {

    public boolean addUser(User user) throws BusinessException;
}
