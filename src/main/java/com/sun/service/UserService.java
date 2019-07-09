package com.sun.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sun.dao.SysUserMapper;
import com.sun.model.SysUser;

@Service
public class UserService {

	@Resource
	private SysUserMapper sysUserMapper;
	
	public void insert(SysUser user) {
		sysUserMapper.insertSelective(user);
	}
	public SysUser select(int id) {
		SysUser user= sysUserMapper.selectByPrimaryKey(id);
		return user;
	}
}
