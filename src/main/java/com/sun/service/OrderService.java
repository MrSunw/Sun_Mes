package com.sun.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sun.dao.MesOrderMapper;
import com.sun.model.MesOrder;
import com.sun.param.MesOrderVo;
import com.sun.util.MyStringUtils;

@Service
public class OrderService {

	@Resource
	private MesOrderMapper mesOrderMapper;
	
	
	//增加用户
	public void addOrder(MesOrderVo mesOrderVo) {
		
		try {
			MesOrder mesOrder = MesOrder.builder().orderId(mesOrderVo.getOrderId())
					.orderClientname(mesOrderVo.getOrderClientname())//
					.orderProductname(mesOrderVo.getOrderProductname()).orderContractid(mesOrderVo.getOrderContractid())//
					.orderImgid(mesOrderVo.getOrderImgid()).orderMaterialname(mesOrderVo.getOrderMaterialname())
					.orderCometime(MyStringUtils.string2Date(mesOrderVo.getComeTime(), null))//
					.orderCommittime(MyStringUtils.string2Date(mesOrderVo.getCommitTime(), null))
					.orderInventorystatus(mesOrderVo.getOrderInventorystatus()).orderStatus(mesOrderVo.getOrderStatus())//
					.orderMaterialsource(mesOrderVo.getOrderMaterialsource())
					.orderHurrystatus(mesOrderVo.getOrderHurrystatus()).orderStatus(mesOrderVo.getOrderStatus())
					.orderRemark(mesOrderVo.getOrderRemark()).build();
			
			mesOrderMapper.insertSelective(mesOrder);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
}
