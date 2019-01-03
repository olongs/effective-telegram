package com.test.exception;

public class OrgModelNoUserException extends Exception{
	public OrgModelNoUserException()
	{
		super("数据库中无此用户，请检查用户登录名");
	}
}
