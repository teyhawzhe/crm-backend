package com.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crm.model.entity.TbPath;
import com.crm.model.entity.TbPathPk;

@Repository
public interface PathRepository extends JpaRepository<TbPath, TbPathPk> {

	@Query(value = "SELECT * FROM TB_PATH WHERE TIER =:tier ORDER BY SORT", nativeQuery = true)
	public List<TbPath> getOneTierPath(@Param("tier") int tier);
	
	@Query(value = "SELECT DISTINCT  TB_PATH.* FROM TB_PATH LEFT JOIN FRONTEND_PERMISSION ON TB_PATH.PATH = FRONTEND_PERMISSION.PATH WHERE TB_PATH.TIER=:tier AND FRONTEND_PERMISSION.ROLE IN (:role) ORDER BY SORT" , nativeQuery = true)
	public List<TbPath> getOneTierPath(@Param("tier") int tier,@Param("role") List<String> role);
	
	@Query(value = "SELECT * FROM TB_PATH WHERE TIER =:tier and PARENT=:parent ORDER BY SORT", nativeQuery = true)
	public List<TbPath> getPathByParent(@Param("tier") int tier , @Param("parent") String parent);
	
	@Query(value = "SELECT DISTINCT  TB_PATH.* FROM TB_PATH LEFT JOIN FRONTEND_PERMISSION ON TB_PATH.PATH = FRONTEND_PERMISSION.PATH WHERE TB_PATH.TIER=:tier AND TB_PATH.PARENT=:parent AND FRONTEND_PERMISSION.ROLE IN (:role) ORDER BY SORT", nativeQuery = true)
	public List<TbPath> getPathByParent(@Param("tier") int tier , @Param("parent") String parent,@Param("role") List<String> role);

	@Modifying
	@Query(value = "UPDATE TB_PATH SET PATH=:path , TITLE=:title , ICON=:icon  where PATH=:oldPath and TITLE=:oldTitle and ICON=:oldIcon", nativeQuery = true)
	public void updateMainMenu(@Param("path") String path, @Param("title") String title, @Param("icon") String icon,
			@Param("oldPath") String oldPath, @Param("oldTitle") String oldTitle, @Param("oldIcon") String oldIcon);
	
	@Modifying
	@Query(value = "UPDATE TB_PATH SET PATH=:path , TITLE=:title , ICON=:icon ,PARENT=:parent where PATH=:oldPath and TITLE=:oldTitle and ICON=:oldIcon and PARENT=:oldParent", nativeQuery = true)
	public void updateSubMenu(@Param("path") String path, @Param("title") String title, @Param("icon") String icon, @Param("parent") String parent,
			@Param("oldPath") String oldPath, @Param("oldTitle") String oldTitle, @Param("oldIcon") String oldIcon, @Param("oldParent") String oldParent);

	@Modifying
	@Query(nativeQuery = true , value = "DELETE　TB_PATH　WHERE PATH =:path AND　TIER =:tier ")
	public void deletePath(@Param("path")String path , @Param("tier") int tier);
	
	@Modifying
	@Query(nativeQuery = true , value = "DELETE　TB_PATH　WHERE PARENT = :parent ")
	public void deleteAllSubPath(@Param("parent")String parent);
	
}
