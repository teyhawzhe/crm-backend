package com.crm.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class TbPath implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8606648692914694053L;

	@EmbeddedId
	private TbPathPk id;
	private String title;
	private String icon;
	@Column(insertable = false)
	private int sort;
	private String parent;
	private String pathstr;
	
}
