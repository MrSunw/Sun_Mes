package com.sun.dao;

import com.sun.model.MesOrder;

public interface MesOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MesOrder record);

    int insertSelective(MesOrder record);

    MesOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MesOrder record);

    int updateByPrimaryKey(MesOrder record);
}