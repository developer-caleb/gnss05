package kr.loplab.gnss05.activities.stop_survey

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.MyDialog
import kr.loplab.gnss05.R
import kr.loplab.gnss05.activities.mobile_station.MobileStationSettingSatelliteActivity
import kr.loplab.gnss05.activities.viewmodel.MobileStationViewModel
import kr.loplab.gnss05.activities.viewmodel.StopSurveyViewModel
import kr.loplab.gnss05.common.Define
import kr.loplab.gnss05.common.OptionList
import kr.loplab.gnss05.common.PrefUtil
import kr.loplab.gnss05.databinding.ActivityStopSurveyBinding

class StopSurveyActivity : ActivityBase<ActivityStopSurveyBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_stop_survey
    lateinit var viewModel1: StopSurveyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun init() {
        viewModel1 = ViewModelProvider(this).get(StopSurveyViewModel::class.java)
        viewBinding.viewModel = viewModel1
    }


    override fun initListener() {
        viewBinding.header01.setOnBackButtonClickListener { onBackPressed();}
        viewBinding.settingSatelliteBt.setOnClickListener {
            Log.d(TAG, "initListener: settingSatelliteBt clicked")
            intent = Intent(this, MobileStationSettingSatelliteActivity::class.java)
            intent.putExtra("StopMode", true)
            startActivity(intent);
        }
        viewBinding.saveAndApplyBt.setOnClickListener {
            savesettings()
            Log.d(TAG, "initListener: saveAndApplyBt clicked")
        }
        viewBinding.applyBt.setOnClickListener {
            Log.d(TAG, "initListener: applyBt clicked")
        }
        viewBinding.layoutCurrentPointName.setOnClickListener {
            Log.d(TAG, "initListener: layoutCurrentPointName Clicked!")
            viewBinding.etCurrentPointName.requestFocus()
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(viewBinding.etCurrentPointName,0)
        }
        viewBinding.layoutPdopLimit.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = OptionList.PDOP_LIMIT_LIST
            dlg.firstLayoutUse = true
            dlg.list = alist
            dlg.input_text_str = viewBinding.tvPdopLimit.text.toString()
            dlg.selectedposition= alist.indexOf(viewBinding.tvPdopLimit.text.toString())
            dlg.start("")
            dlg.input_text.setInputType(InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_DECIMAL + InputType.TYPE_NUMBER_FLAG_SIGNED)
            dlg.setOnListClickedListener { view, i ->
                viewBinding.tvPdopLimit.setText(alist[i])
                dlg.dismiss()
            }
            dlg.setOnCheckClickedListener{ str ->
                viewBinding.tvPdopLimit.setText(str)
                dlg.dismiss()
            }
            dlg.setHeader("PDOP한계")

        }
        viewBinding.layoutCutAngle.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = OptionList.CUT_ANGLE_LIST
            dlg.firstLayoutUse = true
            dlg.list = alist
            dlg.input_text_str = viewBinding.tvCutAngle.text.toString()
            dlg.selectedposition= alist.indexOf(viewModel1.cutAngleNum.value.toString())
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                viewModel1.cutAngleNum.value = alist[i].toInt()
                dlg.dismiss()
            }
            dlg.setOnCheckClickedListener { str ->
                viewModel1.cutAngleNum.value = str.toInt()
            }
            dlg.setHeader("컷 각도")
        }
        viewBinding.layoutCollectionInterval.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = OptionList.COLLECTION_INTERVAL_LIST
            dlg.firstLayoutUse = false
            dlg.list = alist
            dlg.selectedposition= viewModel1.collectionIntervalNum.value!!
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                viewModel1.collectionIntervalNum.value = i
                dlg.dismiss()
            }
            dlg.setHeader("수집간격")
        }
        viewBinding.layoutStopSurveyAutosave.setOnClickListener {
            viewBinding.swStopsurveyAutosave.isChecked = !viewBinding.swStopsurveyAutosave.isChecked
        }
        viewBinding.layoutPoleheight.setOnClickListener {
            viewBinding.etPoleHeight.requestFocus()
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput( viewBinding.etPoleHeight,0)
        }
    }

    override fun initDatabinding() {
        viewBinding.tvPdopLimit.text =PrefUtil.getFloat(this, Define.STOP_SURVEY_PDOP_LIMIT , 0.5f ).toString()
        viewModel1.setIntvalue(viewModel1.cutAngleNum, PrefUtil.getInt2(applicationContext, Define.STOP_SURVEY_CUT_ANGLE, 1))
        viewModel1.setIntvalue(viewModel1.collectionIntervalNum, PrefUtil.getInt2(applicationContext, Define.STOP_SURVEY_COLLECTION_INTERVAL))  //2
        viewBinding.swStopsurveyAutosave.isChecked=  PrefUtil.getBoolean(applicationContext, Define.STOP_SURVEY_AUTO_SAVE)
        viewBinding.etPoleHeight.setText(PrefUtil.getInt2(applicationContext, Define.STOP_SURVEY_POLE_HEIGHT,0))


    }
    fun savesettings(){
        PrefUtil.setFloat(this, Define.STOP_SURVEY_PDOP_LIMIT, viewBinding.tvPdopLimit.text.toString().toFloat())
        PrefUtil.setInt(applicationContext, Define.STOP_SURVEY_CUT_ANGLE, viewModel1.cutAngleNum.value!!)
        PrefUtil.setInt(applicationContext, Define.STOP_SURVEY_COLLECTION_INTERVAL, viewModel1.collectionIntervalNum.value!!) //2
        PrefUtil.setBoolean(this, Define.STOP_SURVEY_AUTO_SAVE, viewBinding.swStopsurveyAutosave.isChecked)
        PrefUtil.setInt(applicationContext, Define.STOP_SURVEY_POLE_HEIGHT, viewBinding.etPoleHeight.text.toString().toInt()) //2

    }
}