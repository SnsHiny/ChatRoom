package com.SnHI.chat.controller;

import com.SnHI.chat.bean.Member;
import com.SnHI.chat.bean.User;
import com.SnHI.chat.service.MemberService;
import com.SnHI.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private UserService userService;

    private Member info = null;

    /**
     * 查询个人信息
     * @param uname
     * @param model
     * @return
     */
    @GetMapping("/chat/selectInfo")
    public String selectInfo(@RequestParam("uname") String uname, Model model) {
        this.info = memberService.selectInfo(uname);
        User user = userService.identityCheck(new User(uname, null, null));
        List<Member> members = memberService.selectAllMember();
        model.addAttribute("members", members);
        model.addAttribute("info", info);
        model.addAttribute("userUname", user.getUname());
        model.addAttribute("oldPassword", user.getPassword());
        return "UserInterface";
    }

    /**
     * 完善个人信息
     * @param uname
     * @param member
     * @param bindingResult
     * @param model
     * @return
     * @throws UnsupportedEncodingException
     */
    @PostMapping("/chat/insertInfo")
    public String insertInfo(@RequestParam("uname") String uname, @Valid Member member, BindingResult bindingResult, Model model) throws UnsupportedEncodingException {
        HashMap<String, Object> errorMap = new HashMap<>();
        if (bindingResult.hasErrors()) {
            model.addAttribute("info", info);
            return "UserInterface";
        } else {
            memberService.save(member);
            model.addAttribute("info", member);
            return "redirect:/chat/selectInfo?uname=" + URLEncoder.encode(uname, "UTF-8");
        }
    }

    /**
     * 修改个人信息
     * @param mid
     * @param uname
     * @param member
     * @param bindingResult
     * @param model
     * @return
     * @throws UnsupportedEncodingException
     */
    @PostMapping("/chat/updateInfo")
    public String updateInfo(@RequestParam("mid") Integer mid,
                             @RequestParam("uname") String uname,
                             @Valid Member member, BindingResult bindingResult, Model model) throws UnsupportedEncodingException {
        HashMap<String, Object> errorMap = new HashMap<>();
        if (bindingResult.hasErrors()) {
            model.addAttribute("info", info);
            return "UserInterface";
        } else {
            memberService.updateById(member);
            return "redirect:/chat/selectInfo?uname=" + URLEncoder.encode(uname, "UTF-8");
        }
    }

}
