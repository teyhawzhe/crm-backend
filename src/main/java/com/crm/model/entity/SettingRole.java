package com.crm.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class SettingRole implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6541070397009002254L;

	@Id
	private String role;
	private String def;
	private boolean status;
}
