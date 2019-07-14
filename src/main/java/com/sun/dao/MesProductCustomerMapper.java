package com.sun.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sun.beans.PageQuery;
import com.sun.dto.SearchProductDto;
import com.sun.model.MesProduct;

public interface MesProductCustomerMapper {

	Long getProductCount();

	int countBySearchDto( @Param("dto") SearchProductDto dto);

	List<MesProduct> getPageListSearchDto( @Param("dto") SearchProductDto dto,  @Param("page") PageQuery page);
    
}