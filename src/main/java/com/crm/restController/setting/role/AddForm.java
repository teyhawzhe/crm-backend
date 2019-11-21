package com.crm.restController.setting.role;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.crm.model.entity.SettingRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class AddForm implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -287128745484425653L;
	@NotBlank(message = "請輸入權限角色!")
	private String role;
	@NotBlank(message = "請輸入敘述!")
	private String def;
	//@NotBlank(message = "請選擇狀態!")
	private boolean status;
}
