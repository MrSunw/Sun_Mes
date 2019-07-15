package com.sun.test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sun.beans.PageQuery;
import com.sun.dao.MesProductCustomerMapper;
import com.sun.dao.SysUserMapper;
import com.sun.dto.SearchProductDto;
import com.sun.dto.SearchProductIronDto;
import com.sun.model.MesProduct;
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
		dto.setSearch_status(0);
		PageQuery page=new PageQuery();
		page.setPageNo(1);
		page.setPageSize(8);
		page.setOffset(0);
		SearchProductIronDto  dto1=new SearchProductIronDto();
		dto1.setKeyword("");
		dto1.setSearch_status(1);
		int count=mesProductCustomerMapper.countBySearchDto(dto);
		List<MesProduct> ps=mesProductCustomerMapper.getPageListSearchDto(dto, page);
		for(MesProduct p:ps) {
		System.out.println("---->"+p);
		}
	}
}
