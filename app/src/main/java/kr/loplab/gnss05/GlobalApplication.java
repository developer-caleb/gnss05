package kr.loplab.gnss05;

import android.app.Application;

import com.naver.maps.map.NaverMapSdk;

public class GlobalApplication  extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NaverMapSdk.getInstance(this).setClient(new NaverMapSdk.NaverCloudPlatformClient("x3tscpw23x"));
    }
}
