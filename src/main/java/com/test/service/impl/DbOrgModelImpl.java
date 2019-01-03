package com.test.service.impl;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.exception.OrgModelInvalidPwdException;
import com.test.exception.OrgModelNoUserException;
import com.test.mapper.OrgModelMapper;
import com.test.model.FileInfo;
import com.test.model.FunctionInfo;
import com.test.model.OrgInfo;
import com.test.model.UserInfo;
import com.test.service.IOrgModel;
import com.test.util.Util;

/**
 * 企业信息系统组织机构接口,涉及组织机构所有操作通过此接口定义功能
 * 当前IOrgModel定义了如下功能
 * 一、用户登录校验
 * 二、用户管理涉及所有功能，添加、删除、逻辑删除、修改，用户修改密码
 * 三、用户注销
 * @author ChenQiXiang
 *
 */
@Service
public class DbOrgModelImpl implements IOrgModel{
	@Autowired
	private OrgModelMapper mapper;

	@Override
	public Boolean login(String loginId, String pwd) throws Exception {
		UserInfo ui = mapper.getUserByLoginId(loginId);
		if(ui == null)
			throw new OrgModelNoUserException();
		if(!Util.toString(ui.getPwd()).equals(pwd))
			return false;
		return true;
	}

	@Override
	public Boolean saveUser(UserInfo ui) throws Exception {
		try
		{
			mapper.saveUser(ui);
			return true;
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	
	@Override
	public Boolean updateUser(UserInfo ui) throws Exception {
		try
		{
			mapper.updateUser(ui);
			return true;
		}
		catch(Exception e)
		{
			throw e;
		}
	}

	@Override
	public Boolean deleteUser(Integer id) throws Exception {
		try
		{
			mapper.deleteUser(id);
			return true;
		}
		catch(Exception e)
		{
			throw e;
		}
	}

	@Override
	public Boolean logicDeleteUser(Integer id) throws Exception {
		try
		{
			mapper.logicDeleteUser(id);
			return true;
		}
		catch(Exception e)
		{
			throw e;
		}
	}

	@Override
	public UserInfo getUserById(Integer id) throws Exception {
		try
		{
			return mapper.getUserById(id);
		}
		catch(Exception e)
		{
			throw e;
		}
	}

	@Override
	public UserInfo getUserByLoginId(String loginId) throws Exception {
		try
		{
			return mapper.getUserByLoginId(loginId);
		}
		catch(Exception e)
		{
			throw e;
		}
	}

	@Override
	public List<UserInfo> getUserListByOid(Integer oid) throws Exception {
		try
		{
			return mapper.getUserListByOid(oid);
		}
		catch(Exception e)
		{
			throw e;
		}
	}

	@Override
	public List<UserInfo> findUserListByName(String name) throws Exception {
		try
		{
			return mapper.findUserListByName(name);
		}
		catch(Exception e)
		{
			throw e;
		}
	}

	@Override
	public List<OrgInfo> getOrgList() throws Exception{
		try
		{
			return mapper.getOrgList();
		}
		catch(Exception e)
		{
			throw e;
		}
	}

	@Override
	public OrgInfo getOrgById(Integer id) throws Exception{
		try
		{
			return mapper.getOrgById(id);
		}
		catch(Exception e)
		{
			throw e;
		}
	}

	@Override
	public List<OrgInfo> getChildOrg(Integer id) {
		try
		{
			return mapper.getChildOrg(id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public OrgInfo getRootOrg() {
		try
		{			
			return mapper.getRootOrg();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void saveOrg(OrgInfo oi) {
		try
		{
			mapper.saveOrg(oi);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void saveFile(FileInfo fi)
	{
		try
		{
			mapper.saveFile(fi);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public FileInfo getFileById(@Param("id") Integer id)
	{
		try
		{
			return mapper.getFileById(id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public FunctionInfo getRootFunc() {
		try
		{
			return mapper.getRootFunc();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<FunctionInfo> getChildFunc(Integer id) {
		try
		{
			return mapper.getChildFunc(id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void saveFunc(FunctionInfo fi) {
		try
		{
			mapper.saveFunc(fi);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void updateFunc(FunctionInfo fi) {
		try
		{
			mapper.updateFunc(fi);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void deleteFunc(Integer id)
	{
		try
		{
			mapper.deleteFunc(id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void initOrgModel()
	{
		try
		{
			OrgInfo root = this.getRootOrg();
			if(root == null)
			{
				OrgInfo oi = new OrgInfo();
				oi.setName("集团公司");
				oi.setPath("/");
				oi.setParentId(null);
				mapper.saveOrg(oi);
			}
			UserInfo adminUser = getUserByLoginId("admin");
			if(adminUser == null)
			{
				adminUser = new UserInfo();
				adminUser.setLoginId("admin");
				adminUser.setName("超级管理员");
				adminUser.setPwd("c4ca4238a0b923820dcc509a6f75849b");
				adminUser.setBirthday(Date.valueOf("2018-08-08"));
				adminUser.setEmail("admin@test.com");
				adminUser.setTelNo("13100000000");
				adminUser.setStatus(0);
				mapper.saveUser(adminUser);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public FunctionInfo initFunc()
	{
		FunctionInfo root = new FunctionInfo();
		root.setName("功能模块");
		root.setPath("/");
		root.setParentId(null);
		root.setType("system");
		this.saveFunc(root);
		FunctionInfo sys = new FunctionInfo();
		sys.setName("系统管理");
		sys.setPath("/"+root.getId());
		sys.setParentId(root.getId());
		sys.setPriority(1);
		sys.setType("system");
		this.saveFunc(sys);
		FunctionInfo funtree = new FunctionInfo();
		funtree.setName("系统管理");
		funtree.setPath(sys.getPath()+"/"+sys.getId());
		funtree.setParentId(sys.getId());
		funtree.setUrl("/function");
		funtree.setPriority(1);
		funtree.setType("system");
		this.saveFunc(funtree);
		
		FunctionInfo orgmodel = new FunctionInfo();
		orgmodel.setName("组织机构");
		orgmodel.setPath("/"+root.getId());
		orgmodel.setParentId(root.getId());
		orgmodel.setPriority(2);
		orgmodel.setType("system");
		this.saveFunc(orgmodel);
		
		FunctionInfo user = new FunctionInfo();
		user.setName("用户管理");
		user.setPath(orgmodel.getPath()+"/"+orgmodel.getId());
		user.setUrl("/user");
		user.setParentId(orgmodel.getId());
		user.setPriority(2);
		user.setType("system");
		this.saveFunc(user);
		FunctionInfo org = new FunctionInfo();
		org.setName("部门管理");
		org.setPath(orgmodel.getPath()+"/"+orgmodel.getId());
		org.setUrl("/org");
		org.setParentId(orgmodel.getId());
		org.setPriority(1);
		org.setType("system");
		this.saveFunc(org);
		
		return root;
	}
}
