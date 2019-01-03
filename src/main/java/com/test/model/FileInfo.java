package com.test.model;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Date;

import javax.sql.rowset.serial.SerialBlob;

public class FileInfo implements Serializable{
	private Integer id = null;
	private String name = null;
	private String contextType = null;
	private Long length = null;
	private Date dt = null;
	private byte[] content = null;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContextType() {
		return contextType;
	}
	public void setContextType(String contextType) {
		this.contextType = contextType;
	}
	public Long getLength() {
		return length;
	}
	public void setLength(Long length) {
		this.length = length;
	}
	public Date getDt() {
		return dt;
	}
	public void setDt(Date dt) {
		this.dt = dt;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}

}
