package com.test;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.test.model.FileInfo;
import com.test.model.OrgInfo;
import com.test.service.IOrgModel;

import junit.framework.Assert;

/**
 * 单元测试类，设计的每个业务方法都需要进行单元测试，符合预期方可集成测试
 * @author ChenQiXiang
 *
 * @date 2018年11月25日 
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=Starter.class)
public class Tester
{
	@Autowired
	private IOrgModel orgmodel = null;
	
    @Test
    public void testGood()
    {
    	OrgInfo root = orgmodel.getRootOrg();
    	if(root != null)
    	{
    		System.out.println("root.name="+root.getName());
    		List<OrgInfo> lists = orgmodel.getChildOrg(root.getId());
    		for(OrgInfo oi:lists)
    		{
    			System.out.println("oi.name="+oi.getName());
    		}
    	}
    }
}
