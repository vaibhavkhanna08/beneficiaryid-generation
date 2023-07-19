package com.iemr.common.bengen.utils.mapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class OutputMapper {
	static GsonBuilder builder;
	private static OutputMapper instance = null;

	public OutputMapper() {
		if (builder == null) {
			builder = new GsonBuilder();
			builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			builder.excludeFieldsWithoutExposeAnnotation();
			builder.serializeNulls();
		}
	}

	public static OutputMapper getInstance()
	{
		if (instance == null)
		{
			instance = new OutputMapper();
		}

		return instance;
	}
	
	public static Gson gson() {
		return builder.create();
	}
}