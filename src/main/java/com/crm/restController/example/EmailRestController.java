package com.crm.restController.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crm.common.HttpStatus;
import com.crm.common.Result;
import com.crm.utils.EmailUtils;

@RestController
@RequestMapping("/example/email")
public class EmailRestController {

	@Autowired
	private EmailUtils emailUtils;
	
	@PostMapping
	public ResponseEntity<Result<String>> sendMail(){
		emailUtils.sendSimpleEmail("loviusc@gmail.com", "測試", "測試中");
		return ResponseEntity.ok(new Result<String>(HttpStatus.ok,"發送email"));
	}
	
	
}
