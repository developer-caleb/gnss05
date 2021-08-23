package kr.loplab.gnss05;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;

import com.naver.maps.map.NaverMapSdk;

public class GlobalApplication  extends Application {
    public static Toast mToast;

    @Override
    public void onCreate() {
        super.onCreate();
        NaverMapSdk.getInstance(this).setClient(new NaverMapSdk.NaverCloudPlatformClient("x3tscpw23x"));
        //Dark mode 없애기
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }



}
