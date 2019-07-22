package com.example.demo.model;

import java.io.Serializable;
import java.util.Date;

import com.example.demo.model.AbstractTimestampEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="Tasks")
@EntityListeners(AuditingEntityListener.class)
public class Task implements Serializable{
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long tid;
	
	@Size(min=1)
	@NotBlank
	@Column(unique=true, nullable=false)
	private String tname;
	
	private String tdesc;
	
	private Long uid;
	
	public Date getTask_Date() {
		return task_Date;
	}

	public void setTask_Date(Date task_Date) {
		this.task_Date = task_Date;
	}

	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "task_Date")
    private Date task_Date;

    @PrePersist
    protected void onCreate() {
    task_Date = new Date();
    }
    
	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getTdesc() {
		return tdesc;
	}

	public void setTdesc(String tdesc) {
		this.tdesc = tdesc;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}
}