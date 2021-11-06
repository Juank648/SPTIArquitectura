package com.arq.sptiarquitectura.service;

import com.arq.sptiarquitectura.entity.UserSPTI;
import com.arq.sptiarquitectura.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceIMPL implements UserService{
    @Autowired
    UserRepository repository;

    @Override
    public Iterable<UserSPTI> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public UserSPTI createUser(UserSPTI userSPTI) throws Exception {
        if(checkUsernameAvailable(userSPTI) && checkPasswordValid(userSPTI)){
            userSPTI = repository.save(userSPTI);
        }
        return userSPTI;
    }

    private Boolean checkUsernameAvailable(UserSPTI userSPTI) throws Exception {
        Optional <UserSPTI> userFound = repository.findUserSPTIByUsername(userSPTI.getUsername());
        if(userFound.isPresent()){
            throw new Exception("Username no disponible");
        }
        return true;
    }
    private Boolean checkPasswordValid(UserSPTI userSPTI) throws Exception {
        if(!userSPTI.getPasswd().equals(userSPTI.getConfirmPassword())){
            throw new Exception("Password y ConfirmPassword no son iguales");
        }
        return true;
    }


}
