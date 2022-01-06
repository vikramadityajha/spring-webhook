package com.example.springwebhooks.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springwebhooks.entity.Webhook;

public interface WebhookRepository extends JpaRepository<Webhook, Long> {

	public List<Webhook> findByCompanyNameAndType(String companyName, String type);

	public List<Webhook> findByCompanyName(String companyName);

	public List<Webhook> findByType(String type);
}
