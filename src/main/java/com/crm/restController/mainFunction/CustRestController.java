package com.crm.restController.mainFunction;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
import com.crm.common.Result;
import com.crm.model.converter.CustConverter;
import com.crm.model.dto.CustDto;
import com.crm.model.entity.TbCust;
import com.crm.model.entity.TbCustPK;
import com.crm.model.queryForm.CustForm;
import com.crm.service.CustService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/cust")
@Slf4j
public class CustRestController {

	@Autowired
	private CustService custService;
	
	@Autowired
	private CustConverter custConverter;
	
	@PreAuthorize("hasAuthority('ADMIN') OR hasAuthority('USER')")
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
		tbCustPK.setCustId(String.valueOf(custService.getSequence()));
		tbCust.setId(tbCustPK);
		
		TbCust tbCustResult;
		try {
			tbCustResult = custService.saveAndGetPk(tbCust);
			log.info(tbCustResult.getId().getCustId());
			return ResponseEntity.ok(new Result<String>(HttpStatus.ok,tbCustResult.getId().getCustId()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("新增失敗!");
			//return ResponseEntity.ok(new Result<String>(HttpStatus.ok,e.getMessage()));
		}
		
	}

	@PreAuthorize("hasAuthority('ADMIN') OR hasAuthority('USER')")
	@GetMapping("/list")
	public ResponseEntity<?> getPage(CustForm custForm , @RequestParam(defaultValue = "1" , name = "currentPage") int pageNum , @RequestParam(defaultValue = "10") int pageSize ){
	
		List<CustDto> tbCustDtoList = new ArrayList<CustDto>();
		
		log.info("custForm ==> " + custForm.toString());
		log.info("pageNum ==> " + pageNum);
		log.info("pageSize ==> " + pageSize);
		PageRequest pageRequest = PageRequest.of(pageNum-1, pageSize);
				
		PageResult<List<TbCust>> page = custService.getList(custForm, pageRequest);
		
		for(TbCust index : page.getData() ) {
			CustDto dto = custConverter.convert(index);
			tbCustDtoList.add(dto);
		}
		
		PageResult<List<CustDto>> pr = new PageResult<List<CustDto>>();
		
		pr.setCurrent(page.getCurrent());
		pr.setStatus(page.getStatus());
		pr.setMessage(page.getMessage());
		pr.setData(tbCustDtoList);
		pr.setTotal(page.getTotal());
		pr.setPageSize(page.getPageSize());
		return ResponseEntity.ok(pr);
	
	}
	
	
	
}
