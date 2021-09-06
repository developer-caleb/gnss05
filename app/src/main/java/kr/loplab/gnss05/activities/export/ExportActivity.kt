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
        //횡단면 출력 OFF
        viewBinding.layoutFileForm1.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = OptionList.DEGREE_FILE_FORM1
            dlg.firstLayoutUse = false
            dlg.list = alist
            dlg.selectedposition = viewModel1.fileFormNum1.value!!
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                viewModel1.fileFormNum1.value = i
                dlg.dismiss()
            }
            dlg.setHeader("파일 형식")
        }

        //횡단면 출력 ON
        viewBinding.layoutFileForm2.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = OptionList.DEGREE_FILE_FORM2
            dlg.firstLayoutUse = false
            dlg.list = alist
            dlg.selectedposition = viewModel1.fileFormNum2.value!!
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                viewModel1.fileFormNum2.value = i
                dlg.dismiss()
            }
            dlg.setHeader("파일 형식")
        }

    }

    override fun initDatabinding() {
        viewModel1.setBoolvalue(viewModel1.roadCrossSecionOutputUsing, PrefUtil.getBoolean(applicationContext, EXPORT_ROAD_CROSS_SECTION_OUTPUT_USING))
        viewModel1.setBoolvalue(viewModel1.pointAssist, PrefUtil.getBoolean(applicationContext, Define.EXPORT_POINT_ASSIST))
        viewModel1.setBoolvalue(viewModel1.pointMeasurement, PrefUtil.getBoolean(applicationContext, Define.EXPORT_POINT_MEASUREMENT))
        viewModel1.setBoolvalue(viewModel1.controlMeasurement, PrefUtil.getBoolean(applicationContext, Define.EXPORT_POINT_CONTROL_MEASUREMENT))
        viewModel1.setBoolvalue(viewModel1.pointInput, PrefUtil.getBoolean(applicationContext, Define.EXPORT_POINT_INPUT))
        viewModel1.setBoolvalue(viewModel1.pointCalculation, PrefUtil.getBoolean(applicationContext, Define.EXPORT_POINT_CALCULATION))
        viewModel1.setBoolvalue(viewModel1.pointSkate, PrefUtil.getBoolean(applicationContext, Define.EXPORT_POINT_SKATE))
        viewModel1.setBoolvalue(viewModel1.screenPoint, PrefUtil.getBoolean(applicationContext, Define.EXPORT_POINT_SCREEN_POINT))
        viewModel1.setBoolvalue(viewModel1.referencePoint, PrefUtil.getBoolean(applicationContext, Define.EXPORT_POINT_REFERENCE_POINT))
        viewModel1.setIntvalue(viewModel1.degreeFormNum, PrefUtil.getInt2(applicationContext, Define.EXPORT_DEGREE_FORM))
        viewModel1.setIntvalue(viewModel1.fileFormNum1, PrefUtil.getInt2(applicationContext, Define.EXPORT_FILEFORM_NUM1))
        viewModel1.setIntvalue(viewModel1.fileFormNum2, PrefUtil.getInt2(applicationContext, Define.EXPORT_FILEFORM_NUM2))


        viewModel1.fileFormNum1.observe (this , {
            viewModel1.setStringValue(viewModel1.fileFormList1,
                dataForm1(it))})
        viewModel1.fileFormNum2.observe (this , {
            viewModel1.setStringValue(viewModel1.fileFormList2,
                dataForm2(it))})
    }


    fun savesettings(){
        PrefUtil.setBoolean(applicationContext, Define.EXPORT_ROAD_CROSS_SECTION_OUTPUT_USING, viewModel1.roadCrossSecionOutputUsing.value!!)
        PrefUtil.setBoolean(applicationContext, Define.EXPORT_POINT_ASSIST, viewModel1.pointAssist.value!!)
        PrefUtil.setBoolean(applicationContext, Define.EXPORT_POINT_MEASUREMENT, viewModel1.pointMeasurement.value!!)
        PrefUtil.setBoolean(applicationContext, Define.EXPORT_POINT_CONTROL_MEASUREMENT, viewModel1.controlMeasurement.value!!)
        PrefUtil.setBoolean(applicationContext, Define.EXPORT_POINT_INPUT, viewModel1.pointInput.value!!)
        PrefUtil.setBoolean(applicationContext, Define.EXPORT_POINT_CALCULATION, viewModel1.pointCalculation.value!!)
        PrefUtil.setBoolean(applicationContext, Define.EXPORT_POINT_SKATE, viewModel1.pointSkate.value!!)
        PrefUtil.setBoolean(applicationContext, Define.EXPORT_POINT_SCREEN_POINT, viewModel1.screenPoint.value!!)
        PrefUtil.setBoolean(applicationContext, Define.EXPORT_POINT_REFERENCE_POINT, viewModel1.referencePoint.value!!)
        PrefUtil.setInt(applicationContext, Define.EXPORT_DEGREE_FORM, viewModel1.degreeFormNum.value!!)
    }
    fun dataForm1(index: Int): String{
        var arrayList =
            when(index){
                1->arrayOf(0,1,2,3,4,5,6,7, 23,24, 74,75,76,77,78,79,80,81,82,83,84,85,86,87,88)
                7-> arrayOf(11, 13,0,1,6,7,8,16,17,15,18)
         else -> arrayOf(0,1,50,51,52,53,54,55,2,3,4,42,5,6,7,37,27,28,33,29,30,39,23,24,45,46,47,48,49,56,57,58,59,60,61,62,63,64,12,16,17,18,19,21);
      }
        var returnString = "";
        arrayList.forEachIndexed { index, element -> returnString = "$returnString[${OptionList.optionitemlist[element]}], " }
        return returnString
    }
    fun dataForm2(index: Int): String{
        var arrayList =
            when(index){
                0-> arrayOf(11, 13,0,1,6,7,8,16,17,15,18,14)
                else -> arrayOf(11,13,0,1,6,7,8,16,17,15,18,14)
            }
        var returnString = "";
        arrayList.forEachIndexed { index, element -> returnString = "$returnString[${OptionList.optionitemlist[element]}], " }
        return returnString
    }

}