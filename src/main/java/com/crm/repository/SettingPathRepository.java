package com.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crm.model.entity.SettingPath;
import com.crm.model.entity.SettingPathPk;

@Repository
public interface SettingPathRepository extends JpaRepository<SettingPath, SettingPathPk> {

	@Query(value = "SELECT * FROM SETTING_PATH WHERE TIER =:tier ORDER BY SORT", nativeQuery = true)
	public List<SettingPath> getOneTierPath(@Param("tier") int tier);
	
	@Query(value = "SELECT DISTINCT  SETTING_PATH.* FROM SETTING_PATH LEFT JOIN SETTING_PATH_PERMISSION ON SETTING_PATH.PATH = SETTING_PATH_PERMISSION.PATH WHERE SETTING_PATH.TIER=:tier AND SETTING_PATH_PERMISSION.ROLE IN (SELECT ROLE FROM SETTING_ROLE WHERE ROLE IN  (:role) AND STATUS = 1 ) ORDER BY SORT" , nativeQuery = true)
	public List<SettingPath> getOneTierPath(@Param("tier") int tier,@Param("role") List<String> role);
	
	@Query(value = "SELECT * FROM SETTING_PATH WHERE TIER =:tier and PARENT=:parent ORDER BY SORT", nativeQuery = true)
	public List<SettingPath> getPathByParent(@Param("tier") int tier , @Param("parent") String parent);
	
	@Query(value = "SELECT DISTINCT  SETTING_PATH.* FROM SETTING_PATH LEFT JOIN SETTING_PATH_PERMISSION ON SETTING_PATH.PATH = SETTING_PATH_PERMISSION.PATH WHERE SETTING_PATH.TIER=:tier AND SETTING_PATH.PARENT=:parent AND SETTING_PATH_PERMISSION.ROLE IN (SELECT ROLE FROM SETTING_ROLE WHERE ROLE IN  (:role) AND STATUS = 1 ) ORDER BY SORT", nativeQuery = true)
	public List<SettingPath> getPathByParent(@Param("tier") int tier , @Param("parent") String parent,@Param("role") List<String> role);

	@Modifying
	@Query(value = "UPDATE SETTING_PATH SET PATH=:path , TITLE=:title , ICON=:icon  where PATH=:oldPath and TITLE=:oldTitle and ICON=:oldIcon", nativeQuery = true)
	public void updateMainMenu(@Param("path") String path, @Param("title") String title, @Param("icon") String icon,
			@Param("oldPath") String oldPath, @Param("oldTitle") String oldTitle, @Param("oldIcon") String oldIcon);
	
	@Modifying
	@Query(value = "UPDATE SETTING_PATH SET PATH=:path , TITLE=:title , ICON=:icon ,PARENT=:parent where PATH=:oldPath and TITLE=:oldTitle and ICON=:oldIcon and PARENT=:oldParent", nativeQuery = true)
	public void updateSubMenu(@Param("path") String path, @Param("title") String title, @Param("icon") String icon, @Param("parent") String parent,
			@Param("oldPath") String oldPath, @Param("oldTitle") String oldTitle, @Param("oldIcon") String oldIcon, @Param("oldParent") String oldParent);

	@Modifying
	@Query(nativeQuery = true , value = "DELETE　SETTING_PATH　WHERE PATH =:path AND　TIER =:tier ")
	public void deletePath(@Param("path")String path , @Param("tier") int tier);
	
	@Modifying
	@Query(nativeQuery = true , value = "DELETE　SETTING_PATH　WHERE PARENT = :parent ")
	public void deleteAllSubPath(@Param("parent")String parent);
	
}
