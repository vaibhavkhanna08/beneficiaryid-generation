package com.iemr.common.bengen.controller;

import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.reflect.TypeToken;
import com.iemr.common.bengen.domain.M_BeneficiaryRegidMapping;
import com.iemr.common.bengen.service.GenerateBeneficiaryService;
import com.iemr.common.bengen.utils.OutputResponse;
import com.iemr.common.bengen.utils.mapper.InputMapper;
import com.iemr.common.bengen.utils.mapper.OutputMapper;

import io.swagger.annotations.ApiParam;

@RequestMapping("/generateBeneficiaryController")
@RestController
public class GenerateBeneficiaryController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	@Autowired
	GenerateBeneficiaryService generateBeneficiaryService;
	
	@CrossOrigin()
	@RequestMapping(value = "/generateBeneficiaryIDs", headers = "Authorization", method = {
			RequestMethod.POST }, produces = { "application/json" })
	public String getBeneficiaryIDs(@ApiParam("{\"benIDRequired\":\"Integer\",\"vanID\":\"Integer\"}") @RequestBody String request, HttpServletRequest httpRequest)
	{
		logger.info("generateBeneficiaryIDs request "+request.toString());
			
			M_BeneficiaryRegidMapping benMapping= InputMapper.gson().fromJson(request, M_BeneficiaryRegidMapping.class);
			List<M_BeneficiaryRegidMapping> list = generateBeneficiaryService.getBeneficiaryIDs(benMapping.getBenIDRequired(), benMapping.getVanID());
			String response = getSuccessResponseString(list, 200, "success", "generateBeneficiaryIDs");
			logger.info("generateBeneficiaryIDs response "+response.toString());
		/**
		 * sending the response...
		 */
		return response;
	}
	
	private String getSuccessResponseString(List<M_BeneficiaryRegidMapping> list, Integer statusCode, String statusMsg,
			String methodName)
	{
		Type typeOfSrc = new TypeToken<List<M_BeneficiaryRegidMapping>>()
			{
				private static final long serialVersionUID = 1L;
			}.getType();
		String data = OutputMapper.getInstance().gson().toJson(list, typeOfSrc);
		logger.info("data: " + data);
		logger.info("data.toStr: " + data.toString());
		OutputResponse response = new OutputResponse.Builder().setDataJsonType("JsonObject.class")
				.setStatusCode(statusCode).setStatusMessage(statusMsg)
				.setDataObjectType(this.getClass().getSimpleName()).setMethodName(methodName).setData(data).build();
		return response.toString();
	}
}
