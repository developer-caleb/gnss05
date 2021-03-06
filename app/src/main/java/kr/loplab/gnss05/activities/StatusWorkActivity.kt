package kr.loplab.gnss05.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.PositionInformationActivity
import kr.loplab.gnss05.R
import kr.loplab.gnss05.activities.measurement.TopoMeasurementActivity
import kr.loplab.gnss05.activities.mobile_station.MobileStationActivity
import kr.loplab.gnss05.activities.mobile_station.MobileStationSettingSatelliteActivity
import kr.loplab.gnss05.activities.viewmodel.ExportViewModel
import kr.loplab.gnss05.activities.viewmodel.StatusWorkViewModel
import kr.loplab.gnss05.databinding.ActivitySettingBinding
import kr.loplab.gnss05.databinding.ActivityStatusWorkBinding
import kr.loplab.gnss05.enums.SurveyType

class StatusWorkActivity : ActivityBase<ActivityStatusWorkBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_status_work
    lateinit var viewModel1: StatusWorkViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun init() {
        viewModel1 = ViewModelProvider(this).get(StatusWorkViewModel::class.java)
        viewBinding.viewModel = viewModel1
    }

    override fun initListener() {
        viewBinding.btBack.setOnClickListener {
            onBackPressed()
        }

        viewBinding.btMobilestation.setOnClickListener {
            Log.d(TAG, "initListener: settingSatelliteBt clicked")
            intent = Intent(this, MobileStationActivity::class.java)
            startActivity(intent);
        }
        viewBinding.btPositionInformation.setOnClickListener {
            Log.d(TAG, "initListener: settingSatelliteBt clicked")
            intent = Intent(this, PositionInformationActivity::class.java)
            startActivity(intent);
        }
        viewBinding.btBatteryStatus.setOnClickListener {
           val alertDialog = AlertDialog.Builder(this)
               .setTitle("????????? ??????")
               .setMessage("A?????? ????????? ??????:90%\nB?????? ????????? ??????:80%")
               .setPositiveButton("??????"){dialog, which -> {
                   //???????????? ?????????..
               }}
               .create()
            alertDialog.show()
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE)
        }
        viewBinding.btSurveyMode.setOnClickListener {
            viewModel1.setBoolvalue(viewModel1.surveyModeLayout, true);
        }
        viewBinding.backgroundDemomode.setOnClickListener{
            viewModel1.setBoolvalue(viewModel1.surveyModeLayout, false);
        }
        viewBinding.btTopoSurvey.setOnClickListener {
            viewModel1.setSurveyType(viewModel1.surveyType, SurveyType.TOPO)
            viewModel1.setBoolvalue(viewModel1.surveyModeLayout, false);
        }
        viewBinding.btFastSurvey.setOnClickListener {
            viewModel1.setSurveyType(viewModel1.surveyType, SurveyType.FAST)
            viewModel1.setBoolvalue(viewModel1.surveyModeLayout, false);
        }
        viewBinding.btAutoSurvey.setOnClickListener {
            viewModel1.setSurveyType(viewModel1.surveyType, SurveyType.AUTO)
            viewModel1.setBoolvalue(viewModel1.surveyModeLayout, false);
        }
        viewBinding.btMeasurement.setOnClickListener {
            when(viewModel1.surveyType.value){
                SurveyType.FAST->{}
                SurveyType.AUTO->{}
                else->{
                    Log.d(TAG, "initListener: GO TOPO ACTIVITY")
                    intent = Intent(this, TopoMeasurementActivity::class.java)
                    startActivity(intent);
                }
            }
        }
        viewBinding.btToolbarOpen.setOnClickListener { viewModel1.setBoolvalue(viewModel1.toolbarOpen, !viewModel1.toolbarOpen.value!!); }
     /*   viewBinding.btToolbar0.setOnClickListener {  }
        viewBinding.btToolbar1.setOnClickListener {  }
        viewBinding.btToolbar2.setOnClickListener {  }
        viewBinding.btToolbar3.setOnClickListener {  }
        viewBinding.btToolbar4.setOnClickListener {  }
        viewBinding.btToolbar5.setOnClickListener {  }
        viewBinding.btToolbar6.setOnClickListener {  }
        viewBinding.btToolbar7.setOnClickListener {  }
        viewBinding.btToolbar8.setOnClickListener {  }*/
        viewBinding.btPointSave.setOnClickListener {  }
        viewBinding.btSettings.setOnClickListener {  }
        viewBinding.layoutFastSurveyName.setOnClickListener {
            requestETfocus(viewBinding.etFastSurveyName)
        }
        viewBinding.layoutFastSurveyCode.setOnClickListener {
            requestETfocus(viewBinding.etFastSurveyCode)
        }
    }

    override fun initDatabinding() {
    }
}