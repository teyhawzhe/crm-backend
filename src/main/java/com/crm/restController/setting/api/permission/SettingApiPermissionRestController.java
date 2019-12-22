package com.crm.restController.setting.api.permission;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.crm.common.HttpStatus;
import com.crm.common.Result;
import com.crm.service.setting.api.permission.ApiPermissionService;

import lombok.extern.slf4j.Slf4j;
///apiPermission
@RestController
@RequestMapping("/setting/api/permission")
@Slf4j
public class SettingApiPermissionRestController {
	
private final String []excludePath = {"/authenticate"};
	
	
	private RequestMappingHandlerMapping handlerMap = new RequestMappingHandlerMapping();

	@Autowired
	private ApiPermissionService apiPermissionService;
	
	@GetMapping
	public ResponseEntity<Result<List<KeyValue>>> findAll(){
		
		Map<String,Object> map =new TreeMap<String,Object>();
		
		List<String> exclPathlist = Arrays.asList(excludePath);
		for (RequestMappingInfo key : handlerMap.getHandlerMethods().keySet()) {
		
			if(!key.getMethodsCondition().isEmpty()) {
				///String value = null;
				String k = null;
				/*for(RequestMethod index : key.getMethodsCondition().getMethods()) {
					value = index.toString();
				}*/
				for(String index : key.getPatternsCondition().getPatterns()) {
					k = index;
				}
				if(!exclPathlist.contains(k)) {
					map.put(k,k);
				}
			}
		}
		
		List<KeyValue> list =  new ArrayList<KeyValue>();
		
		for(String index : map.keySet()) {
			KeyValue kv = new KeyValue(index,index);
			list.add(kv);
		}
		return ResponseEntity.ok(new Result<List<KeyValue>>(HttpStatus.ok,"查詢成功!",list));
	}
	
	@PostMapping
	public ResponseEntity<Result<String>> save(@RequestBody @Valid Form form , BindingResult br) throws Exception{
		
		if (br.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			for (ObjectError index : br.getAllErrors()) {
				sb.append(index.getDefaultMessage() + "<br />");
			}
			return ResponseEntity.ok(new Result<String>(HttpStatus.valid,sb.toString()));
		}
		apiPermissionService.save(form);
		return ResponseEntity.ok(new Result<String>(HttpStatus.ok,"新增成功!"));
	}

	
	@GetMapping("/findCheckApi")
	public ResponseEntity<Result<?>> findCheckApi(@Valid Form form , BindingResult br){

		if (br.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			for (ObjectError index : br.getAllErrors()) {
				sb.append(index.getDefaultMessage() + "<br />");
			}
			return ResponseEntity.ok(new Result<String>(HttpStatus.valid,sb.toString()));
		}
		List<String> list = apiPermissionService.findAllByRole(form);
		return ResponseEntity.ok(new Result<List<String>>(HttpStatus.ok,"查詢成功!",list));
	}
}
