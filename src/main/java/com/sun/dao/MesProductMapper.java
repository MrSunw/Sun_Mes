package com.sun.dao;

import com.sun.model.MesProduct;

public interface MesProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MesProduct record);

    int insertSelective(MesProduct record);

    MesProduct selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MesProduct record);

    int updateByPrimaryKey(MesProduct record);
}