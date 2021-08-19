package com.ruoyi.project.system.controller;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.utils.RSAEncrypt;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.config.RSAConfig;
import com.ruoyi.framework.redis.RedisCache;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.party.domain.DjPartyMember;
import com.ruoyi.project.party.service.IDjPartyMemberService;
import com.ruoyi.project.system.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/system/register")
public class SysRegisterController {

    @Autowired
    private IDjPartyMemberService djPartyMemberService;
    @Autowired
    private RedisCache redisCache;

    /**
     * 手机app 会员注册
     */
    @Log(title = "会员注册", businessType = BusinessType.INSERT)
    @PostMapping("/addForApp")
    public AjaxResult register(@RequestBody Map<String,Object> params)
    {
        String verifyKey = Constants.CAPTCHA_CODE_KEY + params.get("uuid").toString() ;
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if(!params.get("code").toString().equalsIgnoreCase(captcha)){
            return AjaxResult.success("验证码错误");
        }
        String password =  params.get("password").toString();
        //会员变更表
        DjPartyMember djPartyMember = JSON.parseObject(JSON.toJSONString(params), DjPartyMember.class);
        djPartyMember.setPartyOrgId((long) 52);
        return AjaxResult.success(djPartyMemberService.insertDjPartyMemberForApp(djPartyMember,password));
    }


    @PostMapping("/checkPartyMemberUnique")
    public AjaxResult checkPartyMemberUnique(@Validated @RequestBody DjPartyMember djPartyMember) throws IOException
    {

        if (UserConstants.NOT_UNIQUE.equals(djPartyMemberService.checkPartyMemberNameUnique(djPartyMember)))
        {
            return AjaxResult.success("姓名已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(djPartyMemberService.checkPartyMemberMobileUnique(djPartyMember)))
        {
            return AjaxResult.success("手机号已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(djPartyMemberService.checkPartyMemberEmailUnique(djPartyMember)))
        {
            return AjaxResult.success("Email已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(djPartyMemberService.checkPartyMemberIdentityCardUnique(djPartyMember)))
        {
            return AjaxResult.success("身份证号已存在");
        }
        return AjaxResult.success();
    }

    @Log(title = "会员PC端注册", businessType = BusinessType.INSERT)
    @PostMapping("/registerMember")
    public AjaxResult registerMember(@RequestBody SysUser sysUser) throws Exception {
        DjPartyMember djPartyMember = new DjPartyMember();
        djPartyMember.setMemberName(sysUser.getUserName());
        djPartyMember.setMobile(sysUser.getPhonenumber());
        return AjaxResult.success(djPartyMemberService.insertDjPartyMemberForApp(djPartyMember,
                RSAEncrypt.decrypt(sysUser.getPassword(), RSAConfig.getPrivateKey())));
    }
}
