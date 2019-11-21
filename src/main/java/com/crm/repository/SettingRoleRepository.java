package com.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crm.model.entity.SettingRole;

@Repository
public interface SettingRoleRepository extends JpaRepository<SettingRole, String> {
	
	@Modifying
	@Query(nativeQuery = true , value = "UPDATE SETTING_ROLE SET DEF = :def , STATUS = :status WHERE ROLE = :role")
	public void update(@Param("def") String def , @Param("status") boolean status ,@Param("role") String role);
	
	@Modifying
	@Query(nativeQuery = true , value = "DELETE SETTING_ROLE  WHERE ROLE = :role")
	public void delete(@Param("role") String role);
	
}
