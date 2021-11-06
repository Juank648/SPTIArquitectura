package com.arq.sptiarquitectura.repository;

import com.arq.sptiarquitectura.entity.UserSPTI;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<UserSPTI, Long> {

}
