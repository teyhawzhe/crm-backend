package com.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crm.model.entity.SettingApiPermission;
import com.crm.model.entity.SettingApiPermissionPk;

@Repository
public interface SettingApiPermissionRepository extends JpaRepository<SettingApiPermission, SettingApiPermissionPk> {

	@Query(nativeQuery = true , value = "SELECT API FROM SETTING_API_PERMISSION WHERE ROLE = :role")
	public List<String> findAllByRole(@Param("role") String role);
	
	@Modifying
	@Query(nativeQuery = true , value = "DELETE SETTING_API_PERMISSION WHERE ROLE = :role")
	public void delete(@Param("role") String role);
}
