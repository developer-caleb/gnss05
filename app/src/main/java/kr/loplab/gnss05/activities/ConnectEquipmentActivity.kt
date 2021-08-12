package kr.loplab.gnss05.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.DialogRecyclerviewAdapter
import kr.loplab.gnss05.MyDialog
import kr.loplab.gnss05.R
import kr.loplab.gnss05.databinding.ActivityConnectEquipmentBinding
import kr.loplab.gnss05.databinding.ActivityFileExportBinding

class ConnectEquipmentActivity : ActivityBase<ActivityConnectEquipmentBinding>(), DialogRecyclerviewAdapter.RecyclerItemClickListener {
    override val layoutResourceId: Int
        get() = R.layout.activity_connect_equipment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun init() {

    }

    override fun initListener() {
    viewBinding.header01.setOnBackButtonClickListener{onBackPressed()}
    viewBinding.makerSelectBt.setOnClickListener {
            val dlg = MyDialog(this)
            dlg.firstLayoutUse = false
            var list = ArrayList<List<String>>()
            list.add(listOf("/", "1"))
            dlg.setClickListener(this)
            dlg.setOnOKClickedListener{ content ->
                Log.d(TAG, "onItemClick: $content")
            }
            dlg.start("메인의 내용을 변경할까요?")
        dlg.setHeader("장비제조사")

    }
        viewBinding.connectModeBt.setOnClickListener {
            val dlg = MyDialog(this)
            dlg.firstLayoutUse = false
            var list = ArrayList<List<String>>()
            list.add(listOf("/", "1"))
            dlg.setClickListener(this)
            dlg.setOnOKClickedListener{ content ->
                Log.d(TAG, "onItemClick: $content")
            }
            dlg.start("메인의 내용을 변경할까요?")
        }

    }

    override fun initDatabinding() {

    }

    override fun onItemClickDialog(view: View?, position: Int) {
        Log.d(TAG, "onItemClickDialog: position -> $position")
    }
}