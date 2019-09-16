package com.crm.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * The primary key class for the TB_CUST database table.
 * 
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class TbCustPK implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6986527099687427109L;
	
	@Column(name="CUST_ID")
	private String custId;

	@Column(name="REGION_ID")
	private String regionId;

}