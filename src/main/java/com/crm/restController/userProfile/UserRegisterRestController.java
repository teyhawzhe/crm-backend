package com.crm.restController.userProfile;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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
import com.crm.common.ResponseMap;
import com.crm.common.Result;
import com.crm.model.queryForm.setting.userRegister.UserRegisterForm;
import com.crm.service.userProfile.UserProfileService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserRegisterRestController {

	@Autowired
	private UserProfileService userProfileService;
 
	@PostMapping("/add")
	public ResponseEntity<Result<String>> add(@RequestBody @Valid UserRegisterForm userRegisterForm, BindingResult br)
			throws Exception {
		if (br.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			for (ObjectError index : br.getAllErrors()) {
				sb.append(index.getDefaultMessage() + "<br />");
			}
			return ResponseEntity.ok(new Result<String>(HttpStatus.valid, null, sb.toString()));
		}
		
		userProfileService.save(userRegisterForm);
		return ResponseEntity.ok(new Result<String>(HttpStatus.ok, "新增成功!"));
	}

	@GetMapping("/list")
	public ResponseEntity<Result<List<Map<String, Object>>>> query(@RequestParam Map<String, Object> map) {
		List<Map<String,Object>> lists = userProfileService.getList(map);
		return ResponseMap.getResult(lists);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<Result<String>> delete(String username) throws Exception{
		userProfileService.delete(username);
		return ResponseEntity.ok(new Result<String>(HttpStatus.ok, "刪除成功!"));
	}
	
	@PostMapping("/update")
	public ResponseEntity<Result<String>> update(@RequestBody UserRegisterForm userRegisterForm, BindingResult br) throws Exception{
		if (br.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			for (ObjectError index : br.getAllErrors()) {
				sb.append(index.getDefaultMessage() + "<br />");
			}
			return ResponseEntity.ok(new Result<String>(HttpStatus.valid, null, sb.toString()));
		}
		userProfileService.update(userRegisterForm);
		return ResponseEntity.ok(new Result<String>(HttpStatus.ok, "更新成功!"));
	}
}
