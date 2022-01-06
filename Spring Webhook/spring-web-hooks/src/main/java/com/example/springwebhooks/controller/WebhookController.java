package com.example.springwebhooks.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.springwebhooks.entity.Webhook;
import com.example.springwebhooks.repository.WebhookRepository;

@RestController
@RequestMapping("/webhooks")
public class WebhookController {
	private final static Logger logger = LoggerFactory.getLogger(WebhookController.class);

	private WebhookRepository webHookRepository;

	@Autowired
	public WebhookController(WebhookRepository webHookRepository) {
		super();
		this.webHookRepository = webHookRepository;
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_MARKDOWN_VALUE)
	public ResponseEntity<String> addWebHook(@RequestBody Webhook webHook) {
		logger.info("New webhook for " + webHook.getCompanyName() + " is registered");
		List<Webhook> webhooks = webHookRepository.findByCompanyNameAndType(webHook.getCompanyName(),
				webHook.getType());
		if (webhooks != null && webhooks.contains(webHook)) {
			return new ResponseEntity<>("Webhook already exists", HttpStatus.OK);
		}
		webHookRepository.save(webHook);
		return new ResponseEntity<>("Successfully", HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Webhook>> getAllWebHooks() {
		List<Webhook> webhooks = new ArrayList<>();
		webHookRepository.findAll().iterator().forEachRemaining(webhooks::add);
		return new ResponseEntity<List<Webhook>>(webhooks, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/comapnies/{companyName}/types/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Webhook> getWebHooksByCompanyNameAndType(@PathVariable String companyName,
			@PathVariable String type) {
		List<Webhook> webhooks = webHookRepository.findByCompanyNameAndType(companyName, type);
		return new ResponseEntity<Webhook>(webhooks.get(0), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/comapnies/{companyName}/types/{type}", produces = MediaType.TEXT_MARKDOWN_VALUE)
	public ResponseEntity<String> removeWebHook(@PathVariable String companyName, @PathVariable String type) {
		List<Webhook> webhooks = webHookRepository.findByCompanyNameAndType(companyName, type);
		if (!webhooks.isEmpty()) {
			webHookRepository.delete(webhooks.get(0));
			return new ResponseEntity<>("WebHook was successfully deleted.", HttpStatus.OK);
		}
		return new ResponseEntity<>("Webhook doesn't exist.", HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/ids/{id}", produces = MediaType.TEXT_MARKDOWN_VALUE)
	public ResponseEntity<String> removeWebHookById(@PathVariable Long id) {
		Optional<Webhook> webhook = webHookRepository.findById(id);
		if (webhook != null) {
			webHookRepository.deleteById(webhook.get().getId());
			return new ResponseEntity<>("WebHook was successfully deleted.", HttpStatus.OK);
		}
		return new ResponseEntity<>("Webhook doesn't exist.", HttpStatus.OK);
	}
}
