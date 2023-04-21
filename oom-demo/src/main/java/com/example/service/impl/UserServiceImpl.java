package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dao.SysUserMapper;
import com.example.domain.SysUser;
import com.example.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.callback.Callback;
import javax.websocket.MessageHandler;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author yfc
 * @date 2023/4/17 10:45
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchSave(List<SysUser> userList) {
        return sysUserMapper.batchSave(userList) > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public int listUser() {
        int i = 0;

        Cursor<SysUser> cursor = sysUserMapper.findAll();
        Iterator<SysUser> iterator = cursor.iterator();

        ObjectMapper mapper = new ObjectMapper();
        BufferedWriter writer = null;

        try {
            StringBuilder str = new StringBuilder(5000000);
            writer = new BufferedWriter(new FileWriter("D:/json.txt", true));

            while (iterator.hasNext()) {
                SysUser next = iterator.next();
                str.append(mapper.writeValueAsString(next)).append("\n");
                if (str.length() < 5000000 && iterator.hasNext()) {
                    continue;
                }
                writer.write(str.toString());
                str = new StringBuilder();
                i++;
            }
        } catch (JsonProcessingException e) {
            log.error("json格式化失败", e);
        } catch (IOException e) {
            log.error("文件写入失败", e);
        }finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                log.error("流关闭失败", e);
            }
        }

        return i;
    }

    @Override
    public int listUser1() {
        int i = 0;
        SqlSession sqlSession = SqlSessionUtils.getSqlSession(sqlSessionFactory);
        SysUserMapper testMapper = sqlSession.getMapper(SysUserMapper.class);
        Cursor<SysUser> cursor = null;
        try {
            cursor = testMapper.findAll();
            Iterator<SysUser> iterator = cursor.iterator();
            while (iterator.hasNext()) {
                iterator.next();
                i++;
            }
        } catch (Exception e) {
            log.error("sss", e);
        } finally {
            try {
                cursor.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            SqlSessionUtils.closeSqlSession(sqlSession, sqlSessionFactory);
        }
        return i;
    }

    @Override
    public int save(SysUser user) {
        return sysUserMapper.save(user);
    }

}
