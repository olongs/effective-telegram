package com.test.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.test.model.FileInfo;
import com.test.model.FunctionInfo;
import com.test.model.OrgInfo;
import com.test.model.UserInfo;

/**
 * 企业信息系统组织机构接口,涉及组织机构所有操作通过此接口定义功能
 * 当前IOrgModel定义了如下功能
 * 一、用户登录校验
 * 二、用户管理涉及所有功能，添加、删除、逻辑删除、修改，用户修改密码
 * 三、用户注销
 * @author ChenQiXiang
 *
 */
public interface IOrgModel {
	/**
	 * 校验登录用户是否合法
	 * @param loginId 用户登录ID
	 * @param pwd 用户密码
	 * @return Boolean true:登录成功;false:登录失败
	 */
	public Boolean login(String loginId,String pwd) throws Exception;
	
	/**
	 * 保存用户，根据参数ui是否包含id属性决定是新增操作或者更新操作
	 * @param ui 用户实体对象
	 * @return Boolean true:保存成功;false:保存失败
	 */
	public Boolean saveUser(UserInfo ui) throws Exception;
	
	/**
	 * 修改用户，根据参数ui是否包含id属性决定是新增操作或者更新操作
	 * @param ui 用户实体对象
	 * @return Boolean true:保存成功;false:保存失败
	 */
	public Boolean updateUser(UserInfo ui) throws Exception;
	
	/**
	 * 删除用户，从数据库中删除记录，只适应与添加用户错误，新增用户无任何关联业务数据，
	 * 可以从数据库表中删除，如果删除的用户存在关联业务数据，只能执行逻辑删除。
	 * @param id 用户主键
	 * @return Boolean true:删除成功;false:删除失败
	 */
	public Boolean deleteUser(Integer id) throws Exception;
	
	/**
	 * 逻辑删除用户，当用户存在关联业务数据，此用户不可从数据库中删除，只能
	 * 执行逻辑删除。
	 * @param id 用户主键
	 * @return Boolean true:删除成功;false:删除失败
	 */
	public Boolean logicDeleteUser(Integer id) throws Exception;
	
	/**
	 * 根据用户ID获取用户对象
	 * @param id 用户主键
	 * @return UserInfo
	 */
	public UserInfo getUserById(Integer id) throws Exception;
	
	/**
	 * 根据用户LoginId获取用户对象
	 * @param loginId 用户loginId
	 * @return UserInfo
	 */
	public UserInfo getUserByLoginId(String loginId) throws Exception;
	
	/**
	 * 根据部门ID获取此部门下所有用户
	 * @param oid 部门主键
	 * @return List<UserInfo>
	 */
	public List<UserInfo> getUserListByOid(Integer oid) throws Exception;
	
	/**
	 * 根据用户名称查询用户列表
	 * @param name 用户名称
	 * @return List<UserInfo>
	 */
	public List<UserInfo> findUserListByName(String name) throws Exception;

	/**
	 * 获取所有部门列表
	 * 
	 * @return List<UserInfo>
	 */
	public List<OrgInfo> getOrgList() throws Exception;
	
	/**
	 * 根据部门ID获取部门对象
	 * @param id 部门ID
	 * @return List<UserInfo>
	 */
	public OrgInfo getOrgById(@Param("id") Integer id) throws Exception;
	/**
	 * 根据部门ID获取本部门下一级部门列表
	 * @param id
	 * @return
	 */
	public List<OrgInfo> getChildOrg(Integer id);
	/**
	 * 获取根部门对象
	 * @return
	 */
	public OrgInfo getRootOrg();
	/**
	 * 保存部门方法
	 * @param oi
	 */
	public void saveOrg(OrgInfo oi);
	/**
	 * 初始化根部门与超级用户
	 */
	public void initOrgModel();
	
	/**
	 * 获取根模块对象
	 * @return
	 */
	public FunctionInfo getRootFunc();
	/**
	 * 根据功能模块ID获取此功能下一级模块列表
	 * @param id
	 * @return
	 */
	public List<FunctionInfo> getChildFunc(Integer id);
	/**
	 * 功能模块保存方法
	 * @param fi
	 */
	public void saveFunc(FunctionInfo fi);
	/**
	 * 功能模块更新方法
	 * @param fi
	 */
	public void updateFunc(FunctionInfo fi);
	/**
	 * 初始化系统功能模块，包括(系统管理，用户管理，部门管理等模块)
	 * @return
	 */
	public FunctionInfo initFunc();
	/**
	 * 根据模块ID删除此模块，系统功能模块不可删除
	 * @param id
	 */
	public void deleteFunc(Integer id);
	/**
	 * 保存文件对象
	 * @param fi 文件对象
	 * @return List<UserInfo>
	 */
	public void saveFile(FileInfo fi);
	
	/**
	 * 根据ID获取文件对象
	 * @param id 文件主键
	 * @return List<UserInfo>
	 */
	public FileInfo getFileById(@Param("id") Integer id);
}

