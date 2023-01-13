package proyek.akhir.smsreceiverapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SmsReceiverActivity extends AppCompatActivity {

    public static final String EXTRA_SMS_SENDER = "extra_sms_sender";
    public static final String EXTRA_SMS_MESSAGE = "extra_sms_message";
    private TextView tvSmsFrom, tvSmsMessage;
    private Button btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_receiver);

        setTitle("Incoming Message !");

        tvSmsFrom = findViewById(R.id.tv_no);
        tvSmsMessage = findViewById(R.id.tv_message);
        btnClose = findViewById(R.id.btn_close);

        String senderNumber = getIntent().getStringExtra(EXTRA_SMS_SENDER);
        String senderMessage = getIntent().getStringExtra(EXTRA_SMS_MESSAGE);

        tvSmsFrom.setText(senderNumber);
        tvSmsMessage.setText(senderMessage);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}