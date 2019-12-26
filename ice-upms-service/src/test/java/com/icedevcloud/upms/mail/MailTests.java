package com.icedevcloud.upms.mail;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailTests {

    @Autowired
    private JavaMailSender mailSender;

    @Test
    public void sendMail() {
        SimpleMailMessage message = new SimpleMailMessage();

        for (int i = 0; i < 100; i++) {
            message.setFrom("fanshuye1304@163.com");//发送者.
            message.setTo("867510022@qq.com");//接收者.
            message.setSubject("M");//邮件主题.
            message.setText("这是邮件内容"+i);//邮件内容.

            mailSender.send(message);//发送邮件
            log.info("{}", JSONUtil.toJsonStr(message));
        }

    }

}
