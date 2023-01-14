package com.atguigu.srb.sms.controller.api;

import com.atguigu.common.exception.Assert;
import com.atguigu.common.exception.BusinessException;
import com.atguigu.common.result.R;
import com.atguigu.common.result.ResponseEnum;
import com.atguigu.common.util.RandomUtils;
import com.atguigu.common.util.RegexValidateUtils;
import com.atguigu.srb.sms.client.CoreUserInfoClient;
import com.atguigu.srb.sms.sms.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/sms")
@Api(tags = "短信管理")
//@CrossOrigin //跨域   跨域策略更改
@Slf4j
public class ApiSmsController {

    @Resource
    private CoreUserInfoClient coreUserInfoClient;

    @Resource
    private SmsService smsService;
    @Resource
    private RedisTemplate redisTemplate;

    @ApiOperation("获取验证码")
    @GetMapping("/send/{email}")
    public R send(@ApiParam(value = "邮箱地址", required = true)
                  @PathVariable String email){
        //邮件地址校验不能为空
        Assert.notEmpty(email, ResponseEnum.MOBILE_NULL_ERROR);
        //邮件地址格式校验
        Assert.isTrue(RegexValidateUtils.checkEmail(email),ResponseEnum.MOBILE_ERROR);

        //手机号是否注册
        boolean result = coreUserInfoClient.checkMobile(email);
        System.out.println("result = " + result);
        Assert.isTrue(result == false, ResponseEnum.MOBILE_EXIST_ERROR);

        //发送带验证码的邮件
        String code = RandomUtils.getFourBitRandom();
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", ".");
        smsService.send(email, code, map);

        //将验证码存入redis
        try {
            redisTemplate.opsForValue().set("srb:sms:code:" + email, code, 5, TimeUnit.MINUTES);
        } catch (Exception e) {
            throw new BusinessException(ResponseEnum.ERROR);
        }
        return R.ok().message("邮件发送成功");
    }
}
