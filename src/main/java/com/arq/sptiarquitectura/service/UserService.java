package com.arq.sptiarquitectura.service;

import javax.validation.Valid;

import com.arq.sptiarquitectura.Exception.UsernameOrIdNotFound;
import com.arq.sptiarquitectura.entity.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;



public interface UserService {

	public Iterable<User> getAllUsers();

	public User createUser(User user) throws Exception;

	public User getUserById(Long id) throws Exception;
	
	public User updateUser(User user) throws Exception;
	
	public void deleteUser(Long id) throws UsernameOrIdNotFound;
	
	public User changePassword(com.arq.sptiarquitectura.dto.ChangePasswordForm form) throws Exception;
}
