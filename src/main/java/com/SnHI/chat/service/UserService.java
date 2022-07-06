package com.SnHI.chat.service;

import com.SnHI.chat.bean.User;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {
    User identityCheck(User user);

    boolean saveUser(User user);
}
