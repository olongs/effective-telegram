package com.test.model;

import java.io.Serializable;
import java.sql.Date;

/**
 * 用户实体类
 * @author ChenQiXiang
 *
 */
public class UserInfo implements Serializable{
	//用户ID，用户的唯一标识
	private Integer id = null;
	//用户登录ID，必须唯一不可重复
	private String loginId = null;
	//用户姓名
	private String name = null;
	//用户密码，数据存储使用MD5码存储
	private String pwd = null;
	//用户出生日期
	private Date birthday = null;
	//用户电话号码
	private String telNo = null;
	//用户Email
	private String email = null;
	//用户状态，status=0 正常用户;status=1 逻辑删除用户;status=2 用户账号被锁
	private Integer status = 0;
	//头像
	private Integer imgId = null;
	//用户所在部门ID，为空时说明用户不在任何部门
	private Integer oid = null;
	//用户所在部门名称，是冗余字段
	private String oname = null;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getImgId() {
		return imgId;
	}
	public void setImgId(Integer imgId) {
		this.imgId = imgId;
	}
	public Integer getOid() {
		return oid;
	}
	public void setOid(Integer oid) {
		this.oid = oid;
	}
	public String getOname() {
		return oname;
	}
	public void setOname(String oname) {
		this.oname = oname;
	}	
}
