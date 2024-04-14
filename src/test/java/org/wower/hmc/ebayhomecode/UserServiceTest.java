package org.wower.hmc.ebayhomecode;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.wower.hmc.ebayhomecode.bean.User;
import org.wower.hmc.ebayhomecode.exception.BusinessException;
import org.wower.hmc.ebayhomecode.service.AdminService;
import org.wower.hmc.ebayhomecode.service.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    AdminService adminService;
    @Autowired
    UserService userService;

    User user;

    @BeforeEach
    void setUp() throws BusinessException {
        List<String> endpoint = new ArrayList<>();
        endpoint.add("AAA");
        endpoint.add("BBB");
        endpoint.add("CCC");

        user = new User();
        user.setUserId(6666L);
        user.setEndpoint(endpoint);

        adminService.addUser(user);
    }

    @Test
    public void testCheckPermission() {

        assertTrue(userService.checkPermission("AAA", 6666L));
        assertFalse(userService.checkPermission("DDD", 6666L));
        assertFalse(userService.checkPermission("DDD", 7777L));
    }


    @AfterEach
    void delUserBat() {
        try {
            Path path = Paths.get("users.bat");
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}