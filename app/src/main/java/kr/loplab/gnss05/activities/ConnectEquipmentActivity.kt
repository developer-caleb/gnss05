package kr.loplab.gnss05.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.adapter.DialogRecyclerviewAdapter
import kr.loplab.gnss05.MyDialog
import kr.loplab.gnss05.R
import kr.loplab.gnss05.databinding.ActivityConnectEquipmentBinding

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
            //foreach써가지고 만약에 position이 preference의 int와 값이 같으면 1로하고 아니면 0으로 .. 내 생각엔 그냥 dilog 한개 더 만들고,
            //이걸 그냥 ArrayList<String>()으로 해가지고 position값만 받아와서 비교해도 괜찮을 듯?
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
            var list = ArrayList<String>()
            list.add("/")
            //이걸 -> 옮기기
            dlg.list = list
            dlg.selectedposition = 0
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