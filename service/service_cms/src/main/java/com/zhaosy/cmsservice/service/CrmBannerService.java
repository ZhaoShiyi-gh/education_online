package com.zhaosy.cmsservice.service;

import com.zhaosy.cmsservice.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author zhaosy
 * @since 2021-03-29
 */
public interface CrmBannerService extends IService<CrmBanner> {

    @Cacheable(value = "banner", key = "'selectIndexList'")
    List<CrmBanner> getAllBanner();
}
