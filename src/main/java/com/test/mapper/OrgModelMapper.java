package com.test.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.test.model.FileInfo;
import com.test.model.FunctionInfo;
import com.test.model.OrgInfo;
import com.test.model.UserInfo;

/**
 * Mybatis操作组织机构的接口类，包含组织机构所有操作数据库的功能
 * @author ChenQiXiang
 *
 * @date 2018年11月25日 
 * @version 1.0
 */
@Mapper
public interface OrgModelMapper {
	//校验登录用户是否合法
	public UserInfo login(@Param("loginId") String loginId,@Param("pwd") String pwd);
	//保存用户
	public void saveUser(UserInfo ui);
	//修改用户
	public void updateUser(UserInfo ui);
	//删除用户，从数据库中删除记录，只适应与添加用户错误，新增用户无任何关联业务数据，
	//可以从数据库表中删除
	public void deleteUser(@Param("id") Integer id);
	//逻辑删除用户，当用户存在关联业务数据，此用户不可从数据库中删除，只能
	//执行逻辑删除
	public void logicDeleteUser(@Param("id") Integer id);
	//根据用户ID获取用户对象
	public UserInfo getUserById(@Param("id") Integer id);
	//根据用户LoginId获取用户对象
	public UserInfo getUserByLoginId(@Param("loginId") String loginId);
	//根据部门ID获取此部门下所有用户
	public List<UserInfo> getUserListByOid(@Param("oid") Integer oid);
	//根据用户名称查询用户列表
	public List<UserInfo> findUserListByName(@Param("name") String name);
	//获取所有部门列表
	public List<OrgInfo> getOrgList();
	//根据部门ID获取部门对象
	public OrgInfo getOrgById(@Param("id") Integer id);
	//根据部门ID获取下属部门列表
	public List<OrgInfo> getChildOrg(@Param("id") Integer id);
	//获取根部门
	public OrgInfo getRootOrg();
	public void saveOrg(OrgInfo oi);
	public FunctionInfo getRootFunc();
	public List<FunctionInfo> getChildFunc(@Param("id") Integer id);
	public void saveFunc(FunctionInfo fi);
	public void updateFunc(FunctionInfo fi);
	public void deleteFunc(@Param("id") Integer id);
	
	public void saveFile(FileInfo fi);
	public FileInfo getFileById(@Param("id") Integer id);
}
