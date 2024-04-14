package org.wower.hmc.ebayhomecode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.wower.hmc.ebayhomecode.bean.Account;
import org.wower.hmc.ebayhomecode.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{resource}")
    public String getResource(@PathVariable String resource) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        Account account = (Account) requestAttributes.getAttribute("account", RequestAttributes.SCOPE_REQUEST);
        boolean hasPermission = userService.checkPermission(resource, account.getUserId());
        return hasPermission ? resource + " has permission to be accessed."
                : "No permission to access " + resource;
    }
}