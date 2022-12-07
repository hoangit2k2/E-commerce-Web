package com.lovepink.test;

public class sendtoCustomerImpl implements SendMessage {
    @Override
    public void SendMessage(String to, String text){
        System.out.println("Sent to Customer " + to + "with Messge" + text);
    }
}
