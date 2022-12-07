package com.lovepink.test;
import com.lovepink.test.SendMessageSucsess;
import com.lovepink.test.SendMessage;
public class SendtoAmin implements SendMessageSucsess {
    private final SendMessage sendMessage;
    public SendtoAmin(SendMessage sdm) {
        this.sendMessage = sdm;
    }
    @Override
    public void sendMessageSucsess(String to, String text){
    this.sendMessage.SendMessage(to,text);
    }
}

