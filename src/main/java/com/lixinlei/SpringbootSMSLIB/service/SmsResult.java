package com.lixinlei.SpringbootSMSLIB.service;

public class SmsResult {

    private String status;
    private String detail;

    public SmsResult() {}

    public SmsResult(String status, String detail) {
        this.status = status;
        this.detail = detail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}
