package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="Users")
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class User {
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long uid;
	@Column(unique=true, nullable=false)
	@NotBlank
	private String uname;
	@NotBlank
	private String upassword;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	private String token;
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getUpassword() {
		return upassword;
	}
	public void setUpassword(String upassword) {
		this.upassword = upassword;
	}
	
}
