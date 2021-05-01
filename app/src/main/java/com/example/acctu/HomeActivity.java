package com.example.acctu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.BannerAdSize;
import com.huawei.hms.ads.HwAds;
import com.huawei.hms.ads.banner.BannerView;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    String url1 = "https://krct.ac.in/ktgadmin/assets/php/gallery/453571553492041.png";
    String url2 = "https://krct.ac.in/images/6-1.jpg";
    String url3 = "https://4.bp.blogspot.com/--CsADeuG7Ck/WnFlxFItd8I/AAAAAAAAJkg/spjO0vvsgs03Vqh03fK7kZgC9OzbhzbxwCPcBGAYYCw/s1600/KRCT-GATE.jpg";
    private static String adID = "testw6vs28auh3";

//    private ConstraintLayout constLytBanner;

    Button button;
    Button buttonLoc;
    Button buttonmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);
//        dynamicPermission();

        ImageSlider imageSlider = findViewById(R.id.slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://krct.ac.in/ktgadmin/assets/php/gallery/453571553492041.png"));
        slideModels.add(new SlideModel("https://krct.ac.in/images/6-1.jpg"));
        slideModels.add(new SlideModel("https://4.bp.blogspot.com/--CsADeuG7Ck/WnFlxFItd8I/AAAAAAAAJkg/spjO0vvsgs03Vqh03fK7kZgC9OzbhzbxwCPcBGAYYCw/s1600/KRCT-GATE.jpg"));
        imageSlider.setImageList(slideModels, true);



        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.krct);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        HwAds.init(this);

        BannerView bottomBannerView = findViewById(R.id.hw_banner_view);
        bottomBannerView.setBannerAdSize(BannerAdSize.BANNER_SIZE_SMART);
        AdParam adParam = new AdParam.Builder().build();
        bottomBannerView.loadAd(adParam);



        BannerView topBannerView = new BannerView(this);
        topBannerView.setAdId("testw6vs28auh3");
        topBannerView.setBannerAdSize(BannerAdSize.BANNER_SIZE_SMART);
        topBannerView.loadAd(adParam);

        RelativeLayout rootView = findViewById(R.id.root_view);



        button = findViewById(R.id.button);
        buttonLoc = findViewById(R.id.button3);
        buttonmap = findViewById(R.id.button2);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, Scannerinfo.class);
//                startActivity(intent);
//
//            }
//        });
//
//        buttonLoc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, LocationActivity.class);
//                startActivity(intent);
//
//            }
//        });
//
//        buttonmap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, MapViewDemoActivity.class);
//                startActivity(intent);
//
//            }
//        });
//
//    }
//    private void dynamicPermission() {
//        // Dynamically apply for required permissions if the API level is 28 or smaller.
//        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
//            Log.i(TAG, "android sdk <= 28 Q");
//            if (ActivityCompat.checkSelfPermission(this,
//                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                    && ActivityCompat.checkSelfPermission(this,
//                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                String[] strings =
//                        {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
//                ActivityCompat.requestPermissions(this, strings, 1);
//            }
//        } else {
//            // Dynamically apply for required permissions if the API level is greater than 28. The android.permission.ACCESS_BACKGROUND_LOCATION permission is required.
//            if (ActivityCompat.checkSelfPermission(this,
//                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                    && ActivityCompat.checkSelfPermission(this,
//                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                    && ActivityCompat.checkSelfPermission(this,
//                    "android.permission.ACCESS_BACKGROUND_LOCATION") != PackageManager.PERMISSION_GRANTED) {
//                String[] strings = {android.Manifest.permission.ACCESS_FINE_LOCATION,
//                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
//                        "android.permission.ACCESS_BACKGROUND_LOCATION"};
//                ActivityCompat.requestPermissions(this, strings, 2);
//            }
//        }
//    }


    }
}