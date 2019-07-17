package com.sun.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sun.beans.PageQuery;
import com.sun.dto.ProductDto;
import com.sun.dto.SearchProductDto;
import com.sun.model.MesProduct;
import com.sun.param.SearchProductParam;

public interface MesProductCustomerMapper {

	//查询总数
	Long getProductCount();

	//批量到库页面的查询条件
	int countBySearchDto( @Param("dto") SearchProductDto dto);
	
    //批量到库分页
	List<ProductDto> getPageListSearchDto( @Param("dto") SearchProductDto dto,  @Param("page") PageQuery page);
    

	//批量到库
	void batchStart(@Param("list") String[] idArray);

	//材料绑定获取总数
	int countBindBySearchDto(@Param("dto") SearchProductDto dto);
	//材料绑定分页
	List<ProductDto> getPageBindListSearchDto(@Param("dto") SearchProductDto dto,@Param("page") PageQuery page);

	//绑定钢材分页
	List<ProductDto> getBindListSearchDto(@Param("dto") SearchProductParam param,@Param("page") PageQuery page);
	
	
    
}