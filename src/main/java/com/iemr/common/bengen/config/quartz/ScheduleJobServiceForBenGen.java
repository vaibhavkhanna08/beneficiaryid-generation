package com.iemr.common.bengen.config.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.common.bengen.repo.BeneficiaryIdRepo;
import com.iemr.common.bengen.service.GenerateBeneficiaryService;
import com.iemr.common.bengen.utils.config.ConfigProperties;

@Service
@Transactional
public class ScheduleJobServiceForBenGen implements Job
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	BeneficiaryIdRepo beneficiaryIdRepo;
	
	@Autowired
	GenerateBeneficiaryService generateBeneficiaryService;


	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		logger.info("Started job " + arg0.getClass().getName());
		try 
		{
		Long totalBenID= beneficiaryIdRepo.countBenID();
		logger.info("Total Beneficiary ID available -> " + totalBenID);
		Integer lowerLimitOfBen = ConfigProperties.getInteger("lower-limit-of-beneficiary");
		if(totalBenID<lowerLimitOfBen)
		{
			generateBeneficiaryService.generateBeneficiaryIDs();
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Completed job " + arg0.getClass().getName());
	}

}
