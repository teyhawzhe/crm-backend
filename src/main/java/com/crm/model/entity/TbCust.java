package com.crm.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * The persistent class for the TB_CUST database table.
 * 
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class TbCust implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4881882013948655951L;

	@Transient
	private String custId;

	@Transient
	private String regionId;

	@Transient
	private String custLevelName;
	
	@Transient
	private String regionIdName;
	
	@EmbeddedId
	private TbCustPK id;

	private String address;

	@Column(name="ADDRESS_CITY")
	private String addressCity;

	@Column(name="ADDRESS_REGION")
	private String addressRegion;

	@Column(name="ADDRESS_TOWNSHIP")
	private String addressTownship;

	@Column(name="ADDRESS_TRACK")
	private String addressTrack;

	@Column(name="ANNUAL_INCOME")
	private String annualIncome;

	@Column(name="APRV_DATE")
	private String aprvDate;

	@Column(name="APRV_TIME")
	private String aprvTime;

	@Column(name="APRV_USERID")
	private String aprvUserid;

	@Column(name="BILL_CUT_DAY")
	private String billCutDay;

	@Column(name="BILL_VALID_DATE")
	private String billValidDate;

	private String birthday;

	private String contact;

	@Column(name="CREATE_DATE")
	private String createDate;

	@Column(name="CREATE_TIME")
	private String createTime;

	@Column(name="CREATE_USERID")
	private String createUserid;

	@Column(name="CUST_LEVEL")
	private String custLevel;

	@Column(name="DATA_COMPLETE_DATE")
	private String dataCompleteDate;

	@Column(name="DATA_COMPLETE_FLAG")
	private String dataCompleteFlag;

	@Column(name="DATA_COMPLETE_MERCH")
	private String dataCompleteMerch;

	private String education;

	private String email;

	@Column(name="EMAIL_TRACK")
	private String emailTrack;

	@Column(name="ENG_NAME")
	private String engName;

	@Column(name="FAMILY_NUM")
	private String familyNum;

	@Column(name="FAST_ISSUE")
	private String fastIssue;

	private String gender;

	@Column(name="IMP_DATE")
	private String impDate;

	@Column(name="IMP_FILE_NAME")
	private String impFileName;

	private String industry;

	@Column(name="LOC_NAME")
	private String locName;

	private String marriage;

	@Column(name="MEM_DAY")
	private String memDay;

	private String memo;

	private String mobile;

	@Column(name="MOBILE_TRACK")
	private String mobileTrack;

	@Column(name="NEW_BILL_CUT_DAY")
	private String newBillCutDay;

	private String occupation;

	@Column(name="PERSON_CODE")
	private String personCode;

	@Size(min = 10 , max = 10 , message = "身分證長度只能有10個字元")
	@Column(name="PERSON_ID" , unique = true)
	private String personId;

	@Column(name="PERSON_TYPE")
	private String personType;

	@Column(name="REAL_DATA_COMPLETE_DATE")
	private String realDataCompleteDate;

	@Column(name="REAL_DATA_COMPLETE_MERCH")
	private String realDataCompleteMerch;

	@Column(name="RECMD_PERSON_CODE")
	private String recmdPersonCode;

	@Column(name="RECMD_PERSON_TYPE")
	private String recmdPersonType;

	@Column(name="RLD_LIMIT_FLAG")
	private String rldLimitFlag;

	@Column(name="SALE_CODE")
	private String saleCode;

	private String signature;

	private String status;

	@Column(name="TEL_HOME")
	private String telHome;

	@Column(name="TEL_HOME_TRACK")
	private String telHomeTrack;

	@Column(name="TEL_HOME_ZONE")
	private String telHomeZone;

	@Column(name="TEL_OFFICE")
	private String telOffice;

	@Column(name="TEL_OFFICE_EXT")
	private String telOfficeExt;

	@Column(name="TEL_OFFICE_ZONE")
	private String telOfficeZone;

	private String ud1;

	private String ud2;

	private String ud3;

	private String ud4;

	private String ud5;

	@Column(name="UPT_DATE")
	private String uptDate;

	@Column(name="UPT_TIME")
	private String uptTime;

	@Column(name="UPT_USERID")
	private String uptUserid;

	@Column(name="VIP_FLAG")
	private String vipFlag;

	@Column(name="VIP_VALID_DATE")
	private String vipValidDate;

	@Column(name="ZIP_CODE")
	private String zipCode;

}