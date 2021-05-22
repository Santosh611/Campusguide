package com.example.acctu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.BannerAdSize;
import com.huawei.hms.ads.HwAds;
import com.huawei.hms.ads.banner.BannerView;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.common.ResolvableApiException;
import com.huawei.hms.location.FusedLocationProviderClient;
import com.huawei.hms.location.LocationCallback;
import com.huawei.hms.location.LocationRequest;
import com.huawei.hms.location.LocationResult;
import com.huawei.hms.location.LocationServices;
import com.huawei.hms.location.LocationSettingsRequest;
import com.huawei.hms.location.LocationSettingsResponse;
import com.huawei.hms.location.LocationSettingsStatusCodes;
import com.huawei.hms.location.SettingsClient;
import com.huawei.hms.maps.model.LatLng;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.shape.ShapeType;
import co.mobiwise.materialintro.view.MaterialIntroView;



public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
//    String url1 = "https://krct.ac.in/ktgadmin/assets/php/gallery/453571553492041.png";
//    String url2 = "https://krct.ac.in/images/6-1.jpg";
//    String url3 = "https://4.bp.blogspot.com/--CsADeuG7Ck/WnFlxFItd8I/AAAAAAAAJkg/spjO0vvsgs03Vqh03fK7kZgC9OzbhzbxwCPcBGAYYCw/s1600/KRCT-GATE.jpg";
    private static String adID = "testw6vs28auh3";


    Button button;
    Button buttonLoc;
    Button buttonfac;
    Button buttoncont;
    Button buttondep;
//    private static final int requestCode = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        dynamicPermission();


