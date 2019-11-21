package com.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crm.model.entity.SettingPathPermission;
import com.crm.model.entity.SettingPathPermissionPk;

@Repository
public interface SettingPathPermissionRepository extends JpaRepository<SettingPathPermission, SettingPathPermissionPk> {

	@Query(nativeQuery = true , value = "SELECT PATH ||'&'|| TIER FROM SETTING_PATH_PERMISSION WHERE ROLE = :role")
	public List<String> query(@Param("role") String role);
	
	@Modifying
	@Query(nativeQuery = true , value = "DELETE SETTING_PATH_PERMISSION WHERE ROLE = :role")
	public void deleteByRole(@Param("role") String role);
}
