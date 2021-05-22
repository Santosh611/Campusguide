package com.example.acctu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hms.location.FusedLocationProviderClient;
import com.huawei.hms.location.LocationAvailability;
import com.huawei.hms.location.LocationCallback;
import com.huawei.hms.location.LocationRequest;
import com.huawei.hms.location.LocationResult;
import com.huawei.hms.location.LocationServices;
import com.huawei.hms.location.LocationSettingsRequest;
import com.huawei.hms.location.LocationSettingsResponse;
import com.huawei.hms.location.SettingsClient;
import com.huawei.hms.maps.CameraUpdate;
import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.MapView;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.model.BitmapDescriptorFactory;
import com.huawei.hms.maps.model.CameraPosition;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.Marker;
import com.huawei.hms.maps.model.MarkerOptions;
import com.huawei.hms.maps.model.Polyline;
import com.huawei.hms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class Directions extends AppCompatActivity implements OnMapReadyCallback {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private SettingsClient settingsClient;
    LocationCallback mLocationCallback;
    LocationRequest mLocationRequest;

    private HuaweiMap hmap;
    private Marker mMarker;
    private Marker mDestMarker;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private latlngModel.Destination des;
    private latlngModel.Origin origin;
    private Polyline currentPolyline;

    LatLng latlngcurrent;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        settingsClient = LocationServices.getSettingsClient(this);
        MapView mMapView = findViewById(R.id.mapView);
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);


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
                                 latlngcurrent = new LatLng(location.getLatitude(), location.getLongitude());
                                updateMap(latlngcurrent);

                            }
                        }
                    }
                }

                @Override
                public void onLocationAvailability(LocationAvailability locationAvailability) {
                    if (locationAvailability != null) {
                        boolean flag = locationAvailability.isLocationAvailable();
//                        Log.i(TAG, "onLocationAvailability isLocationAvailable:" + flag);
                    }
                }
            };

            requestLocationUpdatesWithCallback();
        }
        dynamicPermission();
    }

    @Override
    public void onMapReady(HuaweiMap map) {
        hmap = map;
        initialLocation();
        hmap.setOnMapClickListener(new HuaweiMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (null != mDestMarker) {
                    mDestMarker.remove();
                }
                mDestMarker = hmap.addMarker(new MarkerOptions().position(latLng)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker1))
                        .clusterable(true));
                mDestMarker.showInfoWindow();
                mDestMarker.setTitle(String.valueOf(latLng));
                des = new latlngModel.Destination();
                des.setLat(latLng.latitude);
                des.setLng(latLng.longitude);
                if (latlngcurrent != null) {
                    getDirectionResponse();
                }
            }
        });
    }

    public static class latlngModel {
        @SerializedName("destination")
        private Destination Destination;
        @SerializedName("origin")
        private Origin Origin;

        public Destination getDestination() {
            return Destination;
        }

        public void setDestination(Destination destination) {
            this.Destination = destination;
        }

        public Origin getOrigin() {
            return Origin;
        }

        public void setOrigin(Origin origin) {
            this.Origin = origin;
        }

        public static class Destination {
            private Double lat;
            private Double lng;

            public Double getLat() {
                return lat;
            }

            public void setLat(Double lat) {
                this.lat = lat;
            }

            public Double getLng() {
                return lng;
            }

            public void setLng(Double lng) {
                this.lng = lng;
            }
        }

        public static class Origin {
            private Double lat;
            private Double lng;

            public Double getLat() {
                return lat;
            }

            public void setLat(Double lat) {
                this.lat = lat;
            }

            public Double getLng() {
                return lng;
            }

            public void setLng(Double lng) {
                this.lng = lng;
            }
        }
    }

    private void updateMap(LatLng latlngcurrent) {
        hmap.setMyLocationEnabled(true);
        CameraPosition build = new CameraPosition.Builder().target(latlngcurrent).zoom(15).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(build);
        hmap.animateCamera(cameraUpdate);
        hmap.setMaxZoomPreference(20);
        hmap.setMinZoomPreference(2);
        if (null != mMarker) {
            mMarker.remove();
        }
        mMarker = hmap.addMarker(new MarkerOptions().position(latlngcurrent)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker2))
                .clusterable(true));
        mMarker.showInfoWindow();
        mMarker.setTitle(String.valueOf(latlngcurrent));
        hmap.setOnMarkerClickListener(new HuaweiMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(getApplicationContext(), "marker location " + marker.getTitle(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }


    public static class RetrofitInitialAPI {
        private static String url = "https://mapapi.cloud.huawei.com/mapApi/v1/routeService/";
        private static Retrofit retrofit = null;
        public static Retrofit getClient(Context context) {
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }
    }


    public interface RetrofitMaps {
        static String key="CgB6e3x9HT8wHBZoFsLF9L5r68jkBWYEZag7AQKBLbT8VObWOtmXjBNdKqdWQbiWNonajAxvNPWh6WoTdnNHmbxv";
        @POST("driving?key="+key)
        Single<Response<DirectionModel>> getDriveDistanceDuration(@Body latlngModel bodys) ;
        @POST("walking?key="+key)
        Single<Response<DirectionModel>> getWalkDistanceDuration(@Body latlngModel bodys) ;
        @POST("bicycling?key="+key)
        Single<Response<DirectionModel>> getBicycleDistanceDuration(@Body latlngModel bodys) ;
    }


    public static class DirectionModel {
        @SerializedName("routes")
        @Expose
        private
        List<Route> routes;

        @SerializedName("returnCode")
        @Expose
        private
        String returnCode;

        @SerializedName("returnDesc")
        @Expose
        private
        String returnDesc;

        public List<Route> getRoutes() {
            return routes;
        }

        public void setRoutes(List<Route> routes) {
            this.routes = routes;
        }

        public String getReturnCode() {
            return returnCode;
        }

        public void setReturnCode(String returnCode) {
            this.returnCode = returnCode;
        }

        public String getReturnDesc() {
            return returnDesc;
        }

        public void setReturnDesc(String returnDesc) {
            this.returnDesc = returnDesc;
        }

        class EndLocation {
            @SerializedName("lng")
            @Expose
            private
            Double lng;

            @SerializedName("lat")
            @Expose
            private
            Double lat;

            public Double getLng() {
                return lng;
            }

            public void setLng(Double lng) {
                this.lng = lng;
            }

            public Double getLat() {
                return lat;
            }

            public void setLat(Double lat) {
                this.lat = lat;
            }
        }

        static class Northeast {
            @SerializedName("lng")
            @Expose
            private
            Double lng;

            @SerializedName("lat")
            @Expose
            private
            Double lat;

            public Double getLng() {
                return lng;
            }

            public void setLng(Double lng) {
                this.lng = lng;
            }

            public Double getLat() {
                return lat;
            }

            public void setLat(Double lat) {
                this.lat = lat;
            }
        }

        class Path {
            @SerializedName("duration")
            @Expose
            private
            Double duration;

            @SerializedName("durationInTraffic")
            @Expose
            private
            Double durationInTraffic;

            @SerializedName("distance")
            @Expose
            private
            Double distance;

            @SerializedName("startLocation")
            @Expose
            private
            StartLocation startLocation;

            @SerializedName("steps")
            @Expose
            private
            List<Step> steps;

            @SerializedName("endLocation")
            @Expose
            private
            EndLocation endLocation;

            public Double getDuration() {
                return duration;
            }

            public void setDuration(Double duration) {
                this.duration = duration;
            }

            public Double getDurationInTraffic() {
                return durationInTraffic;
            }

            public void setDurationInTraffic(Double durationInTraffic) {
                this.durationInTraffic = durationInTraffic;
            }

            public Double getDistance() {
                return distance;
            }

            public void setDistance(Double distance) {
                this.distance = distance;
            }

            public StartLocation getStartLocation() {
                return startLocation;
            }

            public void setStartLocation(StartLocation startLocation) {
                this.startLocation = startLocation;
            }

            public List<Step> getSteps() {
                return steps;
            }

            public void setSteps(List<Step> steps) {
                this.steps = steps;
            }

            public EndLocation getEndLocation() {
                return endLocation;
            }

            public void setEndLocation(EndLocation endLocation) {
                this.endLocation = endLocation;
            }
        }

        class Polyline {
            @SerializedName("lng")
            @Expose
            private
            Double lng;

            @SerializedName("lat")
            @Expose
            private
            Double lat;

            public Double getLng() {
                return lng;
            }

            public void setLng(Double lng) {
                this.lng = lng;
            }

            public Double getLat() {
                return lat;
            }

            public void setLat(Double lat) {
                this.lat = lat;
            }
        }

        class Route {
            @SerializedName("paths")
            @Expose
            private
            List<Path> paths;
            @SerializedName("bounds")
            @Expose
            private
            Bounds bounds;

            public List<Path> getPaths() {
                return paths;
            }

            public void setPaths(List<Path> paths) {
                this.paths = paths;
            }

            public Bounds getBounds() {
                return bounds;
            }

            public void setBounds(Bounds bounds) {
                this.bounds = bounds;
            }
        }

        class Southwest {
            @SerializedName("lng")
            @Expose
            Double lng;

            @SerializedName("lat")
            @Expose
            Double lat;

        }

        class StartLocation {
            @SerializedName("lng")
            @Expose
            Double lng;

            @SerializedName("lat")
            @Expose
            Double lat;

        }

        class Step {
            @SerializedName("duration")
            @Expose
            Double duration;

            @SerializedName("orientation")
            @Expose
            Double orientation;

            @SerializedName("distance")
            @Expose
            Double distance;

            @SerializedName("startLocation")
            @Expose
            StartLocation startLocation;

            @SerializedName("action")
            @Expose
            String action;

            @SerializedName("endLocation")
            @Expose
            EndLocation endLocation;

            @SerializedName("polyline")
            @Expose
            List<Polyline> polyline;

            @SerializedName("roadName")
            @Expose
            String roadName;

        }

        class Bounds {
            @SerializedName("southwest")
            @Expose
            Southwest southwest;

            @SerializedName("northeast")
            @Expose
            Northeast northeast;
        }

    }



    private void getDirectionResponse() {
        //set origin and des latlng
        latlngModel directionApi = new latlngModel();
        latlngModel.Origin origin = new latlngModel.Origin();
        origin.setLat(latlngcurrent.latitude);
        origin.setLng(latlngcurrent.longitude);
        directionApi.setDestination(des);
        directionApi.setOrigin(origin);
        //initial retrofit to call directionAPI
        RetrofitMaps service = RetrofitInitialAPI.getClient(this).create(RetrofitMaps.class);
        Single<Response<DirectionModel>> issueObservable = service.getDriveDistanceDuration(directionApi);
        issueObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<DirectionModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(Response<DirectionModel> response) {
                        if (response.isSuccessful()) {
                            if (currentPolyline != null) {
                                currentPolyline.remove();
                            }
                            getDirectionSuccess(response.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

    private void getDirectionSuccess(DirectionModel direction) {
        ArrayList<LatLng> pathList = new ArrayList<LatLng>();
        if (direction.getRoutes() != null && !direction.getRoutes().equals("")) {
            if (direction.getRoutes().get(0).getPaths() != null && !direction.getRoutes().get(0).getPaths().equals("")) {
                for (int i = 0; i < direction.getRoutes().get(0).getPaths().size(); i++) {
                    int path = i;
                    if (direction.getRoutes().get(0).getPaths().get(i).getSteps() != null && !direction.getRoutes().get(0).getPaths().get(i).getSteps().equals("")) {
                        for (int j = 0; j < direction.getRoutes().get(0).getPaths().get(i).getSteps().size(); j++) {
                            if (direction.getRoutes().get(0).getPaths().get(i).getSteps().get(j).polyline != null && !direction.getRoutes().get(0).getPaths().get(i).getSteps().get(j).polyline.equals("")) {
                                for (int k = 0; k < direction.getRoutes().get(0).getPaths().get(i).getSteps().get(j).polyline.size(); k++) {
                                    pathList.add(new LatLng(direction.getRoutes().get(0).getPaths().get(i).getSteps().get(j).polyline.get(k).getLat(), direction.getRoutes().get(0).getPaths().get(i).getSteps().get(j).polyline.get(k).getLng()));
                                }
                            }

                        }
                    }
                }
            }
        }
        if (direction.getRoutes() != null) {
            currentPolyline = hmap.addPolyline(
                    new PolylineOptions().addAll(pathList)
                            .color(getResources().getColor(R.color.mb_blue)));
            Double distance = Double.valueOf(direction.getRoutes().get(0).getPaths().get(0).getDistance()) / 1000;
            Double eta = Double.valueOf(direction.getRoutes().get(0).getPaths().get(0).getDuration()) / 60;
            textView.setText("Duration: " + String.format("%.2f", eta) + "Min Distance: " + String.format("%.2f", distance) + "KM");
        }
    }

    private void dynamicPermission() {
        // Dynamically apply for required permissions if the API level is 28 or smaller.
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
//            Log.i(TAG, "android sdk <= 28 Q");
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