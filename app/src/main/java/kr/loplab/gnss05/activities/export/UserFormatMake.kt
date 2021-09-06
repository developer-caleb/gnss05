package kr.loplab.gnss05.activities.export

import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_user_format.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.MyDialog
import kr.loplab.gnss05.R
import kr.loplab.gnss05.activities.workmanager.AppDatabase
import kr.loplab.gnss05.adapter.UserFormatAddRecyclerViewAdapter
import kr.loplab.gnss05.adapter.UserFormatDeleteRecyclerViewAdapter
import kr.loplab.gnss05.common.Define
import kr.loplab.gnss05.common.OptionList
import kr.loplab.gnss05.common.OptionList.Companion.SEPERATOR_LIST
import kr.loplab.gnss05.common.PrefUtil
import kr.loplab.gnss05.databinding.ActivityUserFormatBinding
import kr.loplab.gnss05.enums.USERFORMATMAKEMODE
import org.json.JSONArray
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class UserFormatMake : ActivityBase<ActivityUserFormatBinding>(),
    UserFormatAddRecyclerViewAdapter.RecyclerItemClickListener,
    UserFormatDeleteRecyclerViewAdapter.RecyclerItemClickListener {
    override val layoutResourceId: Int
        get() = R.layout.activity_user_format

    private lateinit var adapterAdd : UserFormatAddRecyclerViewAdapter
    private lateinit var adapterDelete : UserFormatDeleteRecyclerViewAdapter
    var itemdata = ArrayList<Array<String>>()
    var listdata =  ArrayList<Array<String>>()
    var mode = USERFORMATMAKEMODE.ADD;

    var requestCode= 0;
    var selectedPosition = -1;
    var fileFormatList : MutableList<FileFormat>? = null

    lateinit var db : AppDatabase


    //ArrayList(listOf(listOf("/", "1"), listOf("@", "0"), listOf("Space", "0"))) //ArrayList<String>();
    override fun init() {

        OptionList.optionitemlist.forEachIndexed { index, item -> itemdata.add(arrayOf(item, index.toString(), true.toString()))}
        adapterAdd  = UserFormatAddRecyclerViewAdapter(this, itemdata)
        adapterDelete  = UserFormatDeleteRecyclerViewAdapter(this, listdata)
        adapterAdd.setClickListener(this)
        adapterDelete.setClickListener(this)
        viewBinding.recyclerviewUserFormatSettings.adapter = adapterAdd
        selectmode(USERFORMATMAKEMODE.ADD)

        db = Room.databaseBuilder(this, AppDatabase::class.java, Define.FILEFORMAT_DB)
            .allowMainThreadQueries() //메인쓰레드에서 작동시킬 때 사용
            .fallbackToDestructiveMigration()
            .build()

        if (intent.hasExtra(Define.REQUEST_CODE_STRING) )
        {
            requestCode = intent.getIntExtra(Define.REQUEST_CODE_STRING,0)
            Log.d(TAG, "init: requestcode $requestCode")
        }
        if (intent.hasExtra("selectPosition") )
        {
            selectedPosition = intent.getIntExtra("selectPosition",0)
            Log.d(TAG, "init: selectedPosition $selectedPosition")
        }
      //
    }

    override fun initListener() {
      //
        viewBinding.header04.setOnBackButtonClickListener{onBackPressed()}
       // viewBinding.header04.optionButtonText = "저장"
        viewBinding.header04.setOnOptionButtonClickListener {
            //저장버튼 클릭
            Log.d(TAG, "initListener: ")
            if (viewBinding.etFormatName.text.toString().isEmpty()) {
                showToast("형식은 반드시 포함되어야 합니다.")
                return@setOnOptionButtonClickListener
            }
            when (requestCode) {
                Define.REQUEST_FILE_FORMAT_ADD -> {
                    lifecycleScope.launch(Dispatchers.IO) {
                        Log.d(TAG, "initListener: bt confirm")

                        db.fileFormatDao().insert(
                            FileFormat(
                                viewBinding.etFormatName.text.toString(),
                                "csv",
                                getFormatDescription(),
                                getSeperateNum()
                            )
                        )
                    }
                    setResult(RESULT_OK)
                    finish()
                }
                Define.REQUEST_FILE_FORMAT_EDIT -> {
                    Log.d(TAG, "initListener: edit! confirm")
                    lifecycleScope.launch(Dispatchers.IO) {
                        var fileFormatModel = db.fileFormatDao().all[selectedPosition]
                        fileFormatModel.formatName = viewBinding.etFormatName.text.toString()
                        fileFormatModel.extensionName = "csv"
                        fileFormatModel.formatDescription =  getFormatDescription()
                        fileFormatModel.seperator =  getSeperateNum()
                        db.fileFormatDao().update(fileFormatModel)
                    }
                    setResult(RESULT_OK)
                    finish()
                }

            };
        }
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
            dlg.list = SEPERATOR_LIST
            dlg.selectedposition= PrefUtil.getInt2(applicationContext, getString(R.string.int_seperate_sign))
            dlg.setOnOKClickedListener{ content ->
                Log.d(TAG, "onItemClick: $content")
            }
            dlg.start("메인의 내용을 변경할까요?")
            dlg.setOnCancelClickedListener { content ->
                Log.d(TAG, "onItemClick: $content")
            }
            dlg.setOnListClickedListener { view, i ->
                viewBinding.tvSeperate.text = SEPERATOR_LIST[i]
                dlg.selectItem(i)
                PrefUtil.setInt(applicationContext, getString(R.string.int_seperate_sign), i)
              // selectSeperateNum(i)
                setUserFormatText()
                dlg.refresh()
                dlg.dismiss()
            }
        }

        viewBinding.layoutFileheader.setOnClickListener { viewBinding.swFileheader.performClick() }
        viewBinding.swFileheader.setOnCheckedChangeListener { compoundButton, bool ->
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
                adapterAdd.notifyDataSetChanged();
                adapterDelete.notifyDataSetChanged();
                viewBinding.tvUserformat.text = listdata[0].toString()
            }
            USERFORMATMAKEMODE.DELETE ->{
                if(adapterDelete.selectedPosition ==-1||listdata.size<1){
                    Log.d(TAG, "not selected")
                    return@setOnClickListener}
                itemdata[listdata[adapterDelete.selectedPosition][1].toInt()][2] = true.toString()
                listdata.removeAt(adapterDelete.selectedPosition)
                adapterDelete.selectedPosition = 0;
                adapterAdd.notifyDataSetChanged();
                adapterDelete.notifyDataSetChanged();
            }

        }
            setUserFormatText()
            viewBinding.recyclerviewUserFormatSettings.smoothScrollToPosition(0)
        }
    }

    override fun initDatabinding() {
        setUserFormatText()

        viewBinding.tvSeperate.text = SEPERATOR_LIST[getSeperateNum()]
        viewBinding.swFileheader.isChecked = PrefUtil.getBoolean(applicationContext, getString(R.string.boolfileheader))
        if(requestCode == Define.REQUEST_FILE_FORMAT_EDIT) {
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    fileFormatList = db.fileFormatDao().all
                    viewBinding.etFormatName.setText(fileFormatList!![selectedPosition].formatName)
                    PrefUtil.setInt(applicationContext, getString(R.string.int_seperate_sign), fileFormatList!![selectedPosition].seperator)
                    viewBinding.tvSeperate.text = SEPERATOR_LIST[getSeperateNum()]


                    var jArray = JSONArray(fileFormatList!![selectedPosition].formatDescription);
                    var formatDescriptionFormatList = ArrayList<Array<String>>()
                    if (jArray != null) {
                        for (i in 0 until jArray.length()) {
                            var jArray2 = JSONArray(jArray[i].toString())
                            println("JARRAY2 : ${jArray2.toString()}")
                            formatDescriptionFormatList.add(arrayOf(jArray2[0] as String, jArray2[1] as String));
                        }
                    }
                   listdata.clear(); listdata.addAll(formatDescriptionFormatList);
                    Log.d(TAG, "initDatabinding: new list data => ${listdata.toString()}")
                  setUserFormatText()
                    listdata.forEachIndexed { index, element -> itemdata[element[1].toInt()][2] = false.toString(); }
                    adapterAdd.notifyDataSetChanged()
                    adapterDelete.notifyDataSetChanged()
                    Log.d(TAG, "initDatabinding: new item data => ${itemdata.toString()}")

                    //여기 빡세다.. index 가 1인 걸 넣으면서 계속 빼주면 되는데
                }catch (e: Exception){
                    Log.e(TAG, "initDatabinding: ", e )
                }
            }
        }
    }

    override fun onItemAddClick(view: View?, position: Int) {
        Log.d(TAG, "onItemClickActivity: -> class . $position")
    }

    override fun onItemDeleteClick(view: View?, position: Int) {
        Log.d(TAG, "onItemClickDialog -> class.. $position")
    }

    fun setUserFormatText(){
       //selectSeperateNum(sperate_num)
        var text1 = "";
        listdata.forEachIndexed { index, smalldata -> text1 += "[${smalldata[0]}]${getSeperator()} " }

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


    fun getSeperateNum():Int{
        var sepearatorNum =  PrefUtil.getInt2(applicationContext, getString(R.string.int_seperate_sign))
        return sepearatorNum;
    }
    fun getSeperator():String{
        var sepearatorNum =  PrefUtil.getInt2(applicationContext, getString(R.string.int_seperate_sign))
        return when(sepearatorNum)
        {
            2 -> " "
            else -> SEPERATOR_LIST[sepearatorNum][0].toString()
        }
    }
    fun getFormatDescription():String{
        //var jsonlist = ArrayList<String>()
         //   listdata.forEachIndexed { index, element -> jsonlist.add(element[0]) }
        var jsonElements = Gson().toJsonTree(listdata)
        return jsonElements.toString()
    }
}
