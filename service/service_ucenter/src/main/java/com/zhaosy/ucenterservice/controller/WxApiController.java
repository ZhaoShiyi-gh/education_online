package com.zhaosy.ucenterservice.controller;

import com.google.gson.Gson;
import com.sun.deploy.net.URLEncoder;
import com.zhaosy.commenUtils.JwtUtils;
import com.zhaosy.ucenterservice.entity.UcenterMember;
import com.zhaosy.ucenterservice.service.UcenterMemberService;
import com.zhaosy.ucenterservice.utils.ConstantPropertiesUtils;
import com.zhaosy.ucenterservice.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

@Controller
@RequestMapping("/api/ucenter/wx")
//@CrossOrigin
public class WxApiController {

    @Autowired
    UcenterMemberService memberService;

    @GetMapping("/callback")
    public String callback(String code, String state, HttpSession session){
        //得到授权临时票据code
        System.out.println(code);
        System.out.println(state);

        //从redis中将state获取出来，和当前传入的state作比较
        //如果一致则放行，如果不一致则抛出异常：非法访问

        //向认证服务器发送请求换取access_token
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
        "?appid=%s" +
        "&secret=%s" +
        "&code=%s" +
        "&grant_type=authorization_code";

        String accessTokenUrl = String.format(baseAccessTokenUrl,
                ConstantPropertiesUtils.APPID,
                ConstantPropertiesUtils.APPSECRET,
                code);

        String result = null;
        try {
            result = HttpClientUtils.get(accessTokenUrl);
            System.out.println("accessToken=============" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //解析json字符串
        Gson gson = new Gson();
        HashMap map = gson.fromJson(result, HashMap.class);
        String accessToken = (String)map.get("access_token");
        String openid = (String)map.get("openid");

        //查询数据库当前用用户是否曾经使用过微信登录
        UcenterMember member = memberService.getByOpenid(openid);
        if(member == null){
            System.out.println("新用户注册");

            //访问微信的资源服务器，获取用户信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
            "?access_token=%s" +
            "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
            String resultUserInfo = null;
            try {
                resultUserInfo = HttpClientUtils.get(userInfoUrl);
                System.out.println("resultUserInfo==========" + resultUserInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //解析json
            HashMap<String, Object> mapUserInfo = gson.fromJson(resultUserInfo, HashMap.class);
            String nickname = (String)mapUserInfo.get("nickname");
            String headimgurl = (String)mapUserInfo.get("headimgurl");

            //向数据库中插入一条记录
            member = new UcenterMember();
            member.setNickname(nickname);
            member.setOpenid(openid);
            member.setAvatar(headimgurl);
            memberService.save(member);
        }

        String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        //TODO 登录
        return "redirect:http://localhost:3000?token="+jwtToken;
    }

    @GetMapping("/login")
    public String getConnection(){

        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        String redirectUrl = ConstantPropertiesUtils.REDIRECTURL;
        String appid = ConstantPropertiesUtils.APPID;
        String APPSECRET = ConstantPropertiesUtils.APPSECRET;
        System.out.println(redirectUrl);
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = String.format(baseUrl, ConstantPropertiesUtils.APPID, redirectUrl, "zhaosy");



        return "redirect:" + url;
    }

}
