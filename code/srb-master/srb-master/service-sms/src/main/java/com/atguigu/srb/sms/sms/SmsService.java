package com.atguigu.srb.sms.sms;

import java.util.Map;

public interface SmsService {
    void send(String email, String code, Map<String, Object> param);
}
