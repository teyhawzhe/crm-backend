package com.crm.restController.path;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crm.common.HttpStatus;
import com.crm.common.ResponseUtils;
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

		pathService.save(pathForm);
		return ResponseEntity.ok(new Result<String>(HttpStatus.ok, "新增成功!"));
	}

	@PutMapping("/update")
	public ResponseEntity<Result<?>> update(@RequestBody @Valid PathForm pathForm, BindingResult br) throws Exception {
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

		pathService.update(pathForm);
		return ResponseEntity.ok(new Result<String>(HttpStatus.ok, "更新成功!"));

	}

	@GetMapping("/list")
	public ResponseEntity<Result<List<TbPath>>> list(@RequestParam(defaultValue = "1") int tier,
			@RequestParam(defaultValue = "1") String parent) {
		List<TbPath> list = null;
		if (tier == 1) {
			list = pathService.getOneTierPath(tier);
		} else {
			list = pathService.getPathByParent(parent);
		}

		if (list == null) {
			return ResponseEntity.ok(new Result(HttpStatus.ok,"查無資料!"));
		}
		return ResponseEntity.ok(new Result(HttpStatus.ok, "查詢成功!", list));
	}

	@PutMapping("/sort")
	public ResponseEntity<Result<?>> listOrder(@RequestBody PathFormList list) throws Exception {

		for (TbPath index : list.getList()) {
			log.info(index.toString());
		}

		pathService.updateSort(list.getList());
		return ResponseEntity.ok(new Result<String>(HttpStatus.ok, "排序成功!"));

	}

	@GetMapping("/path")
	public ResponseEntity<Result<String>> path() throws JsonProcessingException {
		JSONArray paths = pathService.getPath();
		return ResponseEntity.ok(new Result(HttpStatus.ok, "查詢成功!", paths.toString()));
	}

	@GetMapping("/allPath")
	public ResponseEntity<Result<String>> allPath() throws JsonProcessingException {
		JSONArray paths = pathService.getAllPath();
		
		return ResponseEntity.ok(new Result(HttpStatus.ok, "查詢成功!", paths.toString()));
	}

	@DeleteMapping("/delete")
	public ResponseEntity<Result<String>> delete(@RequestBody PathForm pathForm) throws Exception{
		pathService.delete(pathForm);
		return ResponseEntity.ok(new Result(HttpStatus.ok, "刪除成功!"));
	}
	
	
}
