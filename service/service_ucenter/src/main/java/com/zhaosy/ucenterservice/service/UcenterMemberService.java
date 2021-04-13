package com.zhaosy.ucenterservice.service;

import com.zhaosy.ucenterservice.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhaosy.ucenterservice.entity.Vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author zhaosy
 * @since 2021-03-31
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

    String register(RegisterVo registerVo);

    UcenterMember getByOpenid(String openid);

    Integer countRegisterNum(String day);
}
