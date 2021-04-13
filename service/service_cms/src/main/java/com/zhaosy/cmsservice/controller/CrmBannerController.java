package com.zhaosy.cmsservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhaosy.cmsservice.entity.CrmBanner;
import com.zhaosy.cmsservice.service.CrmBannerService;
import com.zhaosy.commenUtils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author zhaosy
 * @since 2021-03-29
 */
@RestController
@RequestMapping("/educms/banner")
//@CrossOrigin
public class CrmBannerController {

    @Autowired
    CrmBannerService bannerService;

    @GetMapping("/getPageAllBanner/{page}/{limit}")
    public R getPageAllBanner(@PathVariable Long page, @PathVariable Long limit){
        Page<CrmBanner> pageBanner = new Page<>(page, limit);
        bannerService.page(pageBanner, null);
        return R.ok().data("items", pageBanner.getRecords()).data("total", pageBanner.getTotal());
    }

    @PostMapping("/addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner){
        bannerService.save(crmBanner);
        return R.ok();
    }

    @PutMapping("/updateBanner")
    public R updateBanner(@RequestBody CrmBanner crmBanner){
        bannerService.updateById(crmBanner);
        return R.ok();
    }

    @DeleteMapping("/deleteBanner/{bannerId}")
    public R deleteBanner(@PathVariable String bannerId){
        bannerService.removeById(bannerId);
        return R.ok();
    }
}

