/*
 * Copyright (c) 2010 Yonyou Auto, Inc. All  Rights Reserved.
 * This software is published under the terms of the Yonyou Auto Information Technology (Shanghai) Co., Ltd
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 * 
 * CreateDate  ： 2016年12月8日 下午2:52:46
 * CreateBy    ： MjieQiu
 * ProjectName ：AtomikosJta
 * PackageName ：com.yonyou.f4.ex
 * File_name   ：JTATest.java
 * Type_name   ：JTATest
 * Comment     ：			    
 */
package com.yonyou.f4.ex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author MjieQiu
 *
 */
@Component
public class JTATest {
	
	private Logger log = LoggerFactory.getLogger("JTATest");
	@Autowired
	private JdbcTemplate mJdbcTemplate;
	@Autowired
	private JdbcTemplate sJdbcTemplate;
	
	@Transactional
	public void insert()throws Throwable{
		mJdbcTemplate.update("insert into users (name) values(?)", new Object[]{"userName3"});
		sJdbcTemplate.update("insert into users (id,name) values(?)", new Object[]{999999999999999999l,"userName3"});
//		throw new RuntimeException("ssssssssssss");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext appCtx = new ClassPathXmlApplicationContext("classpath:*.xml");
		JTATest jtat = appCtx.getBean(JTATest.class); 
		try {
			jtat.insert();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
