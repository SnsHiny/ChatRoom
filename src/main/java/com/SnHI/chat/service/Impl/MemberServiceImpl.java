package com.SnHI.chat.service.Impl;

import com.SnHI.chat.bean.Member;
import com.SnHI.chat.mapper.MemberMapper;
import com.SnHI.chat.service.MemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired(required = false)
    private MemberMapper memberMapper;

    /**
     * 查询个人信息
     * @param uname
     * @return
     */
    @Override
    public Member selectInfo(String uname) {
        QueryWrapper<Member> infoWrapper = new QueryWrapper<>();
        infoWrapper.eq("uname", uname);
        Member member = null;
        try {
            member = memberMapper.selectOne(infoWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return member;
    }

    /**
     * 查询所有用户
     * @return
     */
    @Override
    public List<Member> selectAllMember() {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("mid");
        List<Member> members = memberMapper.selectList(queryWrapper);
        return members;
    }
}
