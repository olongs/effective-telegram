package com.test.service;

import java.util.List;


import com.test.Starter;
import com.test.model.UserInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBootTest(classes=Starter.class)
public class OrgModelTester {
	@Autowired
	private IOrgModel orgmodel;
	
	@Test
	public void testLogin()
	{
		try
		{
			String loginId = "admin";
			String pwd = "1";
			Boolean btn = orgmodel.login(loginId, pwd);
			Assert.assertTrue(btn);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUserOtherOpts()
	{
		try
		{
			//获取oid=null部门下用户总数
			Integer oid = null;
			List<UserInfo> users = orgmodel.getUserListByOid(oid);
			Integer size = users.size();
			//获取登录名为testuser的用户
			String loginId = "testuser";
			UserInfo testUser = orgmodel.getUserByLoginId(loginId);
			if(testUser != null)
			{
				//删除testuser用户
				Boolean rtn = orgmodel.deleteUser(testUser.getId());
				Assert.assertTrue(rtn);
				//重新获取用户记录数
				users = orgmodel.getUserListByOid(oid);
				Integer size2 = users.size();
				Assert.assertEquals("删除用户后数据库记录数比对错误", (long)size, (long)(size2+1));
			}
			
			
			UserInfo ui = new UserInfo();
			ui.setName("测试用户");
			ui.setLoginId("testuser");
			ui.setPwd("1");
			ui.setBirthday(new java.sql.Date(System.currentTimeMillis()));
			ui.setEmail("testuser@test.com");
			ui.setStatus(0);
			orgmodel.saveUser(ui);
			//重新获取用户记录数
			testUser = orgmodel.getUserByLoginId(loginId);
			if(testUser == null)
				Assert.assertFalse("添加用户测试失败", false);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
