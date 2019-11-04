package com.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crm.model.entity.FrontendPermission;
import com.crm.model.entity.FrontendPermissionPk;

@Repository
public interface FrontendPermissionRepository extends JpaRepository<FrontendPermission, FrontendPermissionPk> {

}
