package com.arq.sptiarquitectura.repository;

import com.arq.sptiarquitectura.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends CrudRepository<Role, Long> {
}
