package kr.loplab.gnss05

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.larswerkman.holocolorpicker.ColorPicker

class ColorPickerdialog : AppCompatActivity() {
    var picker: ColorPicker? = null
    var TAG = "ColorPickerdialog"
    var confirmbt1: TextView? = null
    var confirmbt2: TextView? = null
    lateinit var header: DefaultHeader
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color_picker)
        header = findViewById<View>(R.id.header_06) as DefaultHeader
        confirmbt1 = findViewById<View>(R.id.confirmbt1) as TextView
        picker = findViewById<View>(R.id.picker22) as ColorPicker
        // picker.setOldCenterColor(picker.getColor());
        picker!!.showOldCenterColor = false
        Log.d(TAG, "onCreate: 피커는 만들어졌는데 scrollbar size가 왜 안먹힐까?")
        confirmbt1!!.setOnClickListener { v: View? ->
            Log.d(TAG, "picker color->" + picker!!.color)
            val hexColor = String.format("#%06X", 0xFFFFFF and picker!!.color)
            Log.d(TAG, "picker color->$hexColor")
        }
        header.setOnBackButtonClickListener {
            Log.d(TAG, "back pressed!")
        onBackPressed();
        }
        /*  header.setOnBackButtonClickListener{
            Log.d(TAG, "onCreate: dd");
        };*/

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