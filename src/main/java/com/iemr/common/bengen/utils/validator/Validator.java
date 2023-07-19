package com.iemr.common.bengen.utils.validator;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import com.iemr.common.bengen.utils.config.ConfigProperties;
import com.iemr.common.bengen.utils.exception.IEMRException;
import com.iemr.common.bengen.utils.redis.RedisSessionException;
import com.iemr.common.bengen.utils.sessionobject.SessionObject;

@Service
public class Validator
{
	// private static SessionObject session;

	private SessionObject session;

	@Autowired(required = true)
	@Required
	public void setSessionObject(SessionObject sessionObject)
	{
		this.session = sessionObject;
	}

	// private static void setSessionObject() {
	// if (session == null) {
	// session = new SessionObject();
	// }
	// }

	private static Boolean enableIPValidation = false;

	public Validator()
	{
		if (!enableIPValidation)
		{
			enableIPValidation = ConfigProperties.getBoolean("enableIPValidation");
		}
	}

	private Logger logger = LoggerFactory.getLogger(Validator.class);

	public JSONObject updateCacheObj(JSONObject responseObj, String key, String ipKey)
	{
		try
		{
			Boolean loggedFromDifferentIP = false;
			String loginKey = key;
			String status = "login failed";
			try
			{
				responseObj.put("sessionStatus", "session creation failed");
				String sessionData = session.getSessionObject(key);
				if (enableIPValidation)
				{
					if (sessionData != null && sessionData.trim().length() > 0)
					{
						JSONObject sessionObj = new JSONObject(sessionData);
						if (!sessionObj.getString("loginIPAddress").equals(responseObj.getString("loginIPAddress")))
						{
							loggedFromDifferentIP = true;
							status = "login success, but user logged in from " + sessionObj.getString("loginIPAddress");
							logger.error("Logged in IP : " + sessionObj.getString("loginIPAddress") + "\tRequest IP : "
									+ responseObj.getString("loginIPAddress"));
						}
					}
				}
			} catch (RedisSessionException e)
			{
				logger.error("Session validation failed with exception", e);
			}
			if (!loggedFromDifferentIP)
			{
				status = "login success";
				session.setSessionObject(key, responseObj.toString());
			} else
			{
				responseObj = new JSONObject();
			}
			responseObj.put("key", loginKey);
			responseObj.put("sessionStatus", status);
		} catch (RedisSessionException | JSONException e)
		{
			logger.error("Session validation failed with exception", e);
		}
		return responseObj;
	}

	public String getSessionObject(String key) throws RedisSessionException
	{
		return session.getSessionObject(key);
	}

	public void checkKeyExists(String loginKey, String ipAddress) throws IEMRException
	{
		try
		{
			String sessionString = session.getSessionObject(loginKey);
			JSONObject sessionObj = new JSONObject(sessionString);
			if (enableIPValidation)
			{
				if (!sessionObj.getString("loginIPAddress").equals(ipAddress))
				{
					logger.error(
							"Logged in IP : " + sessionObj.getString("loginIPAddress") + "\tRequest IP : " + ipAddress);
					throw new Exception();
				}
			}
		} catch (Exception e)
		{
			throw new IEMRException("Invalid login key or session is expired");
		}
	}
}
