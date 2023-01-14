package com.atguigu.srb.sms.sms.impl;

import com.atguigu.common.exception.BusinessException;
import com.atguigu.common.result.ResponseEnum;
import com.atguigu.srb.sms.sms.SmsService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
@Slf4j
public class SmsServiceImpl implements SmsService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void send(String email, String code, Map<String, Object> param) {
        SimpleMailMessage msg = new SimpleMailMessage();    //构建一个邮件对象
        msg.setSubject("尚融宝验证码"); // 设置邮件主题
        msg.setFrom("319111721@qq.com"); // 设置邮箱发送者
        msg.setTo(email); // 设置邮件接收者，可以有多个接收者
        msg.setSentDate(new Date());    // 设置邮件发送日期
        Gson gson = new Gson();
        String jsonParam = gson.toJson(param);
        msg.setText("【尚融宝】验证码"+code+jsonParam); // 设置邮件的正文,发送验证码+发送提示消息
        try {
            javaMailSender.send(msg);
        } catch (MailException e) {
            log.error("邮件发送失败:");
            throw new BusinessException(ResponseEnum.ALIYUN_SMS_ERROR,e);
        }
    }
}
