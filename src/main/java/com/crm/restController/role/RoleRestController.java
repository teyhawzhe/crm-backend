package com.crm.restController.role;

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
import org.springframework.web.bind.annotation.RestController;

import com.crm.common.HttpStatus;
import com.crm.common.Result;
import com.crm.model.entity.TbRole;
import com.crm.service.role.RoleService;

@RestController
@RequestMapping("/role")
public class RoleRestController {

	@Autowired
	private RoleService roleService;

	@PostMapping("/add")
	public ResponseEntity<Result<String>> add(@RequestBody @Valid RoleForm roleForm, BindingResult br)
			throws Exception {
		if (br.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			for (ObjectError index : br.getAllErrors()) {
				sb.append(index.getDefaultMessage() + "<br />");
			}
			return ResponseEntity.ok(new Result<String>(HttpStatus.valid, null, sb.toString()));
		}

		roleService.save(roleForm);
		return ResponseEntity.ok(new Result(HttpStatus.ok, "新增成功!"));

	}
	
	public ResponseEntity<Result<String>> update(@RequestBody @Valid RoleForm roleForm, BindingResult br)
			throws Exception {
		if (br.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			for (ObjectError index : br.getAllErrors()) {
				sb.append(index.getDefaultMessage() + "<br />");
			}
			return ResponseEntity.ok(new Result<String>(HttpStatus.valid, null, sb.toString()));
		}

		roleService.save(roleForm);
		return ResponseEntity.ok(new Result(HttpStatus.ok, "更新成功!"));

	}

	@GetMapping("/list")
	public ResponseEntity<Result<List<TbRole>>> list() throws Exception {
		List<TbRole> list = roleService.getAllList();
		if (null == list) {
			return ResponseEntity.ok(new Result(HttpStatus.ok, null, "查無資料!"));
		}
		return ResponseEntity.ok(new Result(HttpStatus.ok, list, "查詢成功!"));
	}

}
