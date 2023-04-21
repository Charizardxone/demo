package com.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.domain.SysUser;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.cursor.Cursor;

import java.util.List;
import java.util.Map;

/**
 * @author yfc
 * @date 2023/4/13 9:59
 */
@Mapper
public interface SysUserMapper{

    @Select("select * from t_sys_user")
    @Options(fetchSize = Integer.MIN_VALUE)
    Cursor<SysUser> findAll();

    /**
     * @param list
     * @return int 影响行数
     */
    @Insert({"<script>" +
            "INSERT INTO `demo`.`t_sys_user` ( `name`, `age`, `remark`,\n" +
            "                                         `address`, `nick_name`, `email`,\n" +
            "                                         `phone`, `insert_time`, `last_modify_time`)\n" +
            "        VALUES" +
            "        <foreach collection=\"list\" item=\"item\" separator=\",\" >\n" +
            "            ( #{item.name}, #{item.age}, #{item.remark},\n" +
            "            #{item.address}, #{item.nickName}, #{item.email},\n" +
            "            #{item.phone}, #{item.insertTime}, #{item.lastModifyTime})" +
            "        </foreach>" +
            "</script>"})
    Integer batchSave(List<SysUser> list);

    @Insert("insert into  t_sys_user (`name`, `age`, `remark`, " +
            "            `address`, `nick_name`, `email`, " +
            "            `phone`, `insert_time`, `last_modify_time`) " +
            "value ( #{name}, #{age}, #{remark}, " +
            "          #{address}, #{nickName}, #{email}," +
            "          #{phone}, #{insertTime}, #{lastModifyTime})")
    int save(SysUser user);
}
