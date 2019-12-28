package com.lixinlei.SpringbootSMSLIB.service;

import org.smslib.*;
import org.smslib.modem.SerialModemGateway;

import java.io.IOException;

public enum SmsCatEnumInstance {

    INSTANCE {

        private volatile boolean started = false;

        protected void start(String port) throws InterruptedException, SMSLibException, IOException {
            if (started) {
                return;
            }

            SmsCatEnumInstance.OutboundNotification outboundNotification
                    = new SmsCatEnumInstance.OutboundNotification();

            SerialModemGateway gateway = new SerialModemGateway(
                    "modem.com3",
                    port,
                    9600,
                    "null",
                    "null");
            gateway.setInbound(false);
            gateway.setOutbound(true);
            gateway.setSimPin("0000");

            org.smslib.Service.getInstance().setOutboundMessageNotification(outboundNotification);
            org.smslib.Service.getInstance().addGateway(gateway);
            org.smslib.Service.getInstance().startService();

            started = true;
            System.out.println("SMS CAT Service Started...");
        }

        public synchronized SmsResult send(String port, String receiver, String content) {
            try {
                start(port);
            } catch (GatewayException e) {
                return MessageWrapper.doWrapError(e);
            } catch (Exception e) {
                return MessageWrapper.doWrapError(e);
            }

            OutboundMessage msg = new OutboundMessage(receiver, content);
            try {
                msg.setEncoding(Message.MessageEncodings.ENCUCS2);
                org.smslib.Service.getInstance().sendMessage(msg);
            } catch (Exception e) {
                stop(e);
            }
            return MessageWrapper.doWrap(msg);
        }

        public void stop(Exception e) {
            try {
                Service.getInstance().stopService();
                SerialModemGateway gateway = (SerialModemGateway) Service.getInstance().getGateway("modem.com3");
                gateway.stopGateway();
                Service.getInstance().removeGateway(gateway);
                started = false;
                System.out.println(e.getMessage());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

    };

    protected abstract void start(String port) throws InterruptedException, SMSLibException, IOException;
    public abstract SmsResult send(String port, String receiver, String content);
    public abstract void stop(Exception e);

    public static SmsCatEnumInstance getInstance() {
        return INSTANCE;
    }

    private class OutboundNotification implements IOutboundMessageNotification {
        @Override
        public void process(AGateway aGateway, OutboundMessage outboundMessage) {
            System.out.println("OutboundNotification aGateway.getGatewayId(): " + aGateway.getGatewayId());
            System.out.println("OutboundNotification outboundMessage:" + outboundMessage);
        }
    }

}

class MessageWrapper {

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
