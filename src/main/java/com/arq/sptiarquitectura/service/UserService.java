package com.arq.sptiarquitectura.service;

import com.arq.sptiarquitectura.entity.UserSPTI;

public interface UserService {
    public Iterable<UserSPTI> getAllUsers();

    UserSPTI createUser(UserSPTI userSPTI) throws Exception;
}
