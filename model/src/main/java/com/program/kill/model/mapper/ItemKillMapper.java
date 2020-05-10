package com.program.kill.model.mapper;

import com.program.kill.model.entity.ItemKill;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemKillMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemKill record);

    int insertSelective(ItemKill record);

    ItemKill selectByPrimaryKey(@Param("id") Integer id);

    int updateByPrimaryKeySelective(ItemKill record);

    int updateByPrimaryKey(ItemKill record);

    List<ItemKill> selectAllItemKill();

    int updateKillItem(@Param("killId") Integer killId);
}