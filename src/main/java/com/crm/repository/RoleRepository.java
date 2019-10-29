package com.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crm.model.entity.TbRole;

@Repository
public interface RoleRepository extends JpaRepository<TbRole, Integer> {

}
