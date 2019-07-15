package com.sun.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sun.beans.PageQuery;
import com.sun.dto.SearchProductDto;
import com.sun.dto.SearchProductIronDto;
import com.sun.model.MesProduct;

public interface MesProductCustomerMapper {

	//查询总数
	Long getProductCount();

	//批量到库页面的查询条件
	int countBySearchDto( @Param("dto") SearchProductDto dto);
	
    //批量到库分页
	List<MesProduct> getPageListSearchDto( @Param("dto") SearchProductDto dto,  @Param("page") PageQuery page);
    
	//钢锭查询的查询条件
	int countBySearcheIronDto(@Param("dto") SearchProductIronDto dto);
     
	//钢锭分页
	List<MesProduct> getPageListSearchIronDto(@Param("dto") SearchProductIronDto dto,@Param("page") PageQuery page);

	//批量到库
	void batchStart(@Param("list") String[] idArray);
	
	
    
}