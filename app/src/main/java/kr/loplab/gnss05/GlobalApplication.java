package kr.loplab.gnss05;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.naver.maps.map.NaverMapSdk;

public class GlobalApplication  extends Application {
    public static Toast mToast;

    @Override
    public void onCreate() {
        super.onCreate();
        NaverMapSdk.getInstance(this).setClient(new NaverMapSdk.NaverCloudPlatformClient("x3tscpw23x"));
    }



}
