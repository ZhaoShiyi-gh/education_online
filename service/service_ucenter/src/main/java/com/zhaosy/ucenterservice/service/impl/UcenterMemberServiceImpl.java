package com.zhaosy.ucenterservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhaosy.commenUtils.JwtUtils;
import com.zhaosy.commenUtils.MD5;
import com.zhaosy.ucenterservice.entity.UcenterMember;
import com.zhaosy.ucenterservice.entity.Vo.RegisterVo;
import com.zhaosy.ucenterservice.mapper.UcenterMemberMapper;
import com.zhaosy.ucenterservice.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author zhaosy
 * @since 2021-03-31
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String login(UcenterMember member) {

        String mobile = member.getMobile();
        String password = member.getPassword();

        if(mobile == null || password == null) return "登录失败";

        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        UcenterMember m = baseMapper.selectOne(wrapper);
        if(m == null) return "登录失败";
        if(!MD5.encrypt(password).equals(m.getPassword())) return "登录失败";
        if(m.getIsDisabled()) return "登录失败";
        String jwtToken = JwtUtils.getJwtToken(m.getId(), m.getNickname());
        return jwtToken;

    }

    @Override
    public String register(RegisterVo registerVo) {

        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String nickname = registerVo.getNickname();
        String code = registerVo.getCode();

        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)) return "error";

        //数据添加数据库中
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));//密码需要加密的
        member.setIsDisabled(false);//用户不禁用
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        baseMapper.insert(member);
        return "success";
    }

    @Override
    public UcenterMember getByOpenid(String openid) {
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openid);

        UcenterMember member = baseMapper.selectOne(queryWrapper);
        return member;
    }

    @Override
    public Integer countRegisterNum(String day) {
        return baseMapper.countRegisterNum(day);
    }
}
