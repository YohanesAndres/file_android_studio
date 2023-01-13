package proyek.akhir.smsreceiverapp;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = SmsReceiver.class.getSimpleName();

    public SmsReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        try {
                if(bundle != null)
                {
                    Object[] pdusObj = (Object[]) bundle.get("pdus");
                    if(pdusObj != null)
                    {
                        for(Object aPdusObj : pdusObj)
                        {
                            SmsMessage currentMessage = getIncomingMessage(aPdusObj, bundle);
                            String senderNum = currentMessage.getDisplayOriginatingAddress();
                            String message = currentMessage.getDisplayMessageBody();

                            Log.d(TAG, "senderNumber : "+ senderNum + "; Message : " + message);

                            Intent directIntent = new Intent(context, MainActivity.class);
                            directIntent.putExtra(MainActivity.EXTRA_SMS_SENDER, senderNum);
                            directIntent.putExtra(MainActivity.EXTRA_SMS_MESSAGE, message);

                            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, directIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                            pendingIntent.send();

                            Intent showSmsIntent = new Intent(context, SmsReceiverActivity.class);
                            showSmsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            showSmsIntent.putExtra(SmsReceiverActivity.EXTRA_SMS_SENDER, senderNum);
                            showSmsIntent.putExtra(SmsReceiverActivity.EXTRA_SMS_MESSAGE, message);
                            context.startActivity(showSmsIntent);
                        }
                    }
                    else
                    {
                        Log.d(TAG,"onReceive: SMS is Null");

                    }
                }
        }catch (Exception e)
        {
            Log.d(TAG, "Exception smsReceiver : "+ e);
        }
    }

    private SmsMessage getIncomingMessage(Object object, Bundle bundle)
    {
        SmsMessage currentSMS;
        if(Build.VERSION.SDK_INT >= 23)
        {
            String format = bundle.getString("format");
            currentSMS = SmsMessage.createFromPdu((byte[]) object, format);
        }
        else
        {
            currentSMS = SmsMessage.createFromPdu((byte[]) object);
        }
        return currentSMS;
    }
}
