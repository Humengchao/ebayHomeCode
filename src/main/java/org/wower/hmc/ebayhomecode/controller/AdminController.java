package org.wower.hmc.ebayhomecode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wower.hmc.ebayhomecode.bean.User;
import org.wower.hmc.ebayhomecode.exception.BusinessException;
import org.wower.hmc.ebayhomecode.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("addUser")
    public String addUser(@RequestBody User user) throws BusinessException {
        boolean success = adminService.addUser(user);
        return success ? "Add account successfully" : "Add account failed";
    }
}
