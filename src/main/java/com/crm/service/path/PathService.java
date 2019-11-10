package com.crm.service.path;

import java.util.List;

import org.json.JSONArray;

import com.crm.model.Path;
import com.crm.model.entity.TbPath;
import com.crm.model.queryForm.admin.path.PathForm;

public interface PathService {
	
	public void save(PathForm pathForm) throws Exception;
	
	public void update(PathForm pathForm) throws Exception;
	
	public List<TbPath> getOneTierPath(int tier);
	
	public List<TbPath> getPathByParent(String parent);
	
	public void updateSort(List<TbPath> tbPath) throws Exception;
	
	public JSONArray getPath();
	
	public JSONArray getAllPath();
	
	public void delete(PathForm pathForm) throws Exception;
	
}
