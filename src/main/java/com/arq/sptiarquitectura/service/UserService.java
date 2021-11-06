package com.arq.sptiarquitectura.service;

import com.arq.sptiarquitectura.ChangePasswordForm;
import com.arq.sptiarquitectura.entity.UserSPTI;

public interface UserService {
    public Iterable<UserSPTI> getAllUsers();

    UserSPTI createUser(UserSPTI userSPTI) throws Exception;

    public UserSPTI getUserById(Long id) throws Exception;

    public UserSPTI updateUser(UserSPTI userSPTI) throws Exception;

    public void deleteUser(Long id) throws Exception;

    public UserSPTI changePassword(ChangePasswordForm form) throws Exception;
}
