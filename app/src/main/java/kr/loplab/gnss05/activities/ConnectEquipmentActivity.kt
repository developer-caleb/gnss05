package kr.loplab.gnss05.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.adapter.DialogRecyclerviewAdapter
import kr.loplab.gnss05.MyDialog
import kr.loplab.gnss05.R
import kr.loplab.gnss05.common.Define.CONNECT_MODE
import kr.loplab.gnss05.common.Define.EQUIPMENT_MAKER
import kr.loplab.gnss05.common.OptionList.Companion.CONNECT_MODE_LIST
import kr.loplab.gnss05.common.OptionList.Companion.EQUIPMENT_MAKER_LIST
import kr.loplab.gnss05.common.PrefUtil
import kr.loplab.gnss05.databinding.ActivityConnectEquipmentBinding

class ConnectEquipmentActivity : ActivityBase<ActivityConnectEquipmentBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_connect_equipment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun init() {
       viewBinding.tvEquipmentMaker.text = if(PrefUtil.getInt(applicationContext, EQUIPMENT_MAKER)==-1){"제조사명"}
        else{EQUIPMENT_MAKER_LIST[PrefUtil.getInt(applicationContext, EQUIPMENT_MAKER)]}
        viewBinding.tvConnectMode.text = if(PrefUtil.getInt(applicationContext, CONNECT_MODE)==-1){"연결모드"}
        else {CONNECT_MODE_LIST[PrefUtil.getInt(applicationContext, CONNECT_MODE)]}
    }

    override fun initListener() {
    viewBinding.header01.setOnBackButtonClickListener{onBackPressed()}
    viewBinding.makerSelectBt.setOnClickListener {
            val dlg = MyDialog(this)
            dlg.firstLayoutUse = false
            /* var list = arrayListOf<String>("GEO(GINTEC)", "South", "Kolida", "Ruide", "Sanding", "Stonex", "UniStrong",
            "Hemisphere", "GINTEC", "GEOMAX", "Hi-Target", "HuaXing")*/
             dlg.list = EQUIPMENT_MAKER_LIST

              dlg.selectedposition= PrefUtil.getInt(applicationContext, EQUIPMENT_MAKER)
            /*dlg.setOnOKClickedListener{ content ->
                Log.d(TAG, "onItemClick: $content")
            }*/
            dlg.start("메인의 내용을 변경할까요?")
              dlg.setOnListClickedListener { view, i ->
            Log.d(TAG, "initListener: $i")
            PrefUtil.setInt(applicationContext, EQUIPMENT_MAKER, i)
            viewBinding.tvEquipmentMaker.text = EQUIPMENT_MAKER_LIST[PrefUtil.getInt(applicationContext, EQUIPMENT_MAKER)]
            dlg.refresh()
            dlg.dismiss()
        }
        dlg.setHeader("장비제조사")

    }
        viewBinding.connectModeBt.setOnClickListener {
            val dlg = MyDialog(this)
            //dlg.title_dialog
            dlg.firstLayoutUse = false
            dlg.list = CONNECT_MODE_LIST
            dlg.selectedposition= PrefUtil.getInt(applicationContext, CONNECT_MODE)
            dlg.start("1123");
            dlg.setOnOKClickedListener{ content ->
                Log.d(TAG, "onItemClick: $content")
            }
            dlg.setOnListClickedListener { view, i ->
                Log.d(TAG, "initListener: $i")
                PrefUtil.setInt(applicationContext, CONNECT_MODE, i)
                viewBinding.tvConnectMode.text = CONNECT_MODE_LIST[PrefUtil.getInt(applicationContext, CONNECT_MODE)]
                dlg.refresh()
                dlg.dismiss()
            }
            dlg.setHeader("연결모드")
        }

    }

    override fun initDatabinding() {

    }


}