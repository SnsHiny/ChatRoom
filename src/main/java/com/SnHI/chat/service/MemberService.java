package com.SnHI.chat.service;

import com.SnHI.chat.bean.Member;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface MemberService extends IService<Member> {
    Member selectInfo(String uname);

    List<Member> selectAllMember();
}
