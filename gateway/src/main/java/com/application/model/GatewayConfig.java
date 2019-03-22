package com.application.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.application.model.converter.BooleanStringConverter;

@Entity
@Table(name="gateway_config")
public class GatewayConfig {
	
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private Long gatewayId;
	
	@Column(name="key_config", nullable=true, length=255)
	private String key;
	
	@Column(name="value", nullable=true, length=255)
	private String value;
	
	@Column(name="description", nullable=true, length=255)
	private String description;
	
	@Column(name="isNew")
	@Convert(converter=BooleanStringConverter.class)
	private boolean isNew;
	
	
	
	
	@Column(name="active")
	@Convert(converter=BooleanStringConverter.class)
	private boolean active;
	
	
	
	
	@Temporal(TemporalType.TIMESTAMP)
    private Date creationDateTime;


	public Long getGatewayId() {
		return gatewayId;
	}


	public void setGatewayId(Long gatewayId) {
		this.gatewayId = gatewayId;
	}


	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public boolean isNew() {
		return isNew;
	}


	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}


	public Date getCreationDateTime() {
		return creationDateTime;
	}


	public void setCreationDateTime(Date creationDateTime) {
		this.creationDateTime = creationDateTime;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


}
