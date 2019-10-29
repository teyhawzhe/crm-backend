package com.crm.restController.cust;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
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
import com.crm.common.Result;
import com.crm.model.converter.CustConverter;
import com.crm.model.dto.cust.CustDto;
import com.crm.model.entity.TbCust;
import com.crm.model.queryForm.cust.CustForm;
import com.crm.model.queryForm.cust.CustUpdateForm;
import com.crm.service.cust.CustService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/cust")
@Slf4j
public class CustRestController {

	@Autowired
	private CustService custService;

	@Autowired
	private CustConverter custConverter;

	@PreAuthorize("hasAuthority('USER')")
	@PostMapping("/add")
	public ResponseEntity<Result<String>> save(@RequestBody @Valid TbCust tbCust, BindingResult br) throws Exception {

		if (br.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			for (ObjectError index : br.getAllErrors()) {
				sb.append(index.getDefaultMessage() + "<br />");
			}
			return ResponseEntity.ok(new Result<String>(HttpStatus.valid, null, sb.toString()));
		}

		long checkPersonID = custService.checkPersonIdByPersonId(tbCust.getPersonId());
		if (checkPersonID > 0) {
			return ResponseEntity.ok(new Result<String>(HttpStatus.valid, null, "身分證已存在!"));
		}

		try {
			TbCust tbCustResult = custService.saveAndGetPk(tbCust);
			log.info(tbCustResult.getId().getCustId());
			return ResponseEntity.ok(new Result<String>(HttpStatus.ok, tbCustResult.getId().getCustId()));
		} catch (Exception e) {
			throw new Exception("新增失敗!");
		}

	}

	@PreAuthorize("hasAuthority('USER')")
	@GetMapping(value = "/list")
	public ResponseEntity<?> list(CustForm custForm, @RequestParam(defaultValue = "1") int current,
			@RequestParam(defaultValue = "10") int pageSize) {

		List<CustDto> custDtoList = new ArrayList<CustDto>();

		PageRequest pageRequest = PageRequest.of(current - 1, pageSize);

		Page<TbCust> page = custService.getList(custForm, pageRequest);

		for (TbCust index : page.getContent()) {
			CustDto dto = custConverter.convert(index);
			custDtoList.add(dto);
		}

		PageResult<List<CustDto>> pr = new PageResult<List<CustDto>>();

		if (page.getContent().size() == 0) {
			pr.setStatus(HttpStatus.empty);
			pr.setMessage("查無資料!");
		} else {
			pr.setStatus(HttpStatus.ok);
			pr.setMessage("查詢成功!");
			pr.setData(custDtoList);
			pr.setCurrent(page.getNumber());
			pr.setTotal(page.getTotalElements());
			pr.setPageSize(page.getSize());
		}

		return ResponseEntity.ok(pr);
	}

	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/lists")
	public ResponseEntity<?> lists(CustForm custForm, @RequestParam(defaultValue = "1") int current,
			@RequestParam(defaultValue = "10") int pageSize) {
	
		PageRequest pageRequest = PageRequest.of(current - 1, pageSize);
		List<CustDto> custDtoList = new ArrayList<CustDto>();

		PageResult<List<TbCust>> page = custService.getSql(custForm, pageRequest);

		if (null != page.getData()) {
			for (TbCust index : page.getData()) {
				CustDto dto = custConverter.convert(index);
				custDtoList.add(dto);
			}
		}
		PageResult<List<CustDto>> pr = new PageResult<List<CustDto>>();

		if (null == page.getData()) {
			pr.setStatus(HttpStatus.empty);
			pr.setMessage("查無資料!");
		} else {
			pr.setStatus(HttpStatus.ok);
			pr.setMessage("查詢成功!");
			pr.setData(custDtoList);
			pr.setCurrent(page.getCurrent());
			pr.setTotal(page.getTotal());
			pr.setPageSize(page.getPageSize());
		}
		return ResponseEntity.ok(pr);
	}
	
	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/listm")
	public ResponseEntity<?> listm(CustForm custForm, @RequestParam(defaultValue = "1") int current,
			@RequestParam(defaultValue = "10") int pageSize) {
	
		PageRequest pageRequest = PageRequest.of(current - 1, pageSize);

		PageResult<List<Map<String, Object>>> page = custService.getSqlMap(custForm, pageRequest);
	
		return ResponseEntity.ok(page);
	}
	
	@PreAuthorize("hasAuthority('USER')")
	@GetMapping(value = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<?> xml(CustForm custForm, @RequestParam(defaultValue = "1") int current,
			@RequestParam(defaultValue = "10") int pageSize) {
	
		PageRequest pageRequest = PageRequest.of(current - 1, pageSize);
		List<CustDto> custDtoList = new ArrayList<CustDto>();

		PageResult<List<TbCust>> page = custService.getSql(custForm, pageRequest);

		if (null != page.getData()) {
			for (TbCust index : page.getData()) {
				log.info("index=" + index.toString());
				CustDto dto = custConverter.convert(index);
				custDtoList.add(dto);
			}
		}
		PageResult<List<CustDto>> pr = new PageResult<List<CustDto>>();

		if (null == page.getData()) {
			pr.setStatus(HttpStatus.empty);
			pr.setMessage("查無資料!");
		} else {
			pr.setStatus(HttpStatus.ok);
			pr.setMessage("查詢成功!");
			pr.setData(custDtoList);
			pr.setCurrent(page.getCurrent());
			pr.setTotal(page.getTotal());
			pr.setPageSize(page.getPageSize());
		}
		return ResponseEntity.ok(pr);
	}

	@PreAuthorize("hasAuthority('USER')")
	@PostMapping("/modify")
	public ResponseEntity<?> modify(@RequestBody @Valid CustUpdateForm custUpdateForm, BindingResult br) {
		log.info("modify =>" + custUpdateForm.toString());
		if (br.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			for (ObjectError index : br.getAllErrors()) {
				sb.append(index.getDefaultMessage() + "<br />");
			}
			return ResponseEntity.ok(new Result<String>(HttpStatus.valid, null,sb.toString()));
		}

		long checkPersonID = custService.checkPersonIdByPersonIdNotIn(custUpdateForm.getPersonId(),custUpdateForm.getCustId(),custUpdateForm.getRegionId());
		if (checkPersonID > 0) {
			return ResponseEntity.ok(new Result<String>(HttpStatus.valid, null,"身分證已存在!"));
		}

		TbCust cust = custService.update(custUpdateForm);
		log.info("cust = " + cust.toString());
		return ResponseEntity.ok(new Result<String>(HttpStatus.ok,null,"更新成功!"));
	}

}
