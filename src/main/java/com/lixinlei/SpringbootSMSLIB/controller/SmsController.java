package com.lixinlei.SpringbootSMSLIB.controller;

import com.lixinlei.SpringbootSMSLIB.service.SmsCatEnumInstance;
import com.lixinlei.SpringbootSMSLIB.service.SmsResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/sms")
public class SmsController {

    @GetMapping("/send")
    public SmsResult send() {
        SmsResult smsResult = SmsCatEnumInstance
                .getInstance()
                .send("COM3", "185****1624", "Test Message \n" + new Date());
        return smsResult;
    }

}
