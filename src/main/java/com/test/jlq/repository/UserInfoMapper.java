package com.test.jlq.repository;

import com.test.jlq.model.UserInfo;

import java.util.List;

/**
 * @author hao
 * @Date 2020/9/14
 */
public interface UserInfoMapper {

    List<UserInfo> selectAll();


}
