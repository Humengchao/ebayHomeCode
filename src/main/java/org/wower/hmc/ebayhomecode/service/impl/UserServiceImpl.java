package org.wower.hmc.ebayhomecode.service.impl;

import org.springframework.stereotype.Service;
import org.wower.hmc.ebayhomecode.bean.User;
import org.wower.hmc.ebayhomecode.service.UserService;

import java.io.*;


@Service
public class UserServiceImpl implements UserService {
    @Override
    public boolean checkPermission(String resource, Long userId) {
        try (
                FileInputStream fis = new FileInputStream("users.bat");
                ObjectInputStream ois = new ObjectInputStream(fis)
        ) {
            User user;
            while ((user = (User) ois.readObject()) != null) {
                if (user.getUserId().equals(userId) && user.getEndpoint().contains(resource)) {
                    return true;
                }
            }
        } catch (EOFException e) {
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
