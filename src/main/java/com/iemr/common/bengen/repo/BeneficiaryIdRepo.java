package com.iemr.common.bengen.repo;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iemr.common.bengen.domain.BeneficiaryId;

@Repository
public interface BeneficiaryIdRepo extends CrudRepository<BeneficiaryId, BigInteger> {
	List<BeneficiaryId> findByReserved(Boolean isReserved);
	List<BeneficiaryId> findByReservedAndReservedUntilBetween(Boolean isReserved, Timestamp fromDate, Timestamp toDate);
	
	List<BeneficiaryId> findByCreatedDateBetween(Timestamp fromDate, Timestamp toDate);
	List<BeneficiaryId> findByProvisionedAndReserved(Boolean isprovisioned, Boolean isReserved);
	
	BeneficiaryId findFirstByProvisionedAndReserved(Boolean isprovisioned, Boolean isReserved);
	BeneficiaryId findFirstByCreatedDateBetween(Timestamp fromDate, Timestamp toDate);
	
	//Long countBybeneficiaryId();
	Long countByReserved(Boolean isReserved);
	Long countByReservedAndReservedUntilBetween(Boolean isReserved, Timestamp fromDate, Timestamp toDate);
	Long countByProvisionedAndReserved(Boolean isprovisioned, Boolean isReserved);
	Long countByReservedForCountryId(Integer countryId);
	Long countByReservedForCountryIdAndProvisionedAndReserved(Integer countryId, Boolean isprovisioned, Boolean isReserved);
	Long countByReservedForPSMapIdAndReservedForCountryIdAndProvisionedAndReserved(Integer psMapId, Integer countryId, Boolean isprovisioned, Boolean isReserved);
	Long countByReservedForStateId(Integer stateId);
	Long countByReservedForStateIdAndProvisionedAndReserved(Integer stateId, Boolean isprovisioned, Boolean isReserved);
	Long countByReservedForPSMapIdAndReservedForStateIdAndProvisionedAndReserved(Integer psMapId, Integer stateId, Boolean isprovisioned, Boolean isReserved);
	Long countByReservedForDistrictId(Integer districtId);
	Long countByReservedForDistrictIdAndProvisionedAndReserved(Integer districtId, Boolean isprovisioned, Boolean isReserved);
	Long countByReservedForPSMapIdAndReservedForDistrictIdAndProvisionedAndReserved(Integer psMapId, Integer districtId, Boolean isprovisioned, Boolean isReserved);
	Long countByReservedForPSMapId(Integer psMapId);
	Long countByReservedForPSMapIdAndProvisionedAndReserved(Integer psMapId, Boolean isprovisioned, Boolean isReserved);	
	
		@Query(" SELECT count(*) from M_BeneficiaryRegidMapping benregMap "
			  + " WHERE benregMap.provisioned =false and benregMap.reserved =false")
		Long countBenID();

		@Query(nativeQuery = true, value = "Select benregMap.benRegId, benregMap.beneficiaryId, benregMap.CreatedDate "
				+ "from db_identity.m_beneficiaryregidmapping  benregMap "
				+ "where benregMap.provisioned =false and benregMap.reserved =true and benregMap.vanID=:vanID order by benregMap.benRegId desc limit :num ")
		List<Objects[]> getBenIDGenerated(@Param("vanID") Integer vanID, @Param("num") Long num);
}
