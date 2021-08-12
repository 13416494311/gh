package com.ruoyi.project.democracy.controller;

import com.ruoyi.common.utils.RSAEncrypt;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.config.TduckConfig;
import com.ruoyi.framework.web.domain.AjaxResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;

/**
 * @author:万登枫
 * @date: 2021/8/12
 */
@RestController
@RequestMapping("/tduck")
public class TduckController {

    @GetMapping("/getTduckLogin")
    public AjaxResult getTduckLogin() throws Exception {

        String  tduckLoginUrl = String.format(
                TduckConfig.getLoginUrl(),
                URLEncoder.encode(
                        RSAEncrypt.encrypt(
                                SecurityUtils.getLoginUser().getUser().getUserName(),
                                TduckConfig.getPublicKey()
                        ),
                        "UTF-8")
        );
        return AjaxResult.success(tduckLoginUrl);
    }
}
