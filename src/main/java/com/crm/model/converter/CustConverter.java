package com.crm.model.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.crm.common.AbstractModelMapper;
import com.crm.model.dto.CustDto;
import com.crm.model.entity.TbCust;

@Component
public class CustConverter extends AbstractModelMapper<TbCust, CustDto> {

	@Override
	public CustDto doConvert(TbCust source) {
		// TODO Auto-generated method stub
		ModelMapper model = new ModelMapper();
		return model.map(source,CustDto.class);
	}

}
