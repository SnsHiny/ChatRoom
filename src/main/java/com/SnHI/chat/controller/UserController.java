package com.SnHI.chat.controller;

import com.SnHI.chat.bean.Member;
import com.SnHI.chat.bean.User;
import com.SnHI.chat.service.MemberService;
import com.SnHI.chat.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private MemberService memberService;

    /**
     * 登录检测
     * @param user
     * @param model
     * @return
     */
    @PostMapping("/user/identityCheck")
    public String identityCheck(User user, Model model) throws UnsupportedEncodingException {
        User resultUser = userService.identityCheck(user);
        if(resultUser == null || !user.getPassword().equals(resultUser.getPassword()) || !user.getUType().equals(resultUser.getUType())) {
            model.addAttribute("msg", "登录错误，请检查用户名密码是否正确");
            return "Login";
        } else if("用户".equals(user.getUType())){
            return "redirect:/chat/selectInfo?uname=" + URLEncoder.encode(user.getUname(), "UTF-8");
        } else {
            return "redirect:/member/selectMember";
        }
    }

    /**
     * 注册用户
     * @param user
     * @param bindingResult
     * @param model
     * @return
     */
    @PostMapping("/user/saveUser")
    public String saveUser(@Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("msg", "注册失败");
            return "Register";
        } else {
            userService.saveUser(user);
            model.addAttribute("msg", "注册成功");
            return "Login";
        }
    }

    /**
     * 修改密码
     * @param uname
     * @param oldPassword
     * @param user
     * @param repeatPassword
     * @param bindingResult
     * @return
     */
    @PostMapping("/user/updatePassword")
    public String updatePassword(String uname, String oldPassword, @Valid User user, String repeatPassword,
                                 BindingResult bindingResult) {
        User identity_user = userService.identityCheck(new User(uname, null, null));
        //找回密码
        if (repeatPassword != null && oldPassword == null && user.getPassword().equals(repeatPassword)) {
            userService.updateById(new User(uname, user.getPassword(), identity_user.getUType()));
            return "FindPassword";
        //修改密码
        }else if (oldPassword != null && user.getPassword() != null && repeatPassword != null && user.getPassword().equals(repeatPassword) && !bindingResult.hasErrors() && oldPassword.equals(identity_user.getPassword())) {
            userService.updateById(new User(uname, user.getPassword(), identity_user.getUType()));
        }
        return "Login";
    }

    /**
     * 注销账号
     * @param uname
     * @return
     */
    @GetMapping("/user/deleteUser/{uname}")
    public String deleteUser(@PathVariable("uname") String uname) {
        memberService.remove(new QueryWrapper<Member>().eq("uname", uname));
        userService.remove(new QueryWrapper<User>().eq("uname", uname));
        return "Login";
    }
}
