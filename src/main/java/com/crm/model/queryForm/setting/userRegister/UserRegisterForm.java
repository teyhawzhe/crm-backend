package com.crm.model.queryForm.setting.userRegister;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRegisterForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5131826568290609484L;

	@NotBlank(message = "請輸入姓名")
	@Size(max = 10 , min=2 ,message = "字元度在2到10之間")
	private String name;
	
	@NotBlank(message = "請輸入帳號")
	@Size(max = 10 , min=5 ,message = "字元度在5到10之間")
	private String username;
	
	@Size(min=1,message = "請選擇至少一個權限")
	private List<String> roles;
	
	@NotBlank(message = "請選擇大頭")
	private String image;
	
}
