package com.crm.restController.userProfile;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crm.common.HttpStatus;
import com.crm.common.PageResult;
import com.crm.common.ResponseMap;
import com.crm.common.Result;
import com.crm.model.queryForm.setting.userRegister.UserRegisterForm;
import com.crm.service.userProfile.UserProfileService;
import com.crm.utils.LoginUser;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserRegisterRestController {

	@Autowired
	private UserProfileService userProfileService;
 
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/add")
	public ResponseEntity<Result<String>> add(@RequestBody @Valid UserRegisterForm userRegisterForm, BindingResult br)
			throws Exception {

		log.info("LoginUser " + LoginUser.getUsername());
		log.info("userRegisterForm " + userRegisterForm.toString());
		if (br.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			for (ObjectError index : br.getAllErrors()) {
				sb.append(index.getDefaultMessage() + "<br />");
			}
			return ResponseEntity.ok(new Result<String>(HttpStatus.valid, null, sb.toString()));
		}
		try {
			userProfileService.save(userRegisterForm);
			return ResponseEntity.ok(new Result<String>(HttpStatus.ok, "新增成功!"));
		} catch (Exception ex) {
			throw new Exception("新增失敗!");
		}

	}

	@GetMapping("/list")
	public ResponseEntity<Result<List<Map<String, Object>>>> query(@RequestParam Map<String, Object> map) {
		log.info("list");
		for(String index : map.keySet()) {
			log.info(index +"  " + map.get(index));
		}
		
		List<Map<String,Object>> lists = userProfileService.getList(map);
		return ResponseMap.getResult(lists);
	}

}
