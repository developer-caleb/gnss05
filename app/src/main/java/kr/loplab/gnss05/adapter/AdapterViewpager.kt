package kr.loplab.gnss05.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import kr.loplab.gnss05.*
import kr.loplab.gnss05.activities.*
import kr.loplab.gnss05.activities.coordinate.CoordinateActivity
import kr.loplab.gnss05.activities.export.ExportActivity
import kr.loplab.gnss05.activities.mobile_station.MobileStationActivity
import kr.loplab.gnss05.activities.reference_country.ReferenceCountryActivity
import kr.loplab.gnss05.activities.stop_survey.StopSurveyActivity
import kr.loplab.gnss05.common.Define
import kr.loplab.gnss05.common.Define.REQUEST_SETTING
import kr.loplab.gnss05.common.PrefUtil
import kr.loplab.gnss05.databinding.ToolViewpageradapterBinding
import kr.loplab.gnss05.model.MainIcons

class AdapterViewpager: PagerAdapter, MainpageRecyclerViewAdapter.RecyclerItemClickListener {
    val TAG = javaClass.simpleName
    public var arrlist = arrayOf("작업", "연결", "측정", "도구")
    // LayoutInflater 서비스 사용을 위한 Context 참조 저장.
    private var mContext: Context? = null
    lateinit var binding : ToolViewpageradapterBinding
    var datasarray : Array<ArrayList<MainIcons>?> = kotlin.arrayOfNulls(4)
    var arrayadapter : Array<MainpageRecyclerViewAdapter?> =  kotlin.arrayOfNulls(4)
    var data0 = ArrayList<MainIcons>()
    var data1 = ArrayList<MainIcons>()
    var data2 = ArrayList<MainIcons>()
    var data3 = ArrayList<MainIcons>()
    constructor() {}
    // Context를 전달받아 mContext에 저장하는 생성자 추가.
    constructor(context: Context?) {
        mContext = context
    }
    override fun getCount(): Int {
        return arrlist.size
    }


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        if (mContext != null) {
            binding = ToolViewpageradapterBinding.inflate(LayoutInflater.from(mContext), container, false)

            initDefault()
            init(position)
            initListener(position)
            initDatabinding(position)

        }

