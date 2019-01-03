package com.test.exception;

public class OrgModelInvalidPwdException extends Exception{
	public OrgModelInvalidPwdException()
	{
		super("登录密码错误，请检查密码是否正确");
	}
}
