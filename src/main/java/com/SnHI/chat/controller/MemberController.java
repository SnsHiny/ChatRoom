package com.SnHI.chat.controller;

import com.SnHI.chat.bean.Member;
import com.SnHI.chat.service.MemberService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    private Page<Member> page;

    /**
     * 插入测试数据
     * @param member
     */
    @PostMapping("/member/InsertMember")
    public void InsertMember(Member member) {
        memberService.save(member);
    }

    /**
     * 新增用户数据
     * @param member
     * @param bindingResult
     * @param model
     * @return
     */
    @PostMapping("/member/saveMember")
    //@Valid：代表封装数据以后要进行数据校验；BindingResult：校验结果
    public String saveMember(@Valid Member member, BindingResult bindingResult, Model model) {
        HashMap<String, Object> errorMap = new HashMap<>();
        if (bindingResult.hasErrors()) {
            //获取所有错误
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError: fieldErrors) {
                //获取校验错误的字段名和错误信息
                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            model.addAttribute("errors", errorMap);
            model.addAttribute("page", page);
            return "AdminInterface";
        } else {
            memberService.save(member);
            return "redirect:/member/selectMember?pn=" + page.getPages();
        }
    }

    /**
     * 查询所有用户信息
     * @param pn
     * @param model
     * @return
     */
    @GetMapping("/member/selectMember")
    public String selectMember(@RequestParam(value = "pn", defaultValue = "1") Integer pn, Model model) {
        Page<Member> memberPage = new Page<>(pn, 4);
        this.page = memberService.page(memberPage, null);
        model.addAttribute("page", page);
        return "AdminInterface";
    }

    /**
     * 根据编号删除用户
     * @param mid
     * @param pn
     * @return
     */
    @GetMapping("/member/deleteById/{mid}")
    public String deleteById(@PathVariable("mid") Integer mid,
                             @RequestParam("pn") Integer pn) {
        memberService.removeById(mid);
        return "redirect:/member/selectMember?pn=" + pn;
    }

    /**
     * 批量删除用户
     * @param members
     * @param pn
     * @return
     */
    @PostMapping("/member/deleteBatch")
    public String deleteBatch(Integer[] members, Integer pn) {
        for (Integer mid: members) {
            memberService.removeById(mid);
        }
        return "redirect:/member/selectMember?pn=" + pn;
    }

    /**
     * 修改用户数据
     * @param mid
     * @param pn
     * @param member
     * @param bindingResult
     * @param model
     * @return
     */
    @PostMapping("/member/updateMember")
    public String updateMember(@RequestParam("mid") Integer mid,
                               @RequestParam("pn") int pn,
                               @Valid Member member, BindingResult bindingResult, Model model) {
        HashMap<String, Object> errorMap = new HashMap<>();
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError: fieldErrors) {
                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            model.addAttribute("errors", errorMap);
            model.addAttribute("page", page);
            return "AdminInterface";
        } else {
            memberService.updateById(member);
            return "redirect:/member/selectMember?pn=" + pn;
        }
    }

}
