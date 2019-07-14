package com.sun.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sun.dao.MesProductCustomerMapper;
import com.sun.dao.SysUserMapper;
import com.sun.dto.SearchProductDto;
import com.sun.model.SysUser;

public class testMybatis {

	private static ApplicationContext bean=new ClassPathXmlApplicationContext("spring\\applicationContext.xml");
	
	private SysUserMapper sysUserMapper;
	private MesProductCustomerMapper mesProductCustomerMapper;
	
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
	@Test
	public void getProductCount() {
		mesProductCustomerMapper=bean.getBean(MesProductCustomerMapper.class);
		//Long s=mesProductCustomerMapper.getProductCount();
		//System.out.println(s);
         
		SearchProductDto  dto=new SearchProductDto();
		dto.setKeyword("");
		dto.setSearch_source("");
		int count=mesProductCustomerMapper.countBySearchDto(dto);
		System.out.println("---->"+count);
	}
}
