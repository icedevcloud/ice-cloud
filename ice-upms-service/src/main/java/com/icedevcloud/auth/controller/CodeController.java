package com.icedevcloud.auth.controller;

import com.icedevcloud.common.core.captcha.Captcha;
import com.icedevcloud.common.core.captcha.SpecCaptcha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 图像验证码和短信验证码
 *
 * @author XiaoBingBy
 * @date 2018-12-17 22:59
 * @since 1.0
 */
@RequestMapping("/oauth")
@RestController
public class CodeController {

    private static final Logger logger = LoggerFactory.getLogger(CodeController.class);

    private static final String UNKNOWN = "unknown";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final String codeKey = "bing:image_code:";

    @GetMapping("/image-code/{randomNumber}")
    public void imageCode(@PathVariable(name = "randomNumber", required = true) String randomNumber,
                          HttpServletRequest request, HttpServletResponse response) {
        // 获取IP
        String ipAddress = getIPAddress(request);
        SpecCaptcha specCaptcha = new SpecCaptcha();
        specCaptcha.setCharType(Captcha.TYPE_ONLY_UPPER);
        String code = specCaptcha.text();
        // 存储验证码到Redis
        stringRedisTemplate.opsForValue().set(codeKey + ipAddress + ":" + randomNumber, code, 60 * 30, TimeUnit.SECONDS);
        if (logger.isDebugEnabled()) {
            logger.debug("ipAddress {} code {}", ipAddress, code);
        }
        try {
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Content-Type", "image/png");
            specCaptcha.out(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/smsCode/{phone}")
    public void smsCode(@PathVariable(name = "phone", required = true) Integer phone) {

    }

    /**
     * 获取IP地址
     *
     * @param request
     * @return
     */
    private String getIPAddress(HttpServletRequest request) {
        String ip = null;

        //X-Forwarded-For：Squid 服务代理
        String ipAddresses = request.getHeader("X-Forwarded-For");

        if (ipAddresses == null || ipAddresses.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddresses)) {
            //Proxy-Client-IP：apache 服务代理
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddresses)) {
            //WL-Proxy-Client-IP：weblogic 服务代理
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddresses)) {
            //HTTP_CLIENT_IP：有些代理服务器
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddresses)) {
            //X-Real-IP：nginx服务代理
            ipAddresses = request.getHeader("X-Real-IP");
        }

        //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ipAddresses != null && ipAddresses.length() != 0) {
            ip = ipAddresses.split(",")[0];
        }

        //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddresses)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
