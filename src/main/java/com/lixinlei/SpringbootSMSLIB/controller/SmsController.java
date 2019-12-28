package com.lixinlei.SpringbootSMSLIB.controller;

import com.lixinlei.SpringbootSMSLIB.service.SmsCatEnumInstance;
import com.lixinlei.SpringbootSMSLIB.service.SmsResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/sms")
public class SmsController {

    @GetMapping("/send/{port}/{receiver}/{content}")
    public SmsResult send(@PathVariable("port") String port,
                          @PathVariable("receiver") String receiver,
                          @PathVariable("content") String content) {
        SmsResult smsResult1 = SmsCatEnumInstance
                .getInstance()
                .send(port, receiver, content + " \n" + new Date());

        SmsCatEnumInstance.getInstance().stop(new Exception());

        SmsResult smsResult2 = SmsCatEnumInstance
                .getInstance()
                .send(port, receiver, content + new Date());
        return smsResult2;
    }

    @GetMapping("/send/{batch}/{port}/{receiver}/{content}")
    public List<SmsResult> sendBatch(@PathVariable("batch") int batch,
                               @PathVariable("port") String port,
                               @PathVariable("receiver") String receiver,
                               @PathVariable("content") String content) {
        List<SmsResult> list = new ArrayList<>(batch);
        for(int i = 0; i < batch; i++) {
            SmsResult smsResult = SmsCatEnumInstance.getInstance().send(port, receiver, content + " \n" + new Date());
            list.add(smsResult);
        }
        return list;
    }

}
