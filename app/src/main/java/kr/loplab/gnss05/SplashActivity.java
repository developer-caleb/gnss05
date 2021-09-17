package kr.loplab.gnss05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.chc.gnss.sdk.CHC_CMDRef;
import com.chc.gnss.sdk.CHC_CONNECTION_METHOD;
import com.chc.gnss.sdk.CHC_MESSAGE_TYPE;
import com.chc.gnss.sdk.CHC_MessageInfo;
import com.chc.gnss.sdk.CHC_OEM_TYPE;
import com.chc.gnss.sdk.CHC_RECEIVER_TYPE;
import com.chc.gnss.sdk.CHC_Receiver;
import com.chc.gnss.sdk.CHC_ReceiverConstants;
import com.chc.gnss.sdk.CHC_ReceiverRef;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class SplashActivity extends AppCompatActivity {
    private String versionName;
    private static int SPLASH_TIME_OUT = 2000;
    private String TAG = getClass().getSimpleName().toString();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        PackageManager pm = getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (Exception e) {
        }
        starthandler();

    }
    private void starthandler()
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade, R.anim.hold);
                finish();
            } }, SPLASH_TIME_OUT);
    }



    public class Gnssreceiver2 {
        String filepath = "";
        CHC_ReceiverRef receiveref;
        CHC_CMDRef cmdRef;
        Gnssreceiver2(){
            internalfilewrite();
            CHC_Receiver receiver = null;  //1
            filepath =  internalfileread(); //2

        }
        // CHC_Receiver = null;
        // CHC_Receiver receiver = new CHC_Receiver();



        //작동 가능 확인
        String openrawfile(){
            InputStream inputStream = getResources().openRawResource(R.raw.features);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int i;
            try {
                i = inputStream.read();
                while (i != -1)
                {
                    byteArrayOutputStream.write(i);
                    i = inputStream.read();
                }
                inputStream.close();
            } catch (IOException e) {
                return null;
            }
            return byteArrayOutputStream.toString();
        }

        void internalfilewrite()  {
            try {
                FileOutputStream fos = openFileOutput("features.hcc", MODE_PRIVATE);
                DataOutputStream dos = new DataOutputStream(fos);
                dos.writeUTF(openrawfile());
                dos.flush();
                dos.close();
            } catch (Exception e){
                Log.e(TAG, "internalfileopenwrite: error "+ e);
            }
        }

        String internalfileread(){
            String returnstr="";
            try {
                File file = getFileStreamPath("features.hcc");
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;

                while ((line = br.readLine()) != null) {
                    Log.d(TAG, "internalfileread:--- "+ line);
                }
                br.close();

                Log.d(TAG, "internalfileread: filepath-> "+ file.getAbsolutePath());
                Log.d(TAG, "internalfileread: file.tostring"+ file.toString());

                //FileInputStream fis = openFileInput(file.getAbsolutePath());
                FileInputStream fis = openFileInput("features.hcc");
                DataInputStream dis = new DataInputStream(fis);
                Log.d(TAG, "internalfileread: -읽기" +dis.readUTF());
                String path = getFileStreamPath("features.hcc").getAbsolutePath();
                Log.d(TAG, "internalfileread:->>> "+ path);
                returnstr=  file.getAbsolutePath();
            } catch (Exception e){
                Log.e(TAG, "internalfileread: "+ e);
            }
            return  returnstr;
        }
    }



}