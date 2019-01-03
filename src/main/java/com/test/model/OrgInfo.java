package com.test.model;

import java.io.Serializable;
import java.sql.Date;

/**
 * 部门实体类
 * @author ChenQiXiang
 *
 */
public class OrgInfo implements Serializable{
	//部门ID，部门的唯一标识
	private Integer id = null;
	//部门名称
	private String name = null;
	//部门父ID
	private Integer parentId = null;
	//部门排序
	private Integer priority = 0;
	//部门路径
	private String path = null;
	//部门全名称
	private String fullName = null;
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
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}	
}
