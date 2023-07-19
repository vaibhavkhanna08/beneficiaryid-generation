package com.iemr.common.bengen.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.annotations.Expose;

import lombok.Data;

@Data
@Entity
@Table(name="m_beneficiaryregidmapping")
public class M_BeneficiaryRegidMapping {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Expose
	private Long benRegId;
	
	@Expose
	private Long beneficiaryId;
	
	private Boolean provisioned;
	
	private Boolean reserved;
	
	private Integer vanID;
	
	@Expose
	private Timestamp createdDate;
	
	@Expose
	private String createdBy;
	
	@Transient
	private Long benIDRequired;
	
	@Override
	public String toString() {

		return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setLongSerializationPolicy(LongSerializationPolicy.STRING).serializeNulls()
				.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create().toJson(this);
	}

	public M_BeneficiaryRegidMapping(Long benRegId, Long beneficiaryId, Timestamp createdDate, String createdBy) {
		this.benRegId=benRegId;
		this.beneficiaryId=beneficiaryId;
		this.createdDate=createdDate;
		this.createdBy=createdBy;
	}
}
