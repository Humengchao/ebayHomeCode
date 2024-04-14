package org.wower.hmc.ebayhomecode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.wower.hmc.ebayhomecode.bean.User;
import org.wower.hmc.ebayhomecode.exception.BusinessException;
import org.wower.hmc.ebayhomecode.service.AdminService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @Mock
    AdminService adminService;
    User user;

    @BeforeEach
    void setUp() {
        List<String> endpoint = new ArrayList<>();
        endpoint.add("AAA");
        endpoint.add("BBB");
        endpoint.add("CCC");

        user = new User();
        user.setUserId(6666L);
        user.setEndpoint(endpoint);
    }

    @Test
    void shouldAddUserSuccessfullyAndFailOnDuplicate() throws BusinessException {
        when(adminService.addUser(user)).thenReturn(true, false);

        boolean isSuccess1 = adminService.addUser(user);
        boolean isSuccess2 = adminService.addUser(user);

        assertTrue(isSuccess1); // add user successfully
        assertFalse(isSuccess2); // repeat add user

        verify(adminService, times(2)).addUser(user);
    }

}