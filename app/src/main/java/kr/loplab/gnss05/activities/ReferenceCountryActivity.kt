package kr.loplab.gnss05.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.ViewModelProvider

import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.MyDialog
import kr.loplab.gnss05.R
import kr.loplab.gnss05.activities.viewmodel.ReferenceContryViewModel
import kr.loplab.gnss05.common.Define
import kr.loplab.gnss05.common.Define.*
import kr.loplab.gnss05.common.OptionList
import kr.loplab.gnss05.common.OptionList.Companion.COLLECTION_INTERVAL_LIST
import kr.loplab.gnss05.common.OptionList.Companion.DATA_CONNECTION_TYPE_List
import kr.loplab.gnss05.common.OptionList.Companion.DEPLACEMENT_MODE_LIST
import kr.loplab.gnss05.common.OptionList.Companion.NETWORK_MODE_List
import kr.loplab.gnss05.common.PrefUtil
import kr.loplab.gnss05.databinding.ActivityReferenceCountryBinding

class ReferenceCountryActivity : ActivityBase<ActivityReferenceCountryBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_reference_country
    lateinit var viewModel1:ReferenceContryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun init() {

        val ab: ActionBar? = supportActionBar
        ab?.title ="기준국설정";
        //viewbinding
        viewModel1 = ViewModelProvider(this).get(ReferenceContryViewModel::class.java)
        viewBinding.referencecontryviewmodel = viewModel1
    }

    override fun initListener() {
        viewBinding.header01.setOnBackButtonClickListener { onBackPressed();}
        viewBinding.referenceCountryIdLayout.setOnClickListener {
            viewBinding.referenceCountryId.requestFocus()
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(viewBinding.referenceCountryId,0)
        }
        viewBinding.deleteId.setOnClickListener {
            viewBinding.referenceCountryId.setText("")
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(viewBinding.referenceCountryId.windowToken,0)
        }
        viewBinding.startModeLayout.setOnClickListener {
            val dlg = MyDialog(this)
            dlg.firstLayoutUse = false
            dlg.list = OptionList.START_MODE_LIST
            dlg.selectedposition= PrefUtil.getInt2(applicationContext, Define.START_MODE)
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                Log.d(TAG, "initListener: $i")
                PrefUtil.setInt(applicationContext, Define.START_MODE, i)
                viewBinding.tvStartMode.text = OptionList.START_MODE_LIST[PrefUtil.getInt2(applicationContext,
                    Define.START_MODE
                )]
                dlg.refresh()
                dlg.dismiss()
                if(i==1){
                    intent = Intent(this, ReferenceCoordinateSetting::class.java)
                    startActivity(intent);
                }
            }
            dlg.setHeader("시작 모드")
        }
       viewBinding.layoutDisplacementMode.setOnClickListener {
           val dlg = MyDialog(this)
           var alist = DEPLACEMENT_MODE_LIST
           var prefvalue = Define.DEPLACEMENT_MODE
           dlg.firstLayoutUse = false
           dlg.list = alist
           dlg.selectedposition= PrefUtil.getInt2(applicationContext, prefvalue)
           dlg.start("")
           dlg.setOnListClickedListener { view, i ->
               Log.d(TAG, "initListener: $i")
               PrefUtil.setInt(applicationContext, prefvalue, i)
               viewBinding.tvDisplacementMode.text = alist[PrefUtil.getInt2(applicationContext, prefvalue
               )]
               dlg.refresh()
               dlg.dismiss()
           }
           dlg.setHeader("변위 모드")
       }
        viewBinding.layoutCollectionInterval.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = COLLECTION_INTERVAL_LIST
            var prefvalue = Define.COLLECTION_INTERVAL
            dlg.firstLayoutUse = false
            dlg.list = alist
            dlg.selectedposition= PrefUtil.getInt2(applicationContext, prefvalue)
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                Log.d(TAG, "initListener: $i")
                PrefUtil.setInt(applicationContext, prefvalue, i)
                viewBinding.tvCollectionInterval.text = alist[PrefUtil.getInt2(applicationContext, prefvalue
                )]
                dlg.refresh()
                dlg.dismiss()
            }
            dlg.setHeader("수집간격")
        }
        viewBinding.layoutDataConnectionType.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = DATA_CONNECTION_TYPE_List
            var prefvalue = Define.DATA_CONNECTION_TYPE
            dlg.firstLayoutUse = false
            dlg.list = alist
            dlg.selectedposition= PrefUtil.getInt2(applicationContext, prefvalue)
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                Log.d(TAG, "initListener: $i")
                viewModel1.setDataConnectionType(i)
                PrefUtil.setInt(applicationContext, prefvalue, i)
                viewBinding.tvDataConnectionType.text = alist[PrefUtil.getInt2(applicationContext, prefvalue
                )]
                dlg.refresh()
                dlg.dismiss()
            }
            dlg.setHeader("데이터 연결방식")
        }
        viewBinding.layoutReferenceCountryAutoplay.setOnClickListener {
            viewBinding.swReferenceCountryAutoplay.isChecked = !viewBinding.swReferenceCountryAutoplay.isChecked
            PrefUtil.setBoolean(applicationContext, REFERENCE_COUNTRY_AUTO_PLAY, viewBinding.swReferenceCountryAutoplay.isChecked)
            //PrefUtil.setBoolean(applicationContext, REFERENCE_COUNTRY_AUTO_PLAY, true)
        }
        viewBinding.layoutRawDataSave.setOnClickListener {
            var bool = !viewModel1.bool_rawdatasave.value!!
            viewModel1.setRawDatavalue(bool)
            PrefUtil.setBoolean(this, RAW_DATA_SAVE, bool);
        }
        viewBinding.layoutCurrentPointName.setOnClickListener {
            Log.d(TAG, "initListener: layoutCurrentPointName Clicked!")
            viewBinding.etCurrentPointName.requestFocus()
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(viewBinding.etCurrentPointName,0)
        }
        viewBinding.layoutNetworkAutoConnect.setOnClickListener {
            viewBinding.swNetworkAutoConnect.isChecked = !viewBinding.swNetworkAutoConnect.isChecked
            PrefUtil.setBoolean(applicationContext, NETWORK_AUTO_CONNECT, viewBinding.swNetworkAutoConnect.isChecked)
        }
        viewBinding.layoutNetworkMode.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = NETWORK_MODE_List
            var prefvalue = Define.NETWORK_MODE
            dlg.firstLayoutUse = false
            dlg.list = alist
            dlg.selectedposition= PrefUtil.getInt2(applicationContext, prefvalue)
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                Log.d(TAG, "initListener: $i")
                viewModel1.setDataConnectionType(i)
                PrefUtil.setInt(applicationContext, prefvalue, i)
                viewBinding.tvNetworkMode.text = alist[PrefUtil.getInt2(applicationContext, prefvalue
                )]
                dlg.refresh()
                dlg.dismiss()
            }
            dlg.setHeader("네트워크 모드")
        }
    }

    override fun initDatabinding() {
        viewModel1.setRawDatavalue(PrefUtil.getBoolean(this, RAW_DATA_SAVE))
        viewModel1.setDataConnectionType(PrefUtil.getInt2(this, DATA_CONNECTION_TYPE))
        viewBinding.tvStartMode.text = OptionList.START_MODE_LIST[PrefUtil.getInt2(applicationContext,
            Define.START_MODE
        )]
        viewBinding.tvDisplacementMode.text = DEPLACEMENT_MODE_LIST[PrefUtil.getInt2(applicationContext,
            Define.DEPLACEMENT_MODE
        )]
        viewBinding.tvCollectionInterval.text = COLLECTION_INTERVAL_LIST[PrefUtil.getInt2(applicationContext,
            Define.COLLECTION_INTERVAL
        )]
        viewBinding.swReferenceCountryAutoplay.isChecked =PrefUtil.getBoolean(applicationContext, REFERENCE_COUNTRY_AUTO_PLAY)
        viewBinding.swNetworkAutoConnect.isChecked =PrefUtil.getBoolean(applicationContext, NETWORK_AUTO_CONNECT)
        viewBinding.tvDataConnectionType.text = DATA_CONNECTION_TYPE_List[PrefUtil.getInt2(applicationContext, Define.DATA_CONNECTION_TYPE
        )]
        viewBinding.tvNetworkMode.text = NETWORK_MODE_List[PrefUtil.getInt2(applicationContext, NETWORK_MODE
        )]

    }


}
