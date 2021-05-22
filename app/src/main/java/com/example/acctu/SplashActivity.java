package com.example.acctu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.support.account.AccountAuthManager;
import com.huawei.hms.support.account.request.AccountAuthParams;
import com.huawei.hms.support.account.request.AccountAuthParamsHelper;
import com.huawei.hms.support.account.result.AuthAccount;
import com.huawei.hms.support.account.service.AccountAuthService;
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams;
import com.huawei.hms.support.hwid.service.HuaweiIdAuthService;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private HuaweiIdAuthService mHuaweiIdAuthService;
    private HuaweiIdAuthParams mHuaweiIdAuthParams;
    private ProgressBar mProgressBar;
    private TimerTask timer;
    long Delay = 3000;
    TextView txtAppName;
    private static final String TAG = "SplashActivity";
    public String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        mProgressBar = findViewById(R.id.progressBar);


        silentlySignIn();

        Timer RunSplash = new Timer();
        timer = new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (userName!=null) {
                            mProgressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(SplashActivity.this,"Sliently Signed In", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
//                            Toast.makeText(SplashActivity.this,"Sliently Signed In", Toast.LENGTH_LONG).show();
                            startActivity(intent);
                            finish();
                        }else{

                            Intent intent = new Intent(SplashActivity.this, MainActivity2.class);

                            startActivity(intent);
                        }
                        finish();
                    }
                });
            }
        };
        RunSplash.schedule(timer, Delay);
    }

    private void silentlySignIn(){
        AccountAuthParams authParams = new AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM).createParams();
        AccountAuthService service = AccountAuthManager.getService(this, authParams);
        Task<AuthAccount> task = service.silentSignIn();
        task.addOnSuccessListener(new OnSuccessListener<AuthAccount>() {
            @Override
            public void onSuccess(AuthAccount authAccount) {
                // Obtain the user's ID information.
                userName = authAccount.getDisplayName();

                Log.i(TAG, "displayName:" + authAccount.getDisplayName());
                // Obtain the ID type (0: HUAWEI ID; 1: AppTouch ID).
                Log.i(TAG, "accountFlag:" + authAccount.getAccountFlag());
//                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
//                startActivity(intent);
            }
        });
    }
}

//        map:latLngBoundsNorthEastLatitude="49.893478"
//                map:latLngBoundsNorthEastLongitude="1.334595"
//                map:latLngBoundsSouthWestLatitude="47.893478"
//                map:latLngBoundsSouthWestLongitude="3.334595"