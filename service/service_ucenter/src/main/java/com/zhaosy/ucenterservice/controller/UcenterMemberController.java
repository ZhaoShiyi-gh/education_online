package com.zhaosy.ucenterservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.zhaosy.commenUtils.JwtUtils;
import com.zhaosy.commenUtils.R;
import com.zhaosy.commenUtils.UCenterInfoForm;
import com.zhaosy.ucenterservice.entity.UcenterMember;
import com.zhaosy.ucenterservice.entity.Vo.RegisterVo;
import com.zhaosy.ucenterservice.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author zhaosy
 * @since 2021-03-31
 */
@RestController
@RequestMapping("/eduucenter/member")
//@CrossOrigin
public class UcenterMemberController {

    @Autowired
    UcenterMemberService ucenterMemberService;

    @PostMapping("/loginUser")
    public R login(@RequestBody UcenterMember member){
        String token = ucenterMemberService.login(member);
        if ("登录失败".equals(token)) return R.error().message("登录失败");

        return R.ok().data("token", token);
    }

    @PostMapping("/register")
    public R register(@RequestBody RegisterVo registerVo){
        String result = ucenterMemberService.register(registerVo);
        if ("error".equals(result)) return R.error().message("注册失败");
        return R.ok().message("注册成功");
    }

    @GetMapping("/getMemberInfo")
    public R getInfo(HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);

        UcenterMember member = ucenterMemberService.getById(memberId);
        return R.ok().data("member", member);
    }

    @GetMapping("/getMember/{id}")
    public UCenterInfoForm getInfo(@PathVariable String id){
        UcenterMember ucenterMember = ucenterMemberService.getById(id);
        UCenterInfoForm uCenterInfoForm = new UCenterInfoForm();
        BeanUtils.copyProperties(ucenterMember, uCenterInfoForm);
        return uCenterInfoForm;
    }


    @GetMapping("/getRegisterNum/{day}")
    public Integer getRegisterNum(@PathVariable String day){
        Integer count = ucenterMemberService.countRegisterNum(day);
        return count;
    }
}