package com.zhaosy.ucenterservice.mapper;

import com.zhaosy.ucenterservice.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author zhaosy
 * @since 2021-03-31
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {


    public Integer countRegisterNum(String day);
}
