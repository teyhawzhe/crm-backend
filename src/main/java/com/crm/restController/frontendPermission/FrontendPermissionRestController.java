package com.crm.restController.frontendPermission;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crm.common.HttpStatus;
import com.crm.common.ResponseUtils;
import com.crm.common.Result;
import com.crm.service.frontendPermission.FrontendPermissionService;

@RestController
@RequestMapping("/frontendPermission")
public class FrontendPermissionRestController {
	
	@Autowired
	private FrontendPermissionService frontendPermissionService;
	
	@Autowired
	private ResponseUtils responseUtils;
	
	@PostMapping("/save")
	public ResponseEntity<Result<?>> save(@RequestBody @Valid FrontendPermissionInsertForm form , BindingResult br) throws Exception{
		return responseUtils.save(frontendPermissionService, "save", form, br); 
	}
	
}
