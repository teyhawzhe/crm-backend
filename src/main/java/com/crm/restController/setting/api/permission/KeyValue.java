package com.crm.restController.setting.api.permission;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KeyValue implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4186848627650659563L;
	private String key;
	private String value;

}
