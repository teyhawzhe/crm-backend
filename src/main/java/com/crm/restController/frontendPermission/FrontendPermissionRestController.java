package com.crm.restController.frontendPermission;

import java.util.List;

import javax.validation.Valid;

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
import com.crm.service.frontendPermission.FrontendPermissionService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/frontendPermission")
@Slf4j
public class FrontendPermissionRestController {
	
	@Autowired
	private FrontendPermissionService frontendPermissionService;
	
	@PostMapping("/save")
	public ResponseEntity<Result<?>> save(@RequestBody @Valid FrontendPermissionInsertForm form , BindingResult br) throws Exception{
	
		if (br.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			for (ObjectError index : br.getAllErrors()) {
				sb.append(index.getDefaultMessage() + "<br />");
			}
			return ResponseEntity.ok(new Result<String>(HttpStatus.valid, null, sb.toString()));
		}
		frontendPermissionService.save(form);
		return ResponseEntity.ok(new Result<String>(HttpStatus.ok, "新增成功!")); 
	}
	
	@GetMapping("/query")
	public ResponseEntity<Result<List<String>>> query(@RequestParam("role") String role){
		log.info("role "+role);
		return ResponseEntity.ok(new Result<List<String>>(HttpStatus.ok, "新增成功!",frontendPermissionService.query(role))); 
	}
	
}
