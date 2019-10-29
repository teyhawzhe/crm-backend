package com.crm.restController.path;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crm.common.HttpStatus;
import com.crm.common.Result;
import com.crm.model.Path;
import com.crm.model.entity.TbPath;
import com.crm.model.queryForm.admin.path.PathForm;
import com.crm.model.queryForm.admin.path.PathFormList;
import com.crm.service.path.PathService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/path")
public class PathRestController {

	@Autowired
	private PathService pathService;

	@PostMapping("/add")
	public ResponseEntity<Result<String>> add(@RequestBody @Valid PathForm pathForm, BindingResult br)
			throws Exception {
		log.info("pathForm " + pathForm.toString());
		if (br.hasErrors() || (pathForm.getTier() == 2 && StringUtils.isBlank(pathForm.getParent()))) {
			StringBuilder sb = new StringBuilder();
			for (ObjectError index : br.getAllErrors()) {
				sb.append(index.getDefaultMessage() + "<br />");
			}
			if (pathForm.getTier() == 2) {
				if (StringUtils.isBlank(pathForm.getParent())) {
					sb.append("請選擇主目錄" + "<br />");
				}
			}
			return ResponseEntity.ok(new Result<String>(HttpStatus.valid, null, sb.toString()));
		}
		try {
			pathService.save(pathForm);
			return ResponseEntity.ok(new Result<String>(HttpStatus.ok, "新增成功!"));
		} catch (Exception e) {
			throw new Exception("新增失敗!");
		}

	}

	@PostMapping("/update")
	public ResponseEntity<Result<String>> update(@RequestBody @Valid PathForm pathForm, BindingResult br)
			throws Exception {
		log.info("pathForm " + pathForm.toString());
		if (br.hasErrors() || (pathForm.getTier() == 2 && StringUtils.isBlank(pathForm.getParent()))) {
			StringBuilder sb = new StringBuilder();
			for (ObjectError index : br.getAllErrors()) {
				sb.append(index.getDefaultMessage() + "<br />");
			}
			if (pathForm.getTier() == 2) {
				if (StringUtils.isBlank(pathForm.getParent())) {
					sb.append("請選擇主目錄" + "<br />");
				}
			}

			return ResponseEntity.ok(new Result<String>(HttpStatus.valid, null, sb.toString()));
		}
		try {
			pathService.update(pathForm);
			return ResponseEntity.ok(new Result<String>(HttpStatus.ok, "更新成功!"));
		} catch (Exception e) {
			throw new Exception("更新失敗!");
		}

	}

	@GetMapping("/list")
	public ResponseEntity<Result<List<TbPath>>> list(@RequestParam(defaultValue = "1") int tier,@RequestParam(defaultValue = "1") String parent) {
		List<TbPath> list = null;
		if(tier==1) {
			list = pathService.getOneTierPath(tier);
		}else {
			list =pathService.getPathByParent(parent);
		}
		 
		if (list == null) {
			return ResponseEntity.ok(new Result(HttpStatus.ok, null, "查無資料!"));
		}
		return ResponseEntity.ok(new Result(HttpStatus.ok, list, "查詢成功!"));
	}

	@PostMapping("/sort")
	public ResponseEntity<Result<String>> listOrder(@RequestBody PathFormList list) throws Exception {

		for (TbPath index : list.getList()) {
			log.info(index.toString());
		}

		try {
			pathService.updateSort(list.getList());
			return ResponseEntity.ok(new Result<String>(HttpStatus.ok, "排序成功!"));
		} catch (Exception e) {
			throw new Exception("排序失敗!");
		}
	}

	
	@GetMapping("/path")
	public ResponseEntity<Result<String>> path() throws JsonProcessingException {
		JSONArray paths = pathService.getPath();
		return ResponseEntity.ok(new Result(HttpStatus.ok, paths.toString(), "查詢成功!"));
	}
	
	@GetMapping("/allPath")
	public ResponseEntity<Result<String>> allPath() throws JsonProcessingException {
		JSONArray paths = pathService.getAllPath();
		return ResponseEntity.ok(new Result(HttpStatus.ok, paths.toString(), "查詢成功!"));
	}
	
	
}
