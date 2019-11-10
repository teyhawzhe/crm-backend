package com.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crm.model.entity.TbRole;

@Repository
public interface RoleRepository extends JpaRepository<TbRole, Integer> {
	
	@Modifying
	@Query(nativeQuery = true , value = "UPDATE TB_ROLE SET DEF = :def WHERE ROLE = :role")
	public void update(@Param("def") String def , @Param("role") String role);
	
	@Modifying
	@Query(nativeQuery = true , value = "DELETE TB_ROLE  WHERE ROLE = :role")
	public void delete(@Param("role") String role);
	
}
