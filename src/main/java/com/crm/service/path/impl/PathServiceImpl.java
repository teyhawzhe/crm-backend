package com.crm.service.path.impl;

import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.model.entity.TbPath;
import com.crm.model.entity.TbPathPk;
import com.crm.model.queryForm.admin.path.PathForm;
import com.crm.repository.PathRepository;
import com.crm.service.path.PathService;
import com.crm.utils.UserProfileUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PathServiceImpl implements PathService {

	@Autowired
	private PathRepository pathRepository;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void save(PathForm pathForm) throws Exception {
		// TODO Auto-generated method stub
		try {
			TbPath tbPath = new TbPath();
			tbPath.setIcon(pathForm.getIcon());
			tbPath.setTitle(pathForm.getTitle());
			TbPathPk id = new TbPathPk();
			id.setPath(pathForm.getPath());
			id.setTier(pathForm.getTier());
			tbPath.setId(id);
			if (pathForm.getTier() >= 1) {
				tbPath.setParent(pathForm.getParent());
			} 
			pathRepository.save(tbPath);
		} catch (Exception ex) {
			throw new Exception("新增失敗!",ex);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void update(PathForm pathForm) throws Exception {
		// TODO Auto-generated method stub
		try {
			TbPath tbPath = new TbPath();
			tbPath.setIcon(pathForm.getIcon());
			tbPath.setTitle(pathForm.getTitle());
			TbPathPk id = new TbPathPk();
			id.setPath(pathForm.getPath());
			id.setTier(pathForm.getTier());
			tbPath.setId(id);

			if (pathForm.getTier() == 0) {
				pathRepository.updateMainMenu(tbPath.getId().getPath(), tbPath.getTitle(), tbPath.getIcon(),
						pathForm.getOldPath(), pathForm.getOldTitle(), pathForm.getOldIcon());
			} else {
				tbPath.setParent(pathForm.getParent());
				pathRepository.updateSubMenu(tbPath.getId().getPath(), tbPath.getTitle(), tbPath.getIcon(),
						tbPath.getParent(),  pathForm.getOldPath(), pathForm.getOldTitle(),
						pathForm.getOldIcon(), pathForm.getOldParent());
			}

		} catch (Exception ex) {
			throw new Exception("更新失敗!",ex);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public List<TbPath> getOneTierPath(int tier) {
		// TODO Auto-generated method stub
		return pathRepository.getOneTierPath(tier);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateSort(List<TbPath> tbPath) throws Exception {
		// TODO Auto-generated method stub
		for (int i = 0; i < tbPath.size(); i++) {
			tbPath.get(i).setSort(i + 1);
		}
		for (TbPath index : tbPath) {
			log.info(index.toString());
		}
		try {
			pathRepository.saveAll(tbPath);
		} catch (Exception e) {
			throw new Exception("更新失敗!",e);
		}

	}

	@Transactional(readOnly = true)
	@Override
	public List<TbPath> getPathByParent(String parent) {
		// TODO Auto-generated method stub
		return pathRepository.getPathByParent(2,parent);
	}

	@Transactional(readOnly = true)
	@Override
	public JSONArray getPath() {
		// TODO Auto-generated method stub
		 
		List<TbPath> oneTier = pathRepository.getOneTierPath(1,UserProfileUtils.getRoles());
		JSONArray jsonArray = new JSONArray();
		if (null != oneTier) {

			for (TbPath index : oneTier) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("path", "/"+index.getId().getPath());
				JSONObject meta = new JSONObject();
				meta.put("title", index.getTitle());
				meta.put("icon", index.getIcon());
				jsonObject.put("meta", meta);
				jsonObject.put("component", "LAYOUT");
				this.getChiledrenPath(index.getId().getTier()+1,index.getId().getPath(),jsonObject);
				log.info(jsonObject.toString());
				jsonArray.put(jsonObject);
			}

		}
		log.info(jsonArray.toString());
		return jsonArray;
	}
	
	public JSONObject getChiledrenPath(int tier,String parent,JSONObject parentObject){
		List<TbPath> twoTier = pathRepository.getPathByParent(tier,parent,UserProfileUtils.getRoles());
		if(null==twoTier || 0 ==twoTier.size() ) {
			return parentObject;
		}
		JSONArray jsonArray = new JSONArray();
		for(TbPath index : twoTier) {	 
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("path", index.getId().getPath());
			JSONObject meta = new JSONObject();
			meta.put("title", index.getTitle());
			meta.put("icon", index.getIcon());
			jsonObject.put("meta", meta);
			jsonObject.put("component", index.getId().getPath());	
			this.getChiledrenPath(index.getId().getTier()+1,index.getId().getPath(),jsonObject);
			jsonArray.put(jsonObject);
		}
		parentObject.put("children", jsonArray);
		return parentObject;
	}

	@Transactional(readOnly = true)
	@Override
	public JSONArray getAllPath() {
		// TODO Auto-generated method stub
		List<TbPath> oneTier = pathRepository.getOneTierPath(1);
		JSONArray jsonArray = new JSONArray();
		if (null != oneTier) {
			for (TbPath index : oneTier) {
				JSONObject path = new JSONObject();
				path.put("id", index.getId().getPath()+"&"+index.getId().getTier());
				path.put("label",index.getTitle());
				this.getFullPath(index.getId().getPath(),index.getId().getTier()+1,path);
				jsonArray.put(path);
			}
		}
		return jsonArray;
	}
	public void getFullPath(String pathParent , int tier,JSONObject parent) {
		List<TbPath> childrenPath  = pathRepository.getPathByParent(tier,pathParent);
		
		if(!childrenPath.isEmpty()) {
			JSONArray allPath = new JSONArray();
			for(TbPath index : childrenPath) {	 
				JSONObject path = new JSONObject();
				path.put("id", index.getId().getPath()+"&"+index.getId().getTier());
				path.put("label",index.getTitle());
				this.getFullPath(index.getId().getPath(),index.getId().getTier()+1,path);
				allPath.put(path);
				parent.put("children", allPath);
			}
		}
		
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void delete(PathForm pathForm) throws Exception {
		// TODO Auto-generated method stub
		log.info("pathForm " + pathForm.toString());	
		try {
			pathRepository.deletePath(pathForm.getPath(), pathForm.getTier());
			if(pathForm.getTier()==1) {
				pathRepository.deleteAllSubPath(pathForm.getParent());
			}
		}catch(Exception ex) {
			throw new Exception("刪除失敗!",ex);
		}
	}
}
