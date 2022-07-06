package com.SnHI.chat;

import com.SnHI.chat.bean.Member;
import com.SnHI.chat.mapper.MemberMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

//批量插入数据
@SpringBootTest(classes = {ChatRoomApplication.class})
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class MemberTest {
    
    @Autowired(required = false)
    private MemberMapper memberMapper;

    @Test
    public void Test01_batchInsert() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new Date());
        for (int i = 0;i < 100;i++) {
            String uuid = UUID.randomUUID().toString().substring(0, 5) + i;
            Member member = new Member(null, uuid, "男", date, "北京", "123456789", uuid + "@qq.com", null);
            memberMapper.insert(member);
        }
    }

}
