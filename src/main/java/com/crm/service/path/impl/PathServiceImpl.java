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
			log.debug(ex.getLocalizedMessage());
			throw new Exception("新增失敗!");
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

			if (pathForm.getTier() > 1) {
				pathRepository.updateMainMenu(tbPath.getId().getPath(), tbPath.getTitle(), tbPath.getIcon(),
						pathForm.getOldPath(), pathForm.getOldTitle(), pathForm.getOldIcon());
			} else {
				tbPath.setParent(pathForm.getParent());
				pathRepository.updateSubMenu(tbPath.getId().getPath(), tbPath.getTitle(), tbPath.getIcon(),
						tbPath.getParent(),  pathForm.getOldPath(), pathForm.getOldTitle(),
						pathForm.getOldIcon(), pathForm.getOldParent());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception();
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
			e.printStackTrace();
			throw new Exception();
		}

	}

	@Transactional(readOnly = true)
	@Override
	public List<TbPath> getPathByParent(String parent) {
		// TODO Auto-generated method stub
		return pathRepository.getPathByParent(2,parent);
	}

	@Override
	public JSONArray getPath() {
		// TODO Auto-generated method stub
		 
		List<TbPath> oneTier = pathRepository.getOneTierPath(1);
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
		List<TbPath> twoTier = pathRepository.getPathByParent(tier,parent);
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

	@Override
	public JSONArray getAllPath() {
		// TODO Auto-generated method stub
		List<TbPath> oneTier = pathRepository.getOneTierPath(1);
		JSONArray jsonArray = new JSONArray();
		if (null != oneTier) {

			for (TbPath index : oneTier) {
				JSONObject path = new JSONObject();
				path.put("path", index.getId().getPath());
				path.put("title",index.getTitle());
				JSONObject fullPath = new JSONObject();
				fullPath.put("path", index.getId().getPath());
				fullPath.put("title", index.getTitle());
				jsonArray.put(path);
				this.getFullPath(index.getId().getTier()+1,index.getId().getPath(),fullPath,jsonArray);
			}

		}
		return jsonArray;
	}
	public void getFullPath(int tier,String parent,JSONObject fullPath,JSONArray jsonArray) {
		List<TbPath> twoTier = pathRepository.getPathByParent(tier,parent);
		if(null==twoTier || 0 ==twoTier.size() ) {
			
		}else {
			for(TbPath index : twoTier) {	 
				JSONObject path = new JSONObject();
				path.put("path", fullPath.get("path")+"/"+index.getId().getPath());
				path.put("title", fullPath.get("title")+"/"+index.getTitle());
				jsonArray.put(path);
				this.getFullPath(index.getId().getTier()+1,index.getId().getPath(),path,jsonArray);
			}
		}	
	}
}