        container.addView(binding.root)
        return binding.root
    }
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        // 뷰페이저에서 삭제.
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as View
    }
    fun initDefault(){
        data0.clear()
        data1.clear()
        data2.clear()
        data3.clear()

        data0.add(MainIcons(R.drawable.ic_0_0_work, "작업"))
        data0.add(MainIcons(R.drawable.ic_0_1_work_group, "작업그룹"))
        data0.add(MainIcons(R.drawable.ic_0_2_coordinate, "좌표계", CoordinateActivity::class.java ))
        data0.add(MainIcons(R.drawable.ic_0_3_correction, "점 보정"))
        data0.add(MainIcons(R.drawable.ic_0_4_pointsave, "점저장소"))
        data0.add(MainIcons(R.drawable.ic_0_5_export, "내보내기", ExportActivity::class.java))
        data0.add(MainIcons(R.drawable.ic_0_6_scan, "스캔"))
        data0.add(MainIcons(R.drawable.ic_0_7_cloud, "클라우드"))
        data0.add(MainIcons(R.drawable.ic_0_8_settings, "설정", SettingActivity::class.java, REQUEST_SETTING))
        data0.add(MainIcons(R.drawable.ic_0_9_information, "정보"))

        data1.add(MainIcons(R.drawable.ic_1_0_connect_device, "장비연결", ConnectEquipmentActivity::class.java))
        data1.add(MainIcons(R.drawable.ic_1_1_move_country, "이동국", MobileStationActivity::class.java))
        data1.add(MainIcons(R.drawable.ic_1_2_reference_country, "기준국", ReferenceCountryActivity::class.java))
        data1.add(MainIcons(R.drawable.ic_1_3_stop_survey, "정지측량", StopSurveyActivity::class.java))
        data1.add(MainIcons(R.drawable.ic_1_4_connection_state, "연결상태"))
        data1.add(MainIcons(R.drawable.ic_1_5_connection_save, "연결저장"))
        data1.add(MainIcons(R.drawable.ic_1_6_connection_infomation, "연결정보"))
        data1.add(MainIcons(R.drawable.ic_1_7_sensor_correction, "센서보정"))
        data1.add(MainIcons(R.drawable.ic_1_8_setting_equipment, "장비설정"))
        data1.add(MainIcons(R.drawable.ic_1_9_frequency_setting, "주파수 설정"))
        data1.add(MainIcons(R.drawable.ic_1_10_change_location, "위치변경"))
        data1.add(MainIcons(R.drawable.ic_1_11_enroll_equipment, "장비등록"))

        data2.add(MainIcons(R.drawable.ic_2_0_status_work, "현황작업", StatusWorkActivity::class.java))
        data2.add(MainIcons(R.drawable.ic_2_1_detail_survey, "상세 측량"))
        data2.add(MainIcons(R.drawable.ic_2_2_cad, "CAD (측설)"))
        data2.add(MainIcons(R.drawable.ic_2_3_point_stack_out, "점측설"))
        data2.add(MainIcons(R.drawable.ic_2_4_line_stack_out, "라인측설"))
        data2.add(MainIcons(R.drawable.ic_2_5_gis, "GIS"))
        data2.add(MainIcons(R.drawable.ic_2_6_road_stack_out, "도로측설"))
        data2.add(MainIcons(R.drawable.ic_2_7_road_offset, "도로옵셋"))
        data2.add(MainIcons(R.drawable.ic_2_8_cross_section_measurement, "횡단면 측정"))
        data2.add(MainIcons(R.drawable.ic_2_9_cross_survey, "횡단측량"))
        data2.add(MainIcons(R.drawable.ic_2_10_bridge_survey, "교량측량"))
        data2.add(MainIcons(R.drawable.ic_2_11_conical_superelevation_stakeout, "원추형 편경사측설"))
        data2.add(MainIcons(R.drawable.ic_2_12_wire_stakeout, "전선측설"))
        data2.add(MainIcons(R.drawable.ic_2_13_electric_stakeout, "전기 측설"))
        data2.add(MainIcons(R.drawable.ic_2_14_level_control, "레벨제어"))
        data2.add(MainIcons(R.drawable.ic_2_15_curve_stakeout, "곡선측설"))
        data2.add(MainIcons(R.drawable.ic_2_16_exist_measurement, "기존측정"))
        data2.add(MainIcons(R.drawable.ic_2_17_railroad_stakeout, "철길측설"))
        data2.add(MainIcons(R.drawable.ic_2_18_layer, "레이어"))
        data2.add(MainIcons(R.drawable.ic_2_19_setting_measuring_range, "측정 범위 설정"))

        data3.add(MainIcons(R.drawable.ic_3_0_calibration, "캘리브레이션"))
        data3.add(MainIcons(R.drawable.ic_3_1_coordinate_inverse_calculation, "좌표역계산"))
        data3.add(MainIcons(R.drawable.ic_3_2_angle_converter, "각도변환기"))
        data3.add(MainIcons(R.drawable.ic_3_3_area_calculation, "면적계산"))
        data3.add(MainIcons(R.drawable.ic_3_4_cogo_calculation, "COGO계산"))
        data3.add(MainIcons(R.drawable.ic_3_5_calculation, "계산기"))
        data3.add(MainIcons(R.drawable.ic_3_6_external_radio, "외장라디오"))
        data3.add(MainIcons(R.drawable.ic_3_7_calculation_volumn, "볼륨계산"))
        data3.add(MainIcons(R.drawable.ic_3_8_reset_location, "위치 재설정"))
        data3.add(MainIcons(R.drawable.ic_3_9_ftp_share, "FTP 공유"))
        data3.add(MainIcons(R.drawable.ic_3_10_share, "공유"))
        datasarray[0] = data0
        datasarray[1] = data1
        datasarray[2] = data2
        datasarray[3] = data3
    }
    fun init(position: Int){
        if(PrefUtil.getBoolean(mContext!!, Define.RECYCLERVIEW_LIST_MODE)){
            binding.recyclerviewMain.layoutManager = LinearLayoutManager(mContext);
            Log.d(TAG, "init: listmode-> RECYCLERVIEW_LIST_MODE" )
            arrayadapter[position] = MainpageRecyclerViewAdapter(mContext, datasarray[position]!!, R.layout.recyclerview_item_vertical)
        }else{
            Log.d(TAG, "init: listmode-> RECYCLERVIEW_GRID_MODE" )
            binding.recyclerviewMain.layoutManager = GridLayoutManager(mContext, 3)
            arrayadapter[position] = MainpageRecyclerViewAdapter(mContext, datasarray[position]!!, R.layout.recyclerview_item_grid)
        }
        binding.recyclerviewMain.adapter = arrayadapter[position]
        arrayadapter[position]?.setClickListener(this)
        arrayadapter[position]?.notifyDataSetChanged()
    }
    fun initListener(position: Int){


    }
    fun initDatabinding(position: Int){}
    override fun onItemClick(view: View?, position: Int) {
        Log.d("TAG", "onItemClick: $position clicked!")
        when (position){
            /*      0 ->    { intent = Intent(this, StandardPointActivity::class.java)
                      startActivity(intent);}
                  1 ->    { intent = Intent(this, NaverMap::class.java)
                      startActivity(intent);}
                  2 -> {
                      val dlg = MyDialog(this)
                      dlg.setClickListener(this)
                      dlg.setOnOKClickedListener{ content ->
                          Log.d(TAG, "onItemClick: $content")
                      }
                      dlg.start("메인의 내용을 변경할까요?")
                  }
                  3 -> {
                      val nextIntent = Intent(this, ColorPickerdialog::class.java)
                      startActivity(nextIntent);
                  }
                  4 -> {
                      val nextIntent = Intent(this, FileExportActivity::class.java)
                      startActivity(nextIntent);
                  }
            */
        }
    }

}