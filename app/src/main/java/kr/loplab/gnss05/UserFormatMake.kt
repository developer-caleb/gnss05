package kr.loplab.gnss05

import android.util.Log
import android.view.View
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.databinding.ActivityUserFormatBinding

class UserFormatMake : ActivityBase<ActivityUserFormatBinding>(), UserFormatRecyclerViewAdapter.RecyclerItemClickListener  {
    override val layoutResourceId: Int
        get() = R.layout.activity_user_format
    var optionitemlist  = arrayOf("이름", "코드", "위도", "경도", "고도", "X", "Y", "Z(레벨)", "X(공간)", "Y(공간)", "Z(공간)", "도로명", "측설점",
            "단면스테이션", "중간 말뚝거리", "수직거리", "스테이션", "옵셋", "레벨편차", "시작거리", "타워명", "타워번호",
            "속성유형", "현재날짜", "현재시간", "UTC날짜", "UTC시간", "위성연산", "위성추적중", "수평오차", "수직오차",
            "Σn", "σe", "PDOP", "HDOP", "VDOP", "저장모드", "솔루션 정보", "Detailed Solution Status", "지연",
            "컷 오프 각도", "반복횟수", "안테나 높이", "속도", "방향 기준국 ID", "기준국 위도", "기준국 경도",
            "기준국 고도", "기준국과 거리", "원점 위도", "원점 경도", "원점 고도", "Station Correction x",
            "Station Correction y", "Station Correction h", "경사 보정", "Pitch", "Roll", "Yaw", "경사 각도", "투영 각도",
            "X보정", "Y보정", "Z보정", "공분산 Cxx", "공분산 Cxy", "공분산 Cxz", "공분산 Cyx", "공분산 Cyy", "공분산 Cyz",
            "공분산 Czx", "공분산 Czy", "공분산 Czz", "SD", "HD", "VD", "HA", "VA", "PPM", "참조각도", "알려진 방위각",
            "스테이션 좌표 북쪽", "스테이션 좌표 동쪽", "스테이션 좌표 높이", "반사경", "HI", "프리즘상수", "HT")
    private lateinit var adapter : UserFormatRecyclerViewAdapter
    var itemdata = ArrayList<Array<String>>()
    var listdata = ArrayList<String>()
    override fun init() {
        optionitemlist.forEachIndexed { index, item ->itemdata.add(arrayOf(item, index.toString(), true.toString()))}
        adapter  = UserFormatRecyclerViewAdapter(this, itemdata)
        adapter.setClickListener(this)
        viewBinding.recyclerviewUserFormatSettings.adapter = adapter

      //
    }

    override fun initListener() {
      //
        viewBinding.header04.setOnBackButtonClickListener{onBackPressed()}
        viewBinding.btAdd.setOnClickListener { Log.d(TAG, "bt add clicked ${adapter.selectedPosition}")
            itemdata[adapter.selectedPosition][2] = false.toString()
            adapter.notifyDataSetChanged();
        }
        viewBinding.btDelete.setOnClickListener { Log.d(TAG, "bt delete clicked") }
        viewBinding.btConfirm.setOnClickListener { Log.d(TAG, "bt confirm clicked") }
    }

    override fun initDatabinding() {
       //
    }

    override fun onItemClick(view: View?, position: Int) {
        Log.d(TAG, "onItemClick: $position recyclerview 클릭 됨")
    }


}