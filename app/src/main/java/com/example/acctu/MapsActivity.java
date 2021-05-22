package com.example.acctu;
//

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentSender;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.acctu.utils.MapUtils;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.common.ResolvableApiException;
import com.huawei.hms.kit.awareness.capture.WeatherStatusResponse;
import com.huawei.hms.kit.awareness.status.WeatherStatus;
import com.huawei.hms.kit.awareness.status.weather.Situation;
import com.huawei.hms.kit.awareness.status.weather.WeatherSituation;
import com.huawei.hms.location.FusedLocationProviderClient;
import com.huawei.hms.location.LocationAvailability;
import com.huawei.hms.location.LocationCallback;
import com.huawei.hms.location.LocationRequest;
import com.huawei.hms.location.LocationResult;
import com.huawei.hms.location.LocationServices;
import com.huawei.hms.location.LocationSettingsRequest;
import com.huawei.hms.location.LocationSettingsResponse;
import com.huawei.hms.location.LocationSettingsStatusCodes;
import com.huawei.hms.location.SettingsClient;
import com.huawei.hms.maps.CameraUpdate;
import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.HuaweiMapOptions;
import com.huawei.hms.maps.MapView;
import com.huawei.hms.maps.MapsInitializer;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.huawei.hms.maps.model.BitmapDescriptorFactory;
import com.huawei.hms.maps.model.CameraPosition;
import com.huawei.hms.maps.model.Circle;
import com.huawei.hms.maps.model.CircleOptions;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.LatLngBounds;
import com.huawei.hms.maps.model.Marker;
import com.huawei.hms.maps.model.MarkerOptions;
import com.huawei.hms.maps.model.Polyline;
import com.huawei.hms.maps.model.PolylineOptions;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hms.kit.awareness.Awareness;
// Import the location capture-related classes.
import android.location.Location;

import com.huawei.hms.kit.awareness.capture.LocationResponse;
// Import the geofence-related classes.
import com.huawei.hms.kit.awareness.barrier.AwarenessBarrier;
import com.huawei.hms.kit.awareness.barrier.BarrierStatus;
import com.huawei.hms.kit.awareness.barrier.LocationBarrier;
import com.huawei.hms.kit.awareness.barrier.BarrierUpdateRequest;

import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_WIFI_STATE;


public class MapsActivity<mGroundOverlay> extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MapActivity";
    // Huawei map.
    private HuaweiMap hMap;

    private Marker mMarker;
    private Marker marker1;
    Polyline mPolyline;
    Circle mCircle;
//    LatLng latlngcurrent;


    LocationRequest mLocationRequest;

    double strresult, strresult1;


    private MapView mMapView;
    LocationCallback mLocationCallback = null;
    SettingsClient settingsClient;
    private FusedLocationProviderClient fusedLocationProviderClient;

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";


    SupportMapFragment mSupportMapFragment;
    CameraPosition cameraPosition =
            CameraPosition.builder().target(new LatLng(10.929247440022037, 78.73805378269797)).zoom(100).bearing(45).tilt(20).build();


    HuaweiMapOptions options = new HuaweiMapOptions();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);




        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            Log.i(TAG, "sdk < 28 Q");
            if (ActivityCompat.checkSelfPermission(MapsActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(MapsActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                String[] strings =
                        {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
                ActivityCompat.requestPermissions(MapsActivity.this, strings, 1);
            }
        } else {
            if (ActivityCompat.checkSelfPermission(MapsActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(MapsActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(MapsActivity.this,
                    "android.permission.ACCESS_BACKGROUND_LOCATION") != PackageManager.PERMISSION_GRANTED) {
                String[] strings = {android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        "android.permission.ACCESS_BACKGROUND_LOCATION"};
                ActivityCompat.requestPermissions(MapsActivity.this, strings, 2);
            }
        }

//        location();
        initialLocation();
//        settingsClient = LocationServices.getSettingsClient(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);



        mMapView = findViewById(R.id.mapView);
        Bundle mapViewBundle = null;

        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }


        mMapView.onCreate(mapViewBundle);
        // Obtain a map instance.
        mMapView.getMapAsync(this);

        MapsInitializer.setApiKey("CgB6e3x9HT8wHBZoFsLF9L5r68jkBWYEZag7AQKBLbT8VObWOtmXjBNdKqdWQbiWNonajAxvNPWh6WoTdnNHmbxv");




    }


    @RequiresPermission(allOf = {ACCESS_FINE_LOCATION, ACCESS_WIFI_STATE})

    @Override
    public void onMapReady(HuaweiMap map) {
        // Obtain a map instance from callback.
        Log.d(TAG, "onMapReady: ");
        hMap = map;
        hMap.setMyLocationEnabled(false);



//        mLocationRequest = new LocationRequest();
//        mLocationRequest.setInterval(1000);
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//
        Log.d("getlocation", "Success");


//        marker1 = hMap.addMarker(new MarkerOptions()
//                .position(new LatLng(strresult, strresult1))
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker2)));

        Log.d("StartUpdLocSuccess", "Success");




        mMarker = hMap.addMarker(new MarkerOptions()
                .position(new LatLng(10.929685963333904, 78.73875966897228))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker2)));

