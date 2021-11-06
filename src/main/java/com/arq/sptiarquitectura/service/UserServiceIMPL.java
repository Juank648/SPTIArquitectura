package com.arq.sptiarquitectura.service;

import com.arq.sptiarquitectura.ChangePasswordForm;
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

    @Override
    public UserSPTI getUserById(Long id) throws Exception {
        return repository.findById(id).orElseThrow(()->new Exception("El usuario para editar no existe"));
    }

    @Override
    public UserSPTI updateUser(UserSPTI fromUser) throws Exception {
        UserSPTI toUser = getUserById(fromUser.getId());
        mapUser(fromUser, toUser);
        return  repository.save(toUser);
    }

    @Override
    public void deleteUser(Long id) throws Exception {
        UserSPTI userSpti = getUserById(id);
        repository.delete(userSpti);
    }

    @Override
    public UserSPTI changePassword(ChangePasswordForm form) throws Exception {
        UserSPTI userSPTI = getUserById(form.getId());
        if(!userSPTI.getPasswd().equals(form.getCurrentPassword())){
            throw new Exception("Current password invalidate");
        }
        if(userSPTI.getPasswd().equals(form.getNewPassword())){
            throw new Exception("Nuevo debe ser diferente al password actual");
        }
        if(!form.getNewPassword().equals(form.getConfirmPassword())){
            throw new Exception("Nuevo password y confirm password no coinciden");
        }
        userSPTI.setPasswd(form.getNewPassword());
        return repository.save(userSPTI);

    }

    /**
     * Map everything but the password.
     * @param from
     * @param to
     */
    protected void mapUser(UserSPTI from,UserSPTI to) {
        to.setUsername(from.getUsername());
        to.setFirstName(from.getFirstName());
        to.setLastName(from.getLastName());
        to.setEmail(from.getEmail());
        to.setRoles(from.getRoles());
    }

    private Boolean checkUsernameAvailable(UserSPTI userSPTI) throws Exception {
        Optional <UserSPTI> userFound = repository.findUserSPTIByUsername(userSPTI.getUsername());
        if(userFound.isPresent()){
            throw new Exception("Username no disponible");
        }
        return true;
    }
    private Boolean checkPasswordValid(UserSPTI userSPTI) throws Exception {
        if(userSPTI.getConfirmPassword() == null || userSPTI.getConfirmPassword().isEmpty()){
            throw new Exception("ConfirmPassword es obligatorio");
        }
        if(!userSPTI.getPasswd().equals(userSPTI.getConfirmPassword())){
            throw new Exception("Password y ConfirmPassword no son iguales");
        }
        return true;
    }




}
