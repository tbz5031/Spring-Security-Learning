package com.tozhang.training.data.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsSender {
    public static final String ACCOUNT_SID =
            "ACc85d94ad511e8f6176e9c3f8f67809df";
    public static final String AUTH_TOKEN =
            "33797e95518968f554b7459daf7f1d8a";

    public static void sendSMSmessage(String phoneNumber) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message
                .creator(new PhoneNumber(phoneNumber), // to
                        new PhoneNumber("+18142818158"), // from
                        "Thank you for registering on Tong.Inc ")
                .create();

        System.out.println(message.getSid());
    }

}
