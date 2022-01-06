package com.example.springwebhooks.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springwebhooks.entity.Webhook;
import com.example.springwebhooks.repository.WebhookRepository;

@Service
public class WebhookService {

	@Autowired
	private WebhookRepository webHookRepository;

	public List<Webhook> getWebHooksByType(String type) {
		List<Webhook> webhooks = webHookRepository.findByType(type);
		return webhooks;
	}
}
