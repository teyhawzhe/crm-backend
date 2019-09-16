package com.crm.restController.mainFunction;

import java.util.ArrayList;
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
import com.crm.exception.SqlConditionItemException;
import com.crm.model.converter.CustConverter;
import com.crm.model.dto.CustDto;
import com.crm.model.entity.TbCust;
import com.crm.model.entity.TbCustPK;
import com.crm.model.queryForm.CustForm;
import com.crm.service.TbCustService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/cust")
@Slf4j
public class CustRestController {

	@Autowired
	private TbCustService tbCustService;
	
	@Autowired
	private CustConverter tbCustConverter;
	
	@PostMapping("/add")
	public ResponseEntity<Result<String>> save(@RequestBody @Valid TbCust tbCust , BindingResult br) throws Exception{
		
		log.info("tbCust="+tbCust.toString());
		
		if(br.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			for(ObjectError index : br.getAllErrors()) {
				sb.append(index.getDefaultMessage()+"<br />");
			}
			return ResponseEntity.ok(new Result<String>(HttpStatus.valid,null,sb.toString()));
		}
		
		/*long checkPersonID = tbCustService.checkPersonID(tbCust.getPersonId());
		log.info("checkPersonID="+checkPersonID);
		if( checkPersonID >0 ) {
			return ResponseEntity.ok(new Result<String>(HttpStatus.valid,null,"身分證已存在!"));
		}*/
		 
		TbCustPK tbCustPK = tbCust.getId();
		tbCustPK.setCustId(String.valueOf(tbCustService.getSequence()));
		tbCust.setId(tbCustPK);
		
		TbCust tbCustResult;
		try {
			tbCustResult = tbCustService.saveAndGetPk(tbCust);
			log.info(tbCustResult.getId().getCustId());
			return ResponseEntity.ok(new Result<String>(HttpStatus.ok,tbCustResult.getId().getCustId()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception(e.toString());
			//return ResponseEntity.ok(new Result<String>(HttpStatus.ok,e.getMessage()));
		}
		
	}

	@GetMapping("/list")
	public ResponseEntity<?> getList(CustForm tbCust ) throws SqlConditionItemException{
	
		List<CustDto> tbCustDtoList = new ArrayList<CustDto>();
		
		
		List<TbCust> tbCustList = tbCustService.getList(tbCust);
		
		log.info("tbCustList size = " +tbCustList.size());
		
		log.info("size="+tbCustList.size());
		for(TbCust index : tbCustList) {
			log.info("index = " +index);
			CustDto tbCustDto = tbCustConverter.convert(index);
			tbCustDtoList.add(tbCustDto);
		}
		
		if(tbCustDtoList.size()==0) {
			return ResponseEntity.ok(new Result<String>(HttpStatus.empty,"查無會員資料!"));
		}else{
			return ResponseEntity.ok(new Result<List<CustDto>>(HttpStatus.ok,tbCustDtoList));
		}
		
		
	}
	
}
