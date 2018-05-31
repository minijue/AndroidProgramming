package com.example.wangjue.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String str = "SMS : ";
        if (bundle != null) {
            msgs = Telephony.Sms.Intents.getMessagesFromIntent(intent);
            for (int i = 0; i < msgs.length; i++) {
                str += msgs[i].getMessageBody().toString();
            }

            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
        }
    }
}
