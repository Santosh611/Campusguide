
package com.example.acctu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.huawei.hmf.tasks.Task;
import com.huawei.hms.ads.HwAds;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.support.account.AccountAuthManager;
import com.huawei.hms.support.account.request.AccountAuthParams;
import com.huawei.hms.support.account.request.AccountAuthParamsHelper;
import com.huawei.hms.support.account.result.AuthAccount;
import com.huawei.hms.support.account.service.AccountAuthService;
public class MainActivity extends AppCompatActivity {

    Button btnAuthMode;
    Button btnIdT;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAuthMode = findViewById(R.id.btnAuthMode);
//        btnIdT = findViewById(R.id.btnIdT);
        button = findViewById(R.id.button);


        btnAuthMode.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_LONG).show();
                AccountAuthParams authParams = new AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM).setAuthorizationCode().createParams();
                AccountAuthService service = AccountAuthManager.getService(MainActivity.this, authParams);
                startActivityForResult(service.getSignInIntent(), 8888);
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();


            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == 8888) {
                Task<AuthAccount> authAccountTask = AccountAuthManager.parseAuthResultFromIntent(data);
                if (authAccountTask.isSuccessful()) {

                    // The sign-in is successful, and the user's ID information and authorization code are obtained.
                    AuthAccount authAccount = authAccountTask.getResult();
                    Log.i("MainActivity", "serverAuthCode:" + authAccount.getAuthorizationCode());



                } else {
                    // The sign-in failed.
                    Log.e("MainActivity", "sign in failed:" + ((ApiException) authAccountTask.getException()).getStatusCode());
                }
            }
        }
}