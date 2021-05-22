package com.example.acctu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.huawei.hmf.tasks.Task;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.support.account.AccountAuthManager;
import com.huawei.hms.support.account.request.AccountAuthParams;
import com.huawei.hms.support.account.request.AccountAuthParamsHelper;
import com.huawei.hms.support.account.result.AuthAccount;
import com.huawei.hms.support.account.service.AccountAuthService;
import com.huawei.hms.support.hwid.ui.HuaweiIdAuthButton;

import static com.example.acctu.R.id.btnIdToken;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    HuaweiIdAuthButton btnAuthCode, btnIdToken;
    Button btnSilent,btnNext;
    AccountAuthService serviceIdToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btnIdToken = findViewById(R.id.btnIdToken);
        btnNext = findViewById(R.id.btnNext);
        btnIdToken.setOnClickListener(this);
        btnNext.setOnClickListener(this);


    }

    @Override



    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnIdToken:
                AccountAuthParams authParamsIdToken = new AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
                        .setMobileNumber()
                        .setEmail()
                        .setIdToken().createParams();
                serviceIdToken = AccountAuthManager.getService(MainActivity2.this, authParamsIdToken);
                startActivityForResult(serviceIdToken.getSignInIntent(), 8888);
                break;
            case R.id.btnNext:
                Intent intent = new Intent(MainActivity2.this,GuestActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Process the authorization result and obtain the authorization code from AuthAccount.
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 8888) {
            Task<AuthAccount> authAccountTask = AccountAuthManager.parseAuthResultFromIntent(data);
            if (authAccountTask.isSuccessful()) {
                // The sign-in is successful, and the user's ID information and ID token are obtained.
                AuthAccount authAccount = authAccountTask.getResult();
                Log.i(MainActivity2.class.getSimpleName(), "idToken:" + authAccount.getIdToken());
                Log.i(MainActivity2.class.getSimpleName(), "idToken:" + authAccount.getEmail());
                Log.i(MainActivity2.class.getSimpleName(), "idToken:" + authAccount.getDisplayName());
                // Obtain the ID type (0: HUAWEI ID; 1: AppTouch ID).
                Log.i(MainActivity2.class.getSimpleName(), "accountFlag:" + authAccount.getAccountFlag());
                Intent intent = new Intent(MainActivity2.this,HomeActivity.class);
                startActivity(intent);
            } else {
                // The sign-in failed. No processing is required. Logs are recorded for fault locating.
                Log.e(MainActivity2.class.getSimpleName(), "sign in failed : " +((ApiException)authAccountTask.getException()).getStatusCode());
            }
        }
    }
}