//        ActivityCompat.requestPermissions(HomeActivity.this, new String[] {Manifest.permission.CAMERA}, requestCode);

        ImageSlider imageSlider = findViewById(R.id.slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://krct.ac.in/ktgadmin/assets/php/gallery/453571553492041.png"));
        slideModels.add(new SlideModel("https://krct.ac.in/images/6-1.jpg"));
        slideModels.add(new SlideModel("https://4.bp.blogspot.com/--CsADeuG7Ck/WnFlxFItd8I/AAAAAAAAJkg/spjO0vvsgs03Vqh03fK7kZgC9OzbhzbxwCPcBGAYYCw/s1600/KRCT-GATE.jpg"));
        slideModels.add(new SlideModel("https://i.ytimg.com/vi/CZrztf5uq-U/maxresdefault.jpg"));
        slideModels.add(new SlideModel("https://www.campusoption.com/images/colleges/gallery/30_11_17_102939_sport-large2.jpg"));
        slideModels.add(new SlideModel("https://www.campusoption.com/images/colleges/gallery/30_11_17_110510_Sport.jpg"));




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




        button = findViewById(R.id.scanbtn);
        new MaterialIntroView.Builder(this)
                .enableDotAnimation(true)
                .enableIcon(false)
                .setFocusGravity(FocusGravity.CENTER)
                .setFocusType(Focus.MINIMUM)
                .setDelayMillis(500)
                .enableFadeAnimation(true)
                .performClick(false)
                .setInfoText("This scanner can be used to scan QR around the campus to Obtain specific Info")
                .setShape(ShapeType.RECTANGLE)
                .setTarget(button)
                .setUsageId("intro_card")
                .setMaskColor(getResources().getColor(R.color.greenTransparent))//THIS SHOULD BE UNIQUE ID
                .show();


        buttonLoc = findViewById(R.id.locatebtn);
        new MaterialIntroView.Builder(this)
                .enableDotAnimation(true)
                .enableIcon(false)
                .setFocusGravity(FocusGravity.CENTER)
                .setFocusType(Focus.MINIMUM)
                .setDelayMillis(2000)
                .enableFadeAnimation(true)
                .performClick(false)
                .setInfoText("Helps you navigate to the campus")
                .setShape(ShapeType.RECTANGLE)
                .setTarget(buttonLoc)
                .setUsageId("Location")
//                .setMaskColor(getResources().getColor(R.color.greenTransparent))//THIS SHOULD BE UNIQUE ID
                .show();
        buttoncont = findViewById(R.id.contactbtn);
        new MaterialIntroView.Builder(this)
                .enableDotAnimation(true)
                .enableIcon(false)
                .setFocusGravity(FocusGravity.CENTER)
                .setFocusType(Focus.MINIMUM)
                .setDelayMillis(4000)
                .enableFadeAnimation(true)
                .performClick(false)
                .setInfoText("To get more contact info")
                .setShape(ShapeType.RECTANGLE)
                .setTarget(buttoncont)
                .setUsageId("Contact")
//                .setMaskColor(getResources().getColor(R.color.greenTransparent))//THIS SHOULD BE UNIQUE ID
                .show();
        buttonfac = findViewById(R.id.facultybtn);
        new MaterialIntroView.Builder(this)
                .enableDotAnimation(true)
                .enableIcon(false)
                .setFocusGravity(FocusGravity.CENTER)
                .setFocusType(Focus.MINIMUM)
                .setDelayMillis(6000)
                .enableFadeAnimation(true)
                .performClick(false)
                .setInfoText("Vision and Mission")
                .setShape(ShapeType.RECTANGLE)
                .setTarget(buttonfac)
                .setUsageId("Vision")
                .setMaskColor(getResources().getColor(R.color.greenTransparent))//THIS SHOULD BE UNIQUE ID
                .show();
        buttondep = findViewById(R.id.depbtn);
        new MaterialIntroView.Builder(this)
                .enableDotAnimation(true)
                .enableIcon(false)
                .setFocusGravity(FocusGravity.CENTER)
                .setFocusType(Focus.MINIMUM)
                .setDelayMillis(8000)
                .enableFadeAnimation(true)
                .performClick(false)
                .setInfoText("List of Departments present in the college")
                .setShape(ShapeType.RECTANGLE)
                .setTarget(buttondep)
                .setUsageId("Departments")
//                .setMaskColor(getResources().getColor(R.color.greenTransparent))//THIS SHOULD BE UNIQUE ID
                .show();


        buttoncont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(HomeActivity.this);
                builder.setTitle("Contact Details");
                builder.setMessage("Phone Number: 0431 - 2670799\n"+"\nLong press to view the website");
                builder.show();



            }
        });

        buttoncont.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v){
                String url = "https://krct.ac.in/contact.php";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                return false;

            }

        });




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(HomeActivity.this);

                builder.setTitle("Why Scanner ?");
                builder.setMessage("Use us to scan QRs present around the campus to get more info, Long press to the button to launch Scanner");
                builder.show();


            }
        });

        button.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v){

                Intent intent = new Intent(HomeActivity.this, ScannerActivity.class);
                startActivity(intent);
                return false;
            }

        });

        buttonfac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(HomeActivity.this);
                builder.setTitle("Vision");
                builder.setMessage("To serve the society by offering top-notch technical education on par with global standards.\n"+"\n"+"Long Press the Button to visit the website");
                builder.show();

            }
        });

        buttonfac.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v){
                String url = "https://krct.ac.in/about.php?cat=1&id=103";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                return false;
            }

        });


        buttonLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(HomeActivity.this);
                builder.setTitle("Locate Us");
                builder.setMessage("K Ramakrishnan College of Technology\n"+"Kariyamanikam Road, Samayapuram,\n" +
                        "Trichy – 621112.\n"+"\n" +"Long Press the button for Navigation");
                builder.show();



            }
        });

        buttonLoc.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v){
                Intent intent = new Intent(HomeActivity.this, MapsActivity.class);
                startActivity(intent);
                return false;

            }

        });

        buttondep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, DepartmentsActivity.class);
                startActivity(intent);
            }
        });

    }



    private void dynamicPermission() {
        // Dynamically apply for required permissions if the API level is 28 or smaller.
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            Log.i(TAG, "android sdk <= 28 Q");
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                String[] strings =
                        {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
                ActivityCompat.requestPermissions(this, strings, 1);
            }
        } else {
            // Dynamically apply for required permissions if the API level is greater than 28. The android.permission.ACCESS_BACKGROUND_LOCATION permission is required.
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this,
                    "android.permission.ACCESS_BACKGROUND_LOCATION") != PackageManager.PERMISSION_GRANTED) {
                String[] strings = {android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        "android.permission.ACCESS_BACKGROUND_LOCATION"};
                ActivityCompat.requestPermissions(this, strings, 2);
            }
        }
    }

}
