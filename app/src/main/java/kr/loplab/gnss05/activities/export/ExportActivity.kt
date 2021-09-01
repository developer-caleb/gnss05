package kr.loplab.gnss05.activities.export

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.R
import kr.loplab.gnss05.activities.FileExportActivity
import kr.loplab.gnss05.activities.viewmodel.CoordinateViewModel
import kr.loplab.gnss05.activities.viewmodel.ExportViewModel
import kr.loplab.gnss05.common.Define
import kr.loplab.gnss05.common.Define.EXPORT_ROAD_CROSS_SECTION_OUTPUT_USING
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
            intent = Intent(this, TableMainActivity::class.java)
            startActivity(intent);
        }
        viewBinding.btExport.setOnClickListener {
            intent = Intent(this, FileExportActivity::class.java)
            startActivity(intent);

        }

        viewBinding.layoutRoadCrossSectionOutputUsing.setOnClickListener {
            viewModel1.setBoolvalue(viewModel1.roadCrossSecionOutputUsing , !viewModel1.roadCrossSecionOutputUsing.value!!)
        }
    }

    override fun initDatabinding() {
        viewModel1.setBoolvalue(viewModel1.roadCrossSecionOutputUsing, PrefUtil.getBoolean(applicationContext, EXPORT_ROAD_CROSS_SECTION_OUTPUT_USING))

    }

    fun savesettings(){
        PrefUtil.setBoolean(applicationContext, Define.EXPORT_ROAD_CROSS_SECTION_OUTPUT_USING, viewModel1.roadCrossSecionOutputUsing.value!!)

    }
}