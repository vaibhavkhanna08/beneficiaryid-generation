package com.iemr.common.bengen.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.iemr.common.bengen.domain.M_BeneficiaryRegidMapping;
import com.iemr.common.bengen.repo.BeneficiaryIdRepo;
import com.iemr.common.bengen.utils.Generator;
import com.iemr.common.bengen.utils.config.ConfigProperties;

@Service
public class GenerateBeneficiaryService  {
	private static final Logger logger = LoggerFactory.getLogger(GenerateBeneficiaryService.class);
	private ExecutorService executor = Executors.newCachedThreadPool();
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	BeneficiaryIdRepo beneficiaryIdRepo;

	public void generateBeneficiaryIDs() throws Exception {
		logger.info("BengenApplication.run start");
		long strt = System.currentTimeMillis();
		
		//for (int i = 0; i < 10; i++) {
			executor.submit(() -> {
				logger.info("Running: " + Thread.currentThread().getName());
				createFile();
			});
			
			/*executor.submit(() -> {
				logger.info("Running: " + Thread.currentThread().getName());
				createFile();
			});
			
			executor.submit(() -> {
				logger.info("Running: " + Thread.currentThread().getName());
				createFile();
			});*/																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																	
		//}	
		
		long fin = System.currentTimeMillis() - strt;
		logger.info("BengenApplication.run finish. time = " + fin + " ms.");
	}
	
	public void createFile() {
		logger.info("BengenApplication.createFile start");
		long strt = System.currentTimeMillis();
		
		try {
			File file = File.createTempFile(""+System.currentTimeMillis(), ".csv");
			logger.info("File: " + file.getAbsolutePath());
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			Integer bentobeGenerate = ConfigProperties.getInteger("no-of-benID-to-be-generate");
			bw.write(createQuery(bentobeGenerate).toString());
			bw.flush();
			bw.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		long fin = System.currentTimeMillis() - strt;
		logger.info("BengenApplication.createFile finish. time = " + fin + " ms.");
	}
	
	public StringBuffer createQuery(Integer num) {
		logger.info("BengenApplication.createQuery start");
		long strt = System.currentTimeMillis();
		
		Generator g = new Generator();	
		StringBuffer sb = new StringBuffer("INSERT INTO `db_identity`.`m_beneficiaryregidmapping` (`BeneficiaryID`,`Provisioned`,`Deleted`,`CreatedDate`,`CreatedBy`) VALUES ");
		
		// INSERT INTO `db_identity`.`m_beneficiaryregidmapping` (`BeneficiaryID`,`Provisioned`,`Deleted`,`CreatedDate`,`CreatedBy`) VALUES
		// (<{BeneficiaryID: }>, <{Provisioned: b'0'}>, <{Deleted: b'0'}>, <{CreatedDate: CURRENT_TIMESTAMP}>, <{CreatedBy: }>);
		
		Timestamp ts = Timestamp.from(Instant.now());
		
		for (int i=0; i < num; i++) {
			sb.append("( ");
			sb.append(g.generateBeneficiaryId()).append(",")
				.append("b'0'").append(",")
				.append("b'0'").append(",")
				.append("'").append(ts).append("',")
				.append("'admin-batch'").append("");
			sb.append(" ), ");
		}

		sb.deleteCharAt(sb.lastIndexOf(","));
		
		jdbcTemplate.execute(sb.toString());
		
		long fin = System.currentTimeMillis() - strt;
		logger.info("BengenApplication.createQuery finish. time = " + fin + " ms.");
		
		return sb;
	}
	
	public void testLoopGenr() {
		List<String> strList = new ArrayList<String>();
		StringBuffer sb = new StringBuffer("INSERT INTO `db_identity`.`m_beneficiaryregidmapping` (`BeneficiaryID`,`Provisioned`,`Deleted`,`CreatedDate`,`CreatedBy`) VALUES ");
		Timestamp ts = Timestamp.from(Instant.now());
		Generator g = new Generator();
		sb.append("( ").append(g.generateBeneficiaryId()).append(",")
			.append("N").append(",").append("0").append(",")
			.append(ts).append(",").append("admin-batch").append(") ");
		
		strList.add(sb.toString());
	}
	
	public void testPMDAvoidGenr() {
		
	}
	public List<M_BeneficiaryRegidMapping> getBeneficiaryIDs(Long num, Integer vanID)
	{
		logger.info("getBeneficiaryIDs start");
		long strt = System.currentTimeMillis();
		
		Generator g = new Generator();	
		StringBuffer sb = new StringBuffer("INSERT INTO `db_identity`.`m_beneficiaryregidmapping` (`BeneficiaryID`,`Provisioned`,`Deleted`,`Reserved`,`CreatedDate`,`CreatedBy`,`VanID`) VALUES ");
		
		Timestamp ts = Timestamp.from(Instant.now());
		List<M_BeneficiaryRegidMapping> list = new ArrayList<M_BeneficiaryRegidMapping>();
		
		for (int i=0; i < num; i++) {
			sb.append("( ");
			sb.append(g.generateBeneficiaryId()).append(",")
				.append("b'0'").append(",")
				.append("b'0'").append(",")
				.append("b'1'").append(",")
				.append("'").append(ts).append("',")
				.append("'admin-batch'").append(",")
				.append(vanID).append("");
			sb.append(" ), ");
			
		}

		sb.deleteCharAt(sb.lastIndexOf(","));
		
		jdbcTemplate.execute(sb.toString());
		
		List<Objects[]> result=null;
		logger.info("ts result1 = " + ts );
		result = beneficiaryIdRepo.getBenIDGenerated(vanID, num);
		for (Object[] objects : result)
		{
			if (objects != null && objects.length > 0)
			{
				list.add(new M_BeneficiaryRegidMapping(((Number) objects[0]).longValue(), ((Number) objects[1]).longValue(), (Timestamp) objects[2],"admin-batch"));
			}
			
		}
		long fin = System.currentTimeMillis() - strt;
		logger.info("getBeneficiaryIDs finish. time = " + fin + " ms.");
		
		return list;
		
	}
	
}
