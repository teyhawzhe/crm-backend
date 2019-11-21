package com.crm.service.setting.path;

import java.util.List;

import org.json.JSONArray;

import com.crm.model.Path;
import com.crm.model.entity.SettingPath;
import com.crm.model.queryForm.admin.path.PathForm;

public interface SettingPathService {
	
	public void save(PathForm pathForm) throws Exception;
	
	public void update(PathForm pathForm) throws Exception;
	
	public List<SettingPath> getOneTierPath(int tier);
	
	public List<SettingPath> getPathByParent(String parent);
	
	public void updateSort(List<SettingPath> tbPath) throws Exception;
	
	public JSONArray getPath();
	
	public JSONArray getAllPath();
	
	public void delete(PathForm pathForm) throws Exception;
	
}
