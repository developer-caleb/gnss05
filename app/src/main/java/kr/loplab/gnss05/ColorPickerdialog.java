package kr.loplab.gnss05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SVBar;
import com.larswerkman.holocolorpicker.SaturationBar;
import com.larswerkman.holocolorpicker.ValueBar;

public class ColorPickerdialog extends AppCompatActivity {
    ColorPicker picker;
    String TAG=  "ColorPickerdialog";
    TextView confirmbt1;
    TextView confirmbt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);


        confirmbt1 = (TextView) findViewById(R.id.confirmbt1);
         picker = (ColorPicker) findViewById(R.id.picker22);
       // picker.setOldCenterColor(picker.getColor());
        picker.setShowOldCenterColor(false);

        Log.d(TAG, "onCreate: 피커는 만들어졌는데 scrollbar size가 왜 안먹힐까?");
        confirmbt1.setOnClickListener(v -> {
            Log.d(TAG, "picker color->" + picker.getColor());
            String hexColor = String.format("#%06X", (0xFFFFFF & picker.getColor()));
            Log.d(TAG, "picker color->" + hexColor);
        });

      /*  SVBar svBar = (SVBar) findViewById(R.id.svbar);
        OpacityBar opacityBar = (OpacityBar) findViewById(R.id.opacitybar);
        SaturationBar saturationBar = (SaturationBar) findViewById(R.id.saturationbar);
        ValueBar valueBar = (ValueBar) findViewById(R.id.valuebar);

        picker.addSVBar(svBar);
        picker.addOpacityBar(opacityBar);
        picker.addSaturationBar(saturationBar);
        picker.addValueBar(valueBar);

//To get the color
        picker.getColor();

//To set the old selected color u can do it like this
        picker.setOldCenterColor(picker.getColor());
// adds listener to the colorpicker which is implemented
//in the activity
        picker.setOnColorChangedListener(this);

//to turn of showing the old color
        picker.setShowOldCenterColor(false);

//adding onChangeListeners to bars
        opacitybar.setOnOpacityChangeListener(new OnOpacityChangeListener …)
        valuebar.setOnValueChangeListener(new OnValueChangeListener …)
        saturationBar.setOnSaturationChangeListener(new OnSaturationChangeListener …)*/


    }
}