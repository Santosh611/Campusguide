package com.example.acctu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class DepartmentsActivity extends AppCompatActivity {

    Button buttoncs;
    Button buttonec;
    Button buttonee;
    Button buttoncivi;
    Button buttonmec;
    Button buttona;
    Button buttones;
    Button buttonpe;
    Button buttonvs;
    Button buttoncse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departments);

        buttoncs = findViewById(R.id.depbtn);
        buttonec = findViewById(R.id.ecebtn);
        buttonee = findViewById(R.id.eeebtn);
        buttoncivi = findViewById(R.id.civilbtn);
        buttonmec =  findViewById(R.id.mechbtn);
        buttona =  findViewById(R.id.aibtn);
        buttones = findViewById(R.id.esbtn);
        buttonpe = findViewById(R.id.pebtn);
        buttoncse = findViewById(R.id.csebtn1);
        buttonvs = findViewById(R.id.vsbtn);


        buttoncs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://krct.ac.in/about.php?cat=1&id=45";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });

        buttoncse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://krct.ac.in/about.php?cat=1&id=45";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });


        buttonec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://krct.ac.in/about.php?cat=1&id=47";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });

        buttonee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://krct.ac.in/about.php?cat=1&id=46";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });

        buttoncivi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://krct.ac.in/about.php?cat=1&id=44";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });

        buttonmec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://krct.ac.in/about.php?cat=1&id=48";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });

        buttona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://krct.ac.in/about.php?cat=1&id=97";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });

        buttones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(DepartmentsActivity.this);
                builder.setTitle("Site Under Contruction");
                builder.setMessage("Check back after few days");
                builder.show();
            }
        });

        buttonpe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(DepartmentsActivity.this);
                builder.setTitle("Site Under Contruction");
                builder.setMessage("Check back after few days");
                builder.show();
            }
        });


        buttonvs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(DepartmentsActivity.this);
                builder.setTitle("Site Under Contruction");
                builder.setMessage("Check back after few days");
                builder.show();

            }
        });






    }
}