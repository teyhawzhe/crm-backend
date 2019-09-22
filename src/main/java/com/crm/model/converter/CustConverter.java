package com.crm.model.converter;

import org.apache.commons.lang3.StringUtils;
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
		
		if(StringUtils.isBlank(source.getCustId())) {
			source.setCustId(source.getId().getCustId());
		}
		if(StringUtils.isBlank(source.getRegionId())) {
			source.setRegionId(source.getId().getRegionId());
		}
		
		
		return model.map(source,CustDto.class);
	}

}
