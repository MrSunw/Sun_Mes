package com.sun.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sun.beans.PageQuery;
import com.sun.dto.SearchPlanDto;
import com.sun.model.MesPlan;

public interface MesPlanCustomerMapper {
	Long getPlanCount();

	// @Param("dto")--给mapper.xml查询sql指定参数名称 #{dto.keyword}
	int countBySearchDto(@Param("dto") SearchPlanDto dto);

	List<MesPlan> getPageListBySearchDto(@Param("dto") SearchPlanDto dto, @Param("page") PageQuery page);

	void batchStartWithIds(@Param("list") String[] tempIds);

}