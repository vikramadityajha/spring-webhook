package com.example.springwebhooks.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "webhook")
public class Webhook {
 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="url")
	private String url;
	
	@Column(name="company_name")
	private String companyName;
	
	@Column(name="type")
	private String type;
 
	public Webhook() {
		super();
		// TODO Auto-generated constructor stub
	}
 
	public Webhook(Long id, String url, String companyName, String type) {
		super();
		this.id = id;
		this.url = url;
		this.companyName = companyName;
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
 
}
