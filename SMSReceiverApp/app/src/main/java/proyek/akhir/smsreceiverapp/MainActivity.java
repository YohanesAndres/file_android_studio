package proyek.akhir.smsreceiverapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    public static final String EXTRA_SMS_SENDER = "extra_sms_sender";
    public static final String EXTRA_SMS_MESSAGE = "extra_sms_message";
    private static final int SMS_REQUEST_CODE = 101;
    private TextView tvSmsFrom, tvSmsContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, SMS_REQUEST_CODE);
        }

        tvSmsContent = findViewById(R.id.tv_content);
        tvSmsFrom = findViewById(R.id.tv_sms_from);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if(!intent.hasExtra(EXTRA_SMS_SENDER) && !intent.hasExtra(EXTRA_SMS_MESSAGE))
        {
            return;
        }
        String smsSender = intent.getExtras().getString(EXTRA_SMS_SENDER);
        String smsContent = intent.getExtras().getString(EXTRA_SMS_MESSAGE);

        tvSmsFrom.setText(smsSender);
        tvSmsContent.setText(smsContent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == SMS_REQUEST_CODE)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "SMS Receiver permission Accepted", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "SMS Receiver permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}