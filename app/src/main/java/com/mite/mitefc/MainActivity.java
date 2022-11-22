package com.mite.mitefc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static final String Error_Detected = "No NFC Tag Detected";
    public static final String Write_Success = "Text Written Successfully";
    public static final String Write_Error = "Error during Writing, Try Again";
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    IntentFilter writingTagFilters;
    boolean writeMode;
    Tag myTag;
    Context context;

    EditText writeText;
    TextView readText;
    Button writeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        writeText = findViewById(R.id.writeText);
        readText = findViewById(R.id.readText);
        writeBtn = findViewById(R.id.writeBtn);
        context = this;

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter == null) {
            Toast.makeText(context, "This Device Does not Support NFC", Toast.LENGTH_SHORT).show();
            finish();
        }

        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(myTag == null) {
                        Toast.makeText(context,Error_Detected, Toast.LENGTH_SHORT).show();
                    } else {
                        write(writeText.toString().toUpperCase().trim(), myTag);
                        Toast.makeText(context, Write_Success, Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    Toast.makeText(context, Write_Error, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (FormatException e) {
                    Toast.makeText(context, Write_Error, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        readfromIntent(getIntent());
        pendingIntent = PendingIntent.getActivity(this, 0,new Intent(this,getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        

    }
}