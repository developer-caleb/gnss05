package kr.loplab.gnss05.activities.export

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.MyDialog
import kr.loplab.gnss05.R
import kr.loplab.gnss05.activities.viewmodel.ExportViewModel
import kr.loplab.gnss05.common.Define
import kr.loplab.gnss05.common.Define.EXPORT_ROAD_CROSS_SECTION_OUTPUT_USING
import kr.loplab.gnss05.common.OptionList
import kr.loplab.gnss05.common.PrefUtil
import kr.loplab.gnss05.databinding.ActivityExportBinding
import kr.loplab.gnss05.tableview.TableMainActivity

class ExportActivity :  ActivityBase<ActivityExportBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_export
    lateinit var viewModel1: ExportViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            //UserFormatMake

    }

    override fun init() {
        viewModel1 = ViewModelProvider(this).get(ExportViewModel::class.java)
        viewBinding.viewModel = viewModel1
    }

    override fun initListener() {
        viewBinding.header01.setOnBackButtonClickListener{onBackPressed()}
        viewBinding.btAddFileFormat.setOnClickListener {
            intent = Intent(this, CustomFileFormatActivity::class.java)
            startActivity(intent);
        }
        viewBinding.btExport.setOnClickListener {
            intent = Intent(this, FileExportActivity::class.java)
            startActivity(intent);
        }

        viewBinding.layoutRoadCrossSectionOutputUsing.setOnClickListener {
            viewModel1.setBoolvalue(viewModel1.roadCrossSecionOutputUsing , !viewModel1.roadCrossSecionOutputUsing.value!!)
        }
        viewBinding.layoutPointAssist.setOnClickListener {
            viewModel1.setBoolvalue(viewModel1.pointAssist , !viewModel1.pointAssist.value!!)
        }
        viewBinding.layoutPointMeasurement.setOnClickListener {
            viewModel1.setBoolvalue(viewModel1.pointMeasurement , !viewModel1.pointMeasurement.value!!)
        }
        viewBinding.layoutControlMeasurement.setOnClickListener {
            viewModel1.setBoolvalue(viewModel1.controlMeasurement , !viewModel1.controlMeasurement.value!!)
        }
        viewBinding.layoutPointInput.setOnClickListener {
            viewModel1.setBoolvalue(viewModel1.pointInput , !viewModel1.pointInput.value!!)
        }
        viewBinding.layoutPointCalculation.setOnClickListener {
            viewModel1.setBoolvalue(viewModel1.pointCalculation , !viewModel1.pointCalculation.value!!)
        }
        viewBinding.layoutPointSkateout.setOnClickListener {
            viewModel1.setBoolvalue(viewModel1.pointSkate , !viewModel1.pointSkate.value!!)
        }
        viewBinding.layoutScreenPoint.setOnClickListener {
            viewModel1.setBoolvalue(viewModel1.screenPoint , !viewModel1.screenPoint.value!!)
        }
        viewBinding.layoutReferencePoint.setOnClickListener {
            viewModel1.setBoolvalue(viewModel1.referencePoint , !viewModel1.referencePoint.value!!)
        }
        viewBinding.layoutDegreeForm.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = OptionList.DEGREE_FORM_TYPE
            dlg.firstLayoutUse = false
            dlg.list = alist
            dlg.selectedposition = viewModel1.degreeFormNum.value!!
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                viewModel1.degreeFormNum.value = i
                dlg.dismiss()
            }
            dlg.setHeader("각도 형식")
        }
    }

    override fun initDatabinding() {
        viewModel1.setBoolvalue(viewModel1.roadCrossSecionOutputUsing, PrefUtil.getBoolean(applicationContext, EXPORT_ROAD_CROSS_SECTION_OUTPUT_USING))

    }

    fun savesettings(){
        PrefUtil.setBoolean(applicationContext, Define.EXPORT_ROAD_CROSS_SECTION_OUTPUT_USING, viewModel1.roadCrossSecionOutputUsing.value!!)
    }
}