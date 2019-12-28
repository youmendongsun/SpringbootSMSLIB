package com.lixinlei.SpringbootSMSLIB.service;

import org.smslib.OutboundMessage;

public class MessageWrapper {

    public static SmsResult doWrap(OutboundMessage msg) {
        SmsResult smsResult = new SmsResult();
        smsResult.setStatus(Status.OK.getStatus());
        smsResult.setDetail(msg.getMessageStatus().toString());
        return smsResult;
    }

    public static SmsResult doWrapError(Exception e) {
        SmsResult smsResult = new SmsResult();
        smsResult.setStatus(Status.ERROR.getStatus());
        smsResult.setDetail(e.getMessage());
        return smsResult;
    }

    private enum Status {
        OK("ok"),
        ERROR("error"),
        ;

        private String status;

        Status(String value) {
            this.status = value;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

}
