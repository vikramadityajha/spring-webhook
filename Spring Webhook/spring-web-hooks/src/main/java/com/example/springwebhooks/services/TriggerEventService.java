package com.example.springwebhooks.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.springwebhooks.dto.RequestDTO;
import com.example.springwebhooks.entity.Webhook;
import com.example.springwebhooks.utils.Utils;

@Service
public class TriggerEventService {

	@Autowired
	WebhookService webhookService;

	@Autowired
	private RestTemplate restTemplate;

	private HttpHeaders httpHeaders = null;

	public void triggerEventToSubcriber(String eventType, String taskName) {
		List<Webhook> list = webhookService.getWebHooksByType(eventType);

		RequestDTO requestObj = new RequestDTO();
		if (eventType == "create") {
			requestObj.setMessage("New Task is added in the system with name " + taskName);
		} else if (eventType == "delete") {
			requestObj.setMessage("Task is deleted from the system with name " + taskName);
		}

		System.out.println("inside triggerEventToSubcriber");
		if (list != null && list.size() != 0) {
			for (Webhook webhook : list) {
				try {
					invokeServiceUsingRestTemplate(requestObj, webhook.getUrl());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	protected String invokeServiceUsingRestTemplate(Object requestObj, String endPointURL) throws Exception {
		HttpEntity<?> httpEntity = this.getHttpEntity(requestObj);
		ResponseEntity<String> responseEntity = this.restTemplate.exchange(endPointURL, HttpMethod.POST, httpEntity,
				String.class);
		String jsonBodyResponse = responseEntity.getBody();
		HttpStatus httpStatus = responseEntity.getStatusCode();
		HttpHeaders headersResponse = responseEntity.getHeaders();// Future use

		if (httpStatus.is2xxSuccessful()) {
			return jsonBodyResponse;

		} else {
			String errMessage = "RestService returned status is " + httpStatus + " ,and URL is " + endPointURL;
			throw new IOException(errMessage);
		}
	}

	protected HttpEntity<?> getHttpEntity(Object request) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String requestBody = this.createRequestBody(request);
		return new HttpEntity(requestBody,headers);
	}

	private String createRequestBody(Object request) throws Exception {
		String jsonStr = Utils.getJSOnStr(request);
		return jsonStr;
	}
}
