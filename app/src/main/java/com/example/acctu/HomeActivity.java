package com.example.acctu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.BannerAdSize;
import com.huawei.hms.ads.HwAds;
import com.huawei.hms.ads.banner.BannerView;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity {

    String url1="https://krct.ac.in/ktgadmin/assets/php/gallery/453571553492041.png";
    String url2="https://krct.ac.in/images/6-1.jpg";
    String url3="https://4.bp.blogspot.com/--CsADeuG7Ck/WnFlxFItd8I/AAAAAAAAJkg/spjO0vvsgs03Vqh03fK7kZgC9OzbhzbxwCPcBGAYYCw/s1600/KRCT-GATE.jpg";
    private static String adID ="testw6vs28auh3";

    private ConstraintLayout constLytBanner;

    Button button;
    Button buttonLoc;
    Button buttonmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
        // initializing the slider view.
        SliderView sliderView = findViewById(R.id.slider);
        // adding the urls inside array list
        sliderDataArrayList.add(new SliderData(url1));
        sliderDataArrayList.add(new SliderData(url2));
        sliderDataArrayList.add(new SliderData(url3));
        // passing this array list inside our adapter class.
        SliderAdapter adapter = new SliderAdapter(this, sliderDataArrayList);
        //below method is used to set auto cycle direction in left to right direction you can change according to requirement.
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        //below method is used to setadapter to sliderview.
        sliderView.setSliderAdapter(adapter);
        //below method is use to set scroll time in seconds.
        sliderView.setScrollTimeInSec(3);
        //to set it scrollable automatically we use below method.
        sliderView.setAutoCycle(true);
        //to start autocycle below method is used.
        sliderView.startAutoCycle();
        HwAds.init(this);
        BannerView bottomBannerView = findViewById(R.id.hw_banner_view);
        AdParam adParam = new AdParam.Builder().build();
        bottomBannerView.loadAd(adParam);

        BannerView topBannerView = new BannerView(this);
        topBannerView.setAdId("testw6vs28auh3");
        topBannerView.setBannerAdSize(BannerAdSize.BANNER_SIZE_SMART);
        topBannerView.loadAd(adParam);

        RelativeLayout rootView = findViewById(R.id.root_view);
        rootView.addView(topBannerView);



        button = findViewById(R.id.button);
        buttonLoc = findViewById(R.id.button3);
        buttonmap = findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, Scannerinfo.class);
                startActivity(intent);

            }
        });

        buttonLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LocationActivity.class);
                startActivity(intent);

            }
        });

        buttonmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MapViewDemoActivity.class);
                startActivity(intent);

            }
        });

    }


}