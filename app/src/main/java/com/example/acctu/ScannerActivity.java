package com.example.acctu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;

import java.util.Scanner;

public class ScannerActivity extends Activity {
    public static final int DEFAULT_VIEW = 0x22;
    private static final int REQUEST_CODE_SCAN = 0X01;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.requestPermissions(
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                    DEFAULT_VIEW);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissions == null || grantResults == null || grantResults.length <2 || grantResults[0] != PackageManager.PERMISSION_GRANTED ||grantResults[1] != PackageManager.PERMISSION_GRANTED){
            return;
        }
        if ( requestCode == DEFAULT_VIEW){
            ScanUtil.startScan(ScannerActivity.this,REQUEST_CODE_SCAN,
                    new HmsScanAnalyzerOptions.Creator().setHmsScanTypes(HmsScan.ALL_SCAN_TYPE,
                            HmsScan.CODE128_SCAN_TYPE).create());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode, data);
        if( resultCode != RESULT_OK || data == null){
            return;
        }
        if (requestCode == REQUEST_CODE_SCAN){
            Object obj = data.getParcelableExtra(ScanUtil.RESULT);
            if(obj instanceof HmsScan){
                if(!TextUtils.isEmpty(((HmsScan) obj).getOriginalValue())){
//                    Toast.makeText(this,((HmsScan) obj).getOriginalValue(),Toast.LENGTH_SHORT).show();
                    Log.d("Scan","URL:"+((HmsScan) obj).getOriginalValue());
                    String url = ((HmsScan) obj).getOriginalValue();
                    String name= Uri.parse(url).getQueryParameter("name");
                    Log.d("TAG", "Name " + name);
                    Uri uri = Uri.parse(url);
                    Log.i("uri",""+uri);
                    String chapter = "";
                    try {
                        String key= "name";
                        if(uri.toString().contains("?"+key+"=") || uri.toString().contains("&"+key+"=")){
                            chapter = uri.getQueryParameter("name");
                            Log.d("parameter", "Value :" + chapter);
                        }
                        else {
//                            Toast.makeText(ScannerActivity.this, "Key doesn't Existing", Toast.LENGTH_SHORT).show();

                        }

                    }
                    catch (Exception e){

                        Log.i("Key","Key  Not present");

                    }
                    String key = "krct";
//                    if(chapter.equals("krct"))
                        if(chapter.compareTo(key)==0){
                        Log.d("Scan","URL:"+((HmsScan) obj).getOriginalValue());
                        Toast.makeText(this,((HmsScan) obj).getOriginalValue(),Toast.LENGTH_SHORT).show();
//                        String url = ((HmsScan) obj).getOriginalValue();
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
//                        ScannerActivity.this.finish();
                    }
                    else{


                            Toast.makeText(ScannerActivity.this, "This URl doesn't come under the KRCT Oranganization", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(ScannerActivity.this, HomeActivity.class);
//                            startActivity(intent);
                    }
//

                }
                ScannerActivity.this.finish();
            }
        }
    }




}

