package com.zhaosy.cmsservice.controller;

import com.zhaosy.cmsservice.entity.CrmBanner;
import com.zhaosy.cmsservice.service.CrmBannerService;
import com.zhaosy.commenUtils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/educms/bannerFront")
//@CrossOrigin
public class CrmBannerFrontController {

    @Autowired
    CrmBannerService bannerService;

    @GetMapping("/getAllBanner")
    public R getAllBanner(){
        List<CrmBanner> banners = bannerService.getAllBanner();
        return R.ok().data("items", banners);
    }
}