//


        // If hMap is null, the program stops running.
        if (null == hMap) {
            return;
        }
// If mPolyline is not null, remove it from the map and then set it to null.

        if (null != mPolyline) {
            mPolyline.remove();
            mPolyline = null;
        }

        if (null != mCircle) {
            mCircle.remove();
            mCircle = null;
        }

        mCircle = hMap.addCircle(new CircleOptions()

                .center(new LatLng(10.929685963333904, 78.73875966897228))
                .radius(300)
                .fillColor(Color.GREEN));
//        mCircle.setFillColor(Color.TRANSPARENT);
//        mCircle.setStrokeColor(Color.GREEN);
        mCircle.setStrokeWidth(5);

// Add a polyline to a map.
//        mPolyline = hMap.addPolyline(new PolylineOptions()
//                // Set the coordinates of a polyline.
//
//                .add(new LatLng(10.929685963333904, 78.73875966897228), new LatLng(strresult, strresult1))
////                .add(new LatLng(10.929685963333904, 78.73875966897228), new LatLng(80, 10))
//                // Set the color of a polyline.
//                .color(Color.BLUE)
//                // Set the polyline width.
//                .width(3));


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
    protected void onResume() {
        super.onResume();
        mMapView.onResume();


    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }



//    public void location() {
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//
//            return;
//        }
//        Awareness.getCaptureClient(MapsActivity.this).getLocation()
//                // Callback listener for execution success.
//                .addOnSuccessListener(new OnSuccessListener<LocationResponse>() {
//                    @Override
//                    public void onSuccess(LocationResponse locationResponse) {
//                        Location location = locationResponse.getLocation();
//                        strresult = location.getLatitude();
//                        strresult1 = location.getLongitude();
//                        Log.i(TAG, "Longitude:" + location.getLongitude()
//                                + ",Latitude:" + location.getLatitude());
//                    }
//                })
//                // Callback listener for execution failure.
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(Exception e) {
//                        Log.e(TAG, "get location failed", e);
//                    }
//                });
//
//
//    }



    private void initialLocation() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (null == mLocationCallback) {
            mLocationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult != null) {
                        List<Location> locations = locationResult.getLocations();
                        if (!locations.isEmpty()) {
                            for (Location location : locations) {
//                                latlngcurrent = new LatLng(location.getLatitude(), location.getLongitude());

                                strresult = location.getLatitude();
                                strresult1 = location.getLongitude();



                            }
                        }
                    }
                }

                @Override
                public void onLocationAvailability(LocationAvailability locationAvailability) {
                    if (locationAvailability != null) {
                        boolean flag = locationAvailability.isLocationAvailable();
                        Log.i(TAG, "onLocationAvailability isLocationAvailable:" + flag);
                    }
                }
            };
            requestLocationUpdatesWithCallback();

        }

    }

    private void requestLocationUpdatesWithCallback() {
        try {
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
            builder.addLocationRequest(mLocationRequest);
            LocationSettingsRequest locationSettingsRequest = builder.build();
            settingsClient.checkLocationSettings(locationSettingsRequest)
                    .addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
                        @Override
                        public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                            fusedLocationProviderClient
                                    .requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.getMainLooper())
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(Exception e) {
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                        }
                    });
        } catch (Exception e) {
        }
    }



}


