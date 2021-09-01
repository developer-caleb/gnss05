package kr.loplab.gnss05.activities.export

import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_user_format.*
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.MyDialog
import kr.loplab.gnss05.R
import kr.loplab.gnss05.adapter.UserFormatAddRecyclerViewAdapter
import kr.loplab.gnss05.adapter.UserFormatDeleteRecyclerViewAdapter
import kr.loplab.gnss05.common.PrefUtil
import kr.loplab.gnss05.databinding.ActivityUserFormatBinding
import kr.loplab.gnss05.enums.USERFORMATMAKEMODE
import java.util.*
import kotlin.collections.ArrayList

class UserFormatMake : ActivityBase<ActivityUserFormatBinding>(),
    UserFormatAddRecyclerViewAdapter.RecyclerItemClickListener,
    UserFormatDeleteRecyclerViewAdapter.RecyclerItemClickListener {
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
    private lateinit var adapterAdd : UserFormatAddRecyclerViewAdapter
    private lateinit var adapterDelete : UserFormatDeleteRecyclerViewAdapter
    var itemdata = ArrayList<Array<String>>()
    var listdata =  ArrayList<Array<String>>()
    var sperate_num = -1;
    var mode = USERFORMATMAKEMODE.ADD;
    var arrays1 = arrayListOf<String>("/", "@", "Space")
        //ArrayList(listOf(listOf("/", "1"), listOf("@", "0"), listOf("Space", "0"))) //ArrayList<String>();
    override fun init() {

        optionitemlist.forEachIndexed { index, item -> itemdata.add(arrayOf(item, index.toString(), true.toString()))}
        adapterAdd  = UserFormatAddRecyclerViewAdapter(this, itemdata)
        adapterDelete  = UserFormatDeleteRecyclerViewAdapter(this, listdata)
        adapterAdd.setClickListener(this)
        adapterDelete.setClickListener(this)
        viewBinding.recyclerviewUserFormatSettings.adapter = adapterAdd
        selectmode(USERFORMATMAKEMODE.ADD)
      //
    }

    override fun initListener() {
      //
        viewBinding.header04.setOnBackButtonClickListener{onBackPressed()}
       // viewBinding.header04.optionButtonText = "저장"
        viewBinding.header04.setOnOptionButtonClickListener{ Log.d(TAG, "initListener: ")};

        viewBinding.btAdd.setOnClickListener {
            //if (mode == USERFORMATMAKEMODE.ADD) return@setOnClickListener
            Log.d(TAG, "bt add clicked ${adapterAdd.selectedPosition}")
            selectmode(USERFORMATMAKEMODE.ADD)
            //viewBinding.recyclerviewUserFormatSettings.adapter = adapterAdd
        }
        viewBinding.btDelete.setOnClickListener {
            //if (mode == USERFORMATMAKEMODE.DELETE) return@setOnClickListener
            Log.d(TAG, "bt delete clicked")
            selectmode(USERFORMATMAKEMODE.DELETE)
           // viewBinding.recyclerviewUserFormatSettings.adapter = adapterDelete
            //viewBinding.btAdd.setBackgroundColor(applicationContext.resources.getColor(R.color.design_default_color_secondary))
        }
        viewBinding.divisionLayout.setOnClickListener {
            Log.d(TAG, "initListener: click! => dialog")
            val dlg = MyDialog(this)
            dlg.firstLayoutUse= false;
           // arrays1.add("/");  arrays1.add("@"); arrays1.add("Space");
            dlg.list = arrays1
            dlg.selectedposition= PrefUtil.getInt2(applicationContext, getString(R.string.int_seperate_sign))
            dlg.setOnOKClickedListener{ content ->
                Log.d(TAG, "onItemClick: $content")
            }
            dlg.start("메인의 내용을 변경할까요?")
            dlg.setOnCancelClickedListener { content ->
                Log.d(TAG, "onItemClick: $content")
            }
            dlg.setOnListClickedListener { view, i ->
                Log.d(TAG, "initListener: 와 된다~ $i 된다~")
                viewBinding.tvSeperate.text = arrays1[i]
                dlg.selectItem(i)
                PrefUtil.setInt(applicationContext, getString(R.string.int_seperate_sign), i)
              // selectSeperateNum(i)
                setUserFormatText()
                dlg.refresh()
                dlg.dismiss()
            }
        }


        viewBinding.headerSwitch.setOnCheckedChangeListener { compoundButton, bool ->
            Log.d(TAG, "initListener: ${bool.toString()}")
            when(bool){
             true-> {viewBinding.checkTv.text = "예"; PrefUtil.setBoolean(applicationContext, getString(
                 R.string.boolfileheader
             ), true)}
             false->  {viewBinding.checkTv.text = "아니오"; PrefUtil.setBoolean(applicationContext, getString(
                 R.string.boolfileheader
             ), false)}
            }
        }



        //confirm 버튼 눌렀을 때
        viewBinding.btConfirm.setOnClickListener {
            Log.d(TAG, "bt confirm clicked")
        when(mode){
            USERFORMATMAKEMODE.ADD ->{
                //선택 포지션이 없으면 return
                if(adapterAdd.selectedPosition ==-1){
                    Log.d(TAG, "initListener")
                    return@setOnClickListener}
                //리스트에 추가
                listdata.add(arrayOf(itemdata[adapterAdd.selectedPosition][0], adapterAdd.selectedPosition.toString(), ))
                //선택위치의 itemdata false로 만들기
                itemdata[adapterAdd.selectedPosition][2] = false.toString()
                //true인 녀석이 있으면 true인 첫번째 index가 selectposition이 되도록한다.
                adapterAdd.selectedPosition = itemdata.indexOfFirst { element -> element[2]==true.toString()}
                /*if(itemdata.indexOfFirst { element -> element[2]==true.toString() } !=-1)
                { adapterAdd.selectedPosition = itemdata.indexOfFirst { element -> element[2]==true.toString() }
                }else { return@setOnClickListener}*/
                //데이터 새로고침
                adapterAdd.notifyDataSetChanged();
                adapterDelete.notifyDataSetChanged();

                //텍스트를 listdata[0]으로 출력
                viewBinding.tvUserformat.text = listdata[0].toString()
            }
            USERFORMATMAKEMODE.DELETE ->{
                //선택 포지션이 없으면 return
                if(adapterDelete.selectedPosition ==-1||listdata.size<1){
                    Log.d(TAG, "not selected")
                    return@setOnClickListener}
                itemdata[listdata[adapterDelete.selectedPosition][1].toInt()][2] = true.toString()
                listdata.removeAt(adapterDelete.selectedPosition)
                //itemdata[adapterDelete.selectedPosition][2] = true.toString()
                adapterDelete.selectedPosition = 0;
                //adapterDelete.selectedPosition = itemdata.indexOfFirst { element -> element[2]==false.toString()}
                adapterAdd.notifyDataSetChanged();
                adapterDelete.notifyDataSetChanged();

            //viewBinding.tvUserformat.text = listdata[0].toString()
            }

        }
            setUserFormatText()
            viewBinding.recyclerviewUserFormatSettings.smoothScrollToPosition(0)
        }
    }

    override fun initDatabinding() {
        setUserFormatText()

        viewBinding.tvSeperate.text = arrays1[sperate_num]
        viewBinding.headerSwitch.isChecked = PrefUtil.getBoolean(applicationContext, getString(R.string.boolfileheader))
    }

    override fun onItemAddClick(view: View?, position: Int) {
        Log.d(TAG, "onItemClickActivity: -> class . $position")
    }

    override fun onItemDeleteClick(view: View?, position: Int) {
        Log.d(TAG, "onItemClickDialog -> class.. $position")
    }

    fun setUserFormatText(){
        sperate_num = if(PrefUtil.getInt(applicationContext, getString(R.string.int_seperate_sign))<=0){
            0 }else{ PrefUtil.getInt(applicationContext, getString(R.string.int_seperate_sign))}
       //selectSeperateNum(sperate_num)
        var text1 = "";
        listdata.forEachIndexed { index, smalldata -> text1 += "[${smalldata[0]}]${
            when(sperate_num)
            {
                2 -> " "
                else -> arrays1[sperate_num][0]
            }
        } " }

        viewBinding.tvUserformat.text ="$text1"
        //Space 로 나누는 것은 별로 좋지 못함.-> 원래 그냥 파일에도 SPACE 있음
    }

    fun selectmode(inputmode : USERFORMATMAKEMODE){
        when (inputmode){
            USERFORMATMAKEMODE.ADD ->{
                viewBinding.btAdd.setBackgroundColor(applicationContext.resources.getColor(R.color.selected_button_color))
                viewBinding.btDelete.setBackgroundColor(applicationContext.resources.getColor(R.color.unselected_button_color))
                if (this.mode == inputmode) return
                viewBinding.recyclerviewUserFormatSettings.adapter = adapterAdd

            }
            USERFORMATMAKEMODE.DELETE ->{
                viewBinding.btAdd.setBackgroundColor(applicationContext.resources.getColor(R.color.unselected_button_color))
                viewBinding.btDelete.setBackgroundColor(applicationContext.resources.getColor(R.color.selected_button_color))
                if (this.mode == inputmode) return
                viewBinding.recyclerviewUserFormatSettings.adapter = adapterDelete

            }
        }
        mode = inputmode;
        adapterAdd.notifyDataSetChanged();
        adapterDelete.notifyDataSetChanged();
    }

   /* override fun onItemClickActivity(view: View?, position: Int) {
        Log.d(TAG, "onItemClickActivity->액티비티: $position")
    }*/


    fun selectSeperateNum(num : Int){
        arrays1.forEachIndexed { index, element  -> (element as MutableList<String>)[1] = "0" }
        (arrays1[num] as MutableList<String>)[1] = "1"
    }

}
