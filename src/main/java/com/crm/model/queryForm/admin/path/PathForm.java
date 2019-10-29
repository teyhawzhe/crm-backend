package com.crm.model.queryForm.admin.path;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
@ToString
public class PathForm implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = -5344297541513534010L;

	@NotBlank(message = "目錄不能為NULL")
	private String path;
	@NotBlank(message = "標題不能為NULL")
	private String title;
	@NotBlank(message = "圖標不能為NULL")
	private String icon;

	private int tier;
	
	private String parent;
	
	private String oldPath;
	private String oldTitle;
	private String oldIcon;
	private String oldParent;
	
}
