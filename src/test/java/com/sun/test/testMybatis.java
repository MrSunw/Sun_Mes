package com.sun.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sun.dao.SysUserMapper;
import com.sun.model.SysUser;

public class testMybatis {

	private static ApplicationContext bean=new ClassPathXmlApplicationContext("spring\\applicationContext.xml");
	
	private SysUserMapper sysUserMapper;
	
	@Test
	public void testUser() {
		sysUserMapper=bean.getBean(SysUserMapper.class);
		SysUser user=sysUserMapper.selectByPrimaryKey(1);
		System.out.println(user);
	}
	@Test
	public void testinsertUser() {
		sysUserMapper=bean.getBean(SysUserMapper.class);
		SysUser user=new SysUser();
		user.setUsername("usernaem");
		user.setTelephone("sdfsd");
		sysUserMapper.insertSelective(user);
	}
}
