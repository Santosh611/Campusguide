package com.example.acctu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Scannerinfo extends AppCompatActivity {
    ImageButton btni;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scannerinfo);
        btni = findViewById(R.id.ibutton);


        btni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Scannerinfo.this, ScannerActivity.class);
                startActivity(intent);

            }
        });


    }
}