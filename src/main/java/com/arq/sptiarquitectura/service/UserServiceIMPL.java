package com.arq.sptiarquitectura.service;

import com.arq.sptiarquitectura.entity.UserSPTI;
import com.arq.sptiarquitectura.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceIMPL implements UserService{
    @Autowired
    UserRepository repository;

    @Override
    public Iterable<UserSPTI> getAllUsers() {
        return repository.findAll();
    }
}
