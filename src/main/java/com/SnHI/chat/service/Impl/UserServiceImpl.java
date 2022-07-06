package com.SnHI.chat.service.Impl;

import com.SnHI.chat.bean.User;
import com.SnHI.chat.mapper.UserMapper;
import com.SnHI.chat.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired(required=false)
    UserMapper userMapper;

    /**
     * 校验用户登录
     * @param user
     * @return
     */
    @Override
    public User identityCheck(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("uname", user.getUname());
        User resultUser = null;
        try {
            resultUser = userMapper.selectOne(wrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultUser;
    }

    /**
     * 注册用户
     * @param user
     * @return
     */
    @Override
    public boolean saveUser(User user) {
        user.setUType("用户");
        boolean flag = true;
        try {
            userMapper.insert(user);
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
}
