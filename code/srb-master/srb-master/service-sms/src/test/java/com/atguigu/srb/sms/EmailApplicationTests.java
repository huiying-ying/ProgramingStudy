package com.atguigu.srb.sms;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Date;

@SpringBootTest
class EmailApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     *  发送简单邮件
     */
    @Test
    public void sendSimpleMail() {
        SimpleMailMessage msg = new SimpleMailMessage();    //构建一个邮件对象
        msg.setSubject("这是一封测试邮件33"); // 设置邮件主题
        msg.setFrom("319111721@qq.com"); // 设置邮箱发送者
        msg.setTo("shuoyi05@gmail.com"); // 设置邮件接收者，可以有多个接收者
        msg.setSentDate(new Date());    // 设置邮件发送日期
        msg.setText("这是测试邮件的正文");   // 设置邮件的正文
        javaMailSender.send(msg);
    }

}
