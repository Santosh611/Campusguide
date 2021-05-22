package com.example.acctu;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.HuaweiMapOptions;
import com.huawei.hms.maps.MapView;
import com.huawei.hms.maps.MapsInitializer;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.huawei.hms.maps.model.CameraPosition;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.LatLngBounds;
import com.huawei.hms.maps.model.Marker;
import com.huawei.hms.maps.model.MarkerOptions;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_WIFI_STATE;

public class Location1<mGroundOverlay> extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MapViewDemoActivity";
    // Huawei map.
    private HuaweiMap hMap;

    private Marker mMarker;

    Button btPicker;
    TextView textView;
    int PLACE_PICKER_REQUEST = 1;



    private MapView mMapView;

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";


    SupportMapFragment mSupportMapFragment;
    // Set initial camera attributes.
    CameraPosition cameraPosition =
            CameraPosition.builder().target(new LatLng(48.893478, 2.334595)).zoom(10).bearing(45).tilt(20).build();
    // Construct the target area of the camera.
    LatLng southwest = new LatLng(47.893478, 3.334595);
    LatLng northeast = new LatLng(49.893478, 1.334595);
    LatLngBounds latLngBounds = new LatLngBounds(southwest, northeast);

    HuaweiMapOptions options = new HuaweiMapOptions();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location1);
        // Obtain a MapView instance.
        mMapView = findViewById(R.id.mapView);
        Bundle mapViewBundle = null;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.i(TAG, "sdk >= 23 M");
            // Check whether your app has the specified permission and whether the app operation corresponding to the permission is allowed.
            if (ActivityCompat.checkSelfPermission(this,
                    ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // Request permissions for your app.
                String[] strings =
                        {ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
                // Request permissions.
                ActivityCompat.requestPermissions(this, strings, 1);
            }
        }


        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        // Please replace Your API key with the API key in
        // agconnect-services.json.
        MapsInitializer.setApiKey("CgB6e3x9DJc24fwQTr+Hxc0ekmd0PvQW8BV0Jyr7f21uWeLQ+PooNQsayXeWa02u/3bXLIoMMYO+K/JSmI2RU6k2");
        mMapView.onCreate(mapViewBundle);
        // Obtain a map instance.
        mMapView.getMapAsync(this);
    }


    @RequiresPermission(allOf = {ACCESS_FINE_LOCATION, ACCESS_WIFI_STATE})

    @Override
    public void onMapReady(HuaweiMap map) {
        // Obtain a map instance from callback.
        Log.d(TAG, "onMapReady: ");
        hMap = map;

        hMap.setMyLocationEnabled(true);
        // Enable the my-location icon.
        hMap.getUiSettings().setMyLocationButtonEnabled(true);

        hMap.setOnMapClickListener(new HuaweiMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(latLng.latitude+" : " + latLng.longitude);
                hMap.clear();
                hMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                hMap.addMarker(markerOptions);
                Toast.makeText(getApplicationContext(), "onMapClick:" + latLng.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }



    @Override
    protected void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }


    public void addMarker(View view) {
        if (null != mMarker) {
            mMarker.remove();
        }
        MarkerOptions options = new MarkerOptions()
                .position(new LatLng(48.893478, 2.334595))
                .title("Hello Huawei Map")
                .snippet("This is a snippet!");
        mMarker = hMap.addMarker(options);
    }





    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }
    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
