package com.iemr.common.bengen.utils.http;

import java.util.HashMap;

import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpUtils {
	public static final String AUTHORIZATION = "Authorization";
	private String server;
	// @Autowired
	private RestTemplate rest;
	// @Autowired
	private HttpHeaders headers;
	// @Autowired
	private HttpStatus status;

	// @Autowired(required = true)
	// @Qualifier("hibernateCriteriaBuilder")
	public HttpUtils() {
		if (rest == null) {
			rest = new RestTemplate();
			headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
		}
	}
	// public HttpUtils() {
	// if (rest == null) {
	// rest = new RestTemplate();
	// headers = new HttpHeaders();
	// headers.add("Content-Type", "application/json");
	// }
	// }

	// @Bean
	// public HttpUtils httpUtils() {
	// return new HttpUtils();
	// }

	public String get(String uri) {
		String body;
		HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
		ResponseEntity<String> responseEntity = rest.exchange(uri, HttpMethod.GET, requestEntity, String.class);
		setStatus(responseEntity.getStatusCode());
		// if (status == HttpStatus.OK){
		body = responseEntity.getBody();
		// }else{
		// responseEntity
		// }
		return body;
	}

	public String get(String uri, HashMap<String, Object> header) {
		String body;
		HttpHeaders headers = new HttpHeaders();
		if (header.containsKey(headers.AUTHORIZATION)) {
			headers.add(headers.AUTHORIZATION, header.get(headers.AUTHORIZATION).toString());
		}
		if (header.containsKey(headers.CONTENT_TYPE)) {
			headers.add(headers.CONTENT_TYPE, header.get(headers.CONTENT_TYPE).toString());
		} else {
			headers.add("Content-Type", MediaType.APPLICATION_JSON);
		}
		HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
		ResponseEntity<String> responseEntity = rest.exchange(uri, HttpMethod.GET, requestEntity, String.class);
		setStatus(responseEntity.getStatusCode());
		body = responseEntity.getBody();
		return body;
	}

	public String post(String uri, String json) {
		String body;
		HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
		ResponseEntity<String> responseEntity = rest.exchange(uri, HttpMethod.POST, requestEntity, String.class);
		setStatus(responseEntity.getStatusCode());
		body = responseEntity.getBody();
		return body;
	}

	public String post(String uri, String data, HashMap<String, Object> header) {
		String body;
		HttpHeaders headers = new HttpHeaders();
		if (header.containsKey(headers.AUTHORIZATION)) {
			headers.add(headers.AUTHORIZATION, header.get(headers.AUTHORIZATION).toString());
		}
		// headers.add("Content-Type", MediaType.APPLICATION_JSON);
		ResponseEntity<String> responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
		HttpEntity<String> requestEntity;
		requestEntity = new HttpEntity<String>(data, headers);
		responseEntity = rest.exchange(uri, HttpMethod.POST, requestEntity, String.class);
		setStatus(responseEntity.getStatusCode());
		body = responseEntity.getBody();
		return body;
	}

/*	public String uploadFile(String uri, String data, HashMap<String, Object> header) {
		String body;
		HttpHeaders headers = new HttpHeaders();
		if (header.containsKey(headers.AUTHORIZATION)) {
			headers.add(headers.AUTHORIZATION, header.get(headers.AUTHORIZATION).toString());
		}
		if (header.containsKey(headers.CONTENT_TYPE)) {
			headers.add(headers.CONTENT_TYPE, header.get(headers.CONTENT_TYPE).toString());
		} else {
			headers.add("Content-Type", MediaType.APPLICATION_JSON);
		}
		ResponseEntity<String> responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
		if (headers.getContentType().toString().equals(MediaType.MULTIPART_FORM_DATA_TYPE.toString())) {
			HttpEntity<FormDataMultiPart> requestEntity;
			try {
				FormDataMultiPart multiPart = new FormDataMultiPart();
				FileInputStream is = new FileInputStream(data);
				FormDataBodyPart filePart = new FormDataBodyPart("content", is,
						MediaType.APPLICATION_OCTET_STREAM_TYPE);
				multiPart.bodyPart(filePart);
				multiPart.field("docPath", data);
				headers.add("Content-Type", MediaType.APPLICATION_JSON);
				requestEntity = new HttpEntity<FormDataMultiPart>(multiPart, headers);// new
																						// HttpEntity<String>(multiPart,
																						// headers);
				responseEntity = rest.exchange(uri, HttpMethod.POST, requestEntity, String.class);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			HttpEntity<String> requestEntity;
			requestEntity = new HttpEntity<String>(data, headers);
			responseEntity = rest.exchange(uri, HttpMethod.POST, requestEntity, String.class);
		}
		setStatus(responseEntity.getStatusCode());
		body = responseEntity.getBody();
		return body;
	}*/

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
}