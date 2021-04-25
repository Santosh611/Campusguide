package com.example.acctu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class BasicMapDemoActivity extends AppCompatActivity {
    private static final String TAG = "BasicMapDemoActivity";

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basicmapdemo);

        btn = findViewById(R.id.btn_mapView);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BasicMapDemoActivity.this, MapViewDemoActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    /**
     * Start the createMapView activity
     */
//    public void createMapView(View view) {
//
//        Log.d(TAG, "createMapView: ");
//        Intent i = new Intent(this, MapViewDemoActivity.class);
//        startActivity(i);
//    }

    /**
     * Start the createMapViewCode activity
     */
//    public void createMapViewCode(View view) {
//        Log.d(TAG, "createMapViewCode: ");
//        Intent i = new Intent(this, MapViewCodeDemoActivity.class);
//        startActivity(i);
//    }

//    /**
//     * Start the createMapFragment activity
//     */
//    public void createMapFragment(View view) {
//        Log.d(TAG, "createMapFragment: ");
//        Intent i = new Intent(this, MapFragmentDemoActivity.class);
//        startActivity(i);
//    }
//
//    /**
//     * Start the createMapFragmentCode activity
//     */
//    public void createMapFragmentCode(View view) {
//        Log.d(TAG, "createMapFragmentCode: ");
//        Intent i = new Intent(this, MapFragmentCodeDemoActivity.class);
//        startActivity(i);
//    }
//
//    /**
//     * Start the createSupportMapFragment activity
//     */
//    public void createSupportMapFragment(View view) {
//        Log.d(TAG, "createSupportMapFragment: ");
//        Intent i = new Intent(this, SupportMapDemoActivity.class);
//        startActivity(i);
//    }
//
//    /**
//     * Start the createSupportMapFragmentCode activity
//     */
//    public void createSupportMapFragmentCode(View view) {
//        Log.d(TAG, "createSupportMapFragmentCode: ");
//        Intent i = new Intent(this, SupportMapCodeDemoActivity.class);
//        startActivity(i);
//    }
}