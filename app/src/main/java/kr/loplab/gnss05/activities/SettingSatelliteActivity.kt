package kr.loplab.gnss05.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Switch
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_setting_satellite.*
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.MyDialog
import kr.loplab.gnss05.R
import kr.loplab.gnss05.common.Define
import kr.loplab.gnss05.common.Define.*
import kr.loplab.gnss05.common.OptionList
import kr.loplab.gnss05.common.PrefUtil
import kr.loplab.gnss05.databinding.ActivitySettingSatelliteBinding
import kr.loplab.gnss05.databinding.ActivityStopSurveyBinding

class SettingSatelliteActivity : ActivityBase<ActivitySettingSatelliteBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_setting_satellite
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun init() {

    }

    override fun initListener() {
        viewBinding.header01.setOnBackButtonClickListener { onBackPressed();}
        viewBinding.btConfirm.setOnClickListener {
            Log.d(TAG, "initListener: confirmbt_clicked-> 저장하기")
            //save
            PrefUtil.setInt(this, REFERENCE_COUNTRY_SATELLITE_CUT_ANGLE, viewBinding.tvCutAngle.text.toString().toInt())
            PrefUtil.setFloat(this, REFERENCE_COUNTRY_SATELLITE_PDOP_LIMIT, viewBinding.tvPdopLimit.text.toString().toFloat())
            PrefUtil.setInt(this, REFERENCE_COUNTRY_SATELLITE_DELAY, viewBinding.tvDelay.text.toString().toInt())


            PrefUtil.setBoolean(this, REFERENCE_COUNTRY_SATELLITE_GPS , viewBinding.swGps.isChecked )
            PrefUtil.setBoolean(this, REFERENCE_COUNTRY_SATELLITE_GLONASS, viewBinding.swGlonass.isChecked)
            PrefUtil.setBoolean(this, REFERENCE_COUNTRY_SATELLITE_BEIDOU, viewBinding.swBeidou.isChecked)
            PrefUtil.setBoolean(this, REFERENCE_COUNTRY_SATELLITE_GALILEO, viewBinding.swGalieo.isChecked)
            PrefUtil.setBoolean(this, REFERENCE_COUNTRY_SATELLITE_SBAS, viewBinding.swSbas.isChecked)
            PrefUtil.setBoolean(this, REFERENCE_COUNTRY_SATELLITE_QZSS, viewBinding.swQzss.isChecked)
            PrefUtil.setBoolean(this, REFERENCE_COUNTRY_SATELLITE_LBAND, viewBinding.swLband.isChecked)



            onBackPressed()
        }

        //숫자
        viewBinding.layoutCutAngle.setOnClickListener {
            Log.d(TAG, "initListener: cutangle")
            val dlg = MyDialog(this)
            var alist = OptionList.CUT_ANGLE_List
            var prefvalue = Define.REFERENCE_COUNTRY_SATELLITE_CUT_ANGLE
            dlg.firstLayoutUse = true
            dlg.list = alist
            dlg.selectedposition= OptionList.CUT_ANGLE_List.indexOf(viewBinding.tvCutAngle.text.toString())
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                viewBinding.tvCutAngle.setText(OptionList.CUT_ANGLE_List[i])
                dlg.dismiss()
            }
            dlg.setOnCheckClickedListener{ str ->
                    viewBinding.tvCutAngle.setText(str)
                dlg.dismiss()
            }
            dlg.setHeader("컷 각도")
        }
        viewBinding.layoutPdopLimit.setOnClickListener { Log.d(TAG, "initListener: pdoplimit")
            val dlg = MyDialog(this)
            var alist = OptionList.PDOP_LIMIT_LIST
            var prefvalue = Define.REFERENCE_COUNTRY_SATELLITE_PDOP_LIMIT
            dlg.firstLayoutUse = true
            dlg.list = alist
            dlg.selectedposition= PrefUtil.getInt2(applicationContext, prefvalue)
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                Log.d(TAG, "initListener: $i")
                PrefUtil.setInt(applicationContext, prefvalue, i)
                //viewBinding.tvDisplacementMode.text = alist[PrefUtil.getInt2(applicationContext, prefvalue )]
                dlg.refresh()
                dlg.dismiss()
            }
            dlg.setHeader("PDOP한계")
        }
        viewBinding.layoutDelay.setOnClickListener { Log.d(TAG, "initListener: delay")
            val dlg = MyDialog(this)
            var alist = OptionList.DELAY_LIST
            var prefvalue = Define.REFERENCE_COUNTRY_SATELLITE_DELAY
            dlg.firstLayoutUse = true
            dlg.list = alist
            dlg.selectedposition= PrefUtil.getInt2(applicationContext, prefvalue)
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                Log.d(TAG, "initListener: $i")
                PrefUtil.setInt(applicationContext, prefvalue, i)
                //viewBinding.tvDisplacementMode.text = alist[PrefUtil.getInt2(applicationContext, prefvalue )]
                dlg.refresh()
                dlg.dismiss()
            }
            dlg.setHeader("지연(S)")
        }


        //스위치
        viewBinding.layoutGps.setOnClickListener {
            Log.d(TAG, "initListener: clicked")
            swchange(viewBinding.swGps)
        }
        viewBinding.layoutGlonass.setOnClickListener {
            Log.d(TAG, "initListener: clicked")
            swchange(viewBinding.swGlonass)
        }
        viewBinding.layoutBeidou.setOnClickListener {
            Log.d(TAG, "initListener: clicked")
            viewBinding.swBeidou.isChecked =    !viewBinding.swBeidou.isChecked
        }
        viewBinding.layoutGalilieo.setOnClickListener {
            Log.d(TAG, "initListener: clicked")
            viewBinding.swGalieo.isChecked =    !viewBinding.swGalieo.isChecked
        }
        viewBinding.layoutSbas.setOnClickListener {
            Log.d(TAG, "initListener: clicked")
            swchange2(viewBinding.swSbas)
        }
        viewBinding.layoutQzss.setOnClickListener {
            Log.d(TAG, "initListener: clicked")
            swchange2(viewBinding.swQzss)
        }
        viewBinding.layoutLband.setOnClickListener {
            Log.d(TAG, "initListener: clicked")
            swchange2(viewBinding.swLband)
        }



    }
    fun swchange(sw : Switch){
        sw.isChecked = !sw.isChecked
        //sw.performClick()
    }
    fun swchange2(sw : Switch){
        // sw.isChecked = !sw.isChecked
        sw.performClick()
    }

    override fun initDatabinding() {
        viewBinding.swGps.isChecked = PrefUtil.getBoolean(this, REFERENCE_COUNTRY_SATELLITE_GPS)
        viewBinding.swGlonass.isChecked = PrefUtil.getBoolean(this, REFERENCE_COUNTRY_SATELLITE_GLONASS)
        viewBinding.swBeidou.isChecked = PrefUtil.getBoolean(this, REFERENCE_COUNTRY_SATELLITE_BEIDOU)
        viewBinding.swGalieo.isChecked = PrefUtil.getBoolean(this, REFERENCE_COUNTRY_SATELLITE_GALILEO)
        viewBinding.swSbas.isChecked = PrefUtil.getBoolean(this, REFERENCE_COUNTRY_SATELLITE_SBAS)
        viewBinding.swQzss.isChecked = PrefUtil.getBoolean(this, REFERENCE_COUNTRY_SATELLITE_QZSS)
        viewBinding.swLband.isChecked = PrefUtil.getBoolean(this, REFERENCE_COUNTRY_SATELLITE_LBAND)


    }
}