package com.crm.model.queryForm.admin.path;

import java.io.Serializable;
import java.util.List;

import com.crm.model.entity.SettingPath;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class PathFormList implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 430179125393132225L;
	private List<SettingPath> list;
}
