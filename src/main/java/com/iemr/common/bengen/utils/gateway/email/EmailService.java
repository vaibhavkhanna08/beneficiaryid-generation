package com.iemr.common.bengen.utils.gateway.email;

public interface EmailService
{
	void sendEmail(String jsonObject, String template);

	void sendEmailWithAttachment(String jsonObject, String template);

	void sendEmail(String jsonObject);
}
