package org.wower.hmc.ebayhomecode.service.impl;

import org.springframework.stereotype.Service;
import org.wower.hmc.ebayhomecode.bean.User;
import org.wower.hmc.ebayhomecode.exception.BusinessException;
import org.wower.hmc.ebayhomecode.service.AdminService;
import org.wower.hmc.ebayhomecode.util.AppendingObjectOutputStream;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class AdminServiceImpl implements AdminService {

    @Override
    public boolean addUser(User user) throws BusinessException {

        // users.bat存在的话先读取判断下该用户是否已存在
        if (Files.exists(Paths.get("users.bat"))) {
            try (
                    FileInputStream fis = new FileInputStream("users.bat");
                    ObjectInputStream ois = new ObjectInputStream(fis)
            ) {
                User tempUser;
                while ((tempUser = (User) ois.readObject()) != null) {
                    if (tempUser.getUserId().equals(user.getUserId())) {
                        throw new BusinessException("User already exists");
                    }
                }
            } catch (BusinessException e) {
                throw e;
            } catch (EOFException e) {
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        try (
                FileOutputStream fos = new FileOutputStream("users.bat", true);
                ObjectOutputStream oos = fos.getChannel().position() > 0 ? new AppendingObjectOutputStream(fos) : new ObjectOutputStream(fos)
        ) {
            oos.writeObject(user);
            System.out.println("A new user is added to users.bat");
        } catch (IOException i) {
            i.printStackTrace();
        }
        return true;
    }
}
