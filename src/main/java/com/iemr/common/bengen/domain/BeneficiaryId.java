package com.iemr.common.bengen.domain;

import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="m_beneficiaryregidmapping")
public class BeneficiaryId {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private BigInteger benRegId; 
	private BigInteger beneficiaryId;
	private Boolean provisioned;
	private Integer provisionedById;
	private String provisionedBy;
	private Timestamp provisionedOn;
	private Boolean reserved;
	
	private Integer reservedForId;
	@Column(length=45)
	private String reservedForName;
	
	private Integer reservedForCountryId;
	@Column(length=45)
	private String reservedForCountryName;
	
	private Integer reservedForStateId;
	@Column(length=45)
	private String reservedForStateName;
	
	private Integer reservedForDistrictId;
	@Column(length=45)
	private String reservedForDistrictName;
	
	private Integer reservedForPSMapId;
	@Column(length=45)
	private String reservedForPSMapName;
	
	private Integer reservedById;
	@Column(length=45)
	private String reservedByName;
	
	private Timestamp reservedOn;
	private Timestamp reservedUntil;
	private String createdBy;
	private Timestamp createdDate;
	
	// 753812721192, 796702837334, b'0', b'0',,,,,,,,admin,0
}
