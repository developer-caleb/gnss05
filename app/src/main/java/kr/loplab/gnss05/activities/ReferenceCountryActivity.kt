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
import kr.loplab.gnss05.activities.cors_servermanager.CORSServerManagerActivity
import kr.loplab.gnss05.activities.viewmodel.ReferenceCountryViewModel
import kr.loplab.gnss05.activities.workmanager.WorkManagerActivity
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
    lateinit var viewModel1:ReferenceCountryViewModel
    var startModeNum = 0;
    var deplaceModeNum = 0;
    var collectionModeNum = 0;
     var dataConnectionTypeNum = 0;
    var networkModeNum = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun init() {
        val ab: ActionBar? = supportActionBar
        ab?.title ="기준국설정";
        //viewbinding
        viewModel1 = ViewModelProvider(this).get(ReferenceCountryViewModel::class.java)
        viewBinding.referencecontryviewmodel = viewModel1
    }

    override fun initListener() {
        viewBinding.settingSatelliteBt.setOnClickListener {
            Log.d(TAG, "initListener: settingSatelliteBt clicked")
            intent = Intent(this, SettingSatelliteActivity::class.java)
            startActivity(intent);
        }
        viewBinding.saveAndApplyBt.setOnClickListener {
            savesettings()


            Log.d(TAG, "initListener: saveAndApplyBt clicked")
        }
        viewBinding.applyBt.setOnClickListener {
            Log.d(TAG, "initListener: applyBt clicked")
        }
        viewBinding.header01.setOnBackButtonClickListener { onBackPressed();}
        viewBinding.corsSettingBt.setOnClickListener {
            intent = Intent(this, CORSServerManagerActivity::class.java)
            startActivity(intent);
        }
        viewBinding.btOpenWorkManager.setOnClickListener {
            intent = Intent(this, WorkManagerActivity::class.java)
            startActivity(intent);
        }
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
            dlg.selectedposition= startModeNum
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                Log.d(TAG, "initListener: $i")
                startModeNum = i
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
           dlg.selectedposition= deplaceModeNum
           dlg.start("")
           dlg.setOnListClickedListener { view, i ->
               Log.d(TAG, "initListener: $i")

               deplaceModeNum = i
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
            dlg.selectedposition= collectionModeNum
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                Log.d(TAG, "initListener: $i")
                collectionModeNum = i

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
            dlg.selectedposition= dataConnectionTypeNum
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                Log.d(TAG, "initListener: $i")
                viewModel1.setDataConnectionType(i)
                dataConnectionTypeNum = i

                viewBinding.tvDataConnectionType.text = alist[PrefUtil.getInt2(applicationContext, prefvalue
                )]
                dlg.refresh()
                dlg.dismiss()
            }
            dlg.setHeader("데이터 연결방식")
        }
        viewBinding.layoutReferenceCountryAutoplay.setOnClickListener {
            viewBinding.swReferenceCountryAutoplay.isChecked = !viewBinding.swReferenceCountryAutoplay.isChecked
        }
        viewBinding.layoutRawDataSave.setOnClickListener {
            var bool = !viewModel1.bool_rawdatasave.value!!
            viewModel1.setRawDatavalue(bool)

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

        }
        viewBinding.layoutNetworkMode.setOnClickListener {
            val dlg = MyDialog(this)
            var alist = NETWORK_MODE_List
            var prefvalue = Define.NETWORK_MODE
            dlg.firstLayoutUse = false
            dlg.list = alist
            dlg.selectedposition= networkModeNum
            dlg.start("")
            dlg.setOnListClickedListener { view, i ->
                Log.d(TAG, "initListener: $i")
                networkModeNum = i

                viewModel1.setNetworkMode(i)
                viewBinding.tvNetworkMode.text = alist[PrefUtil.getInt2(applicationContext, prefvalue
                )]
                dlg.refresh()
                dlg.dismiss()
            }
            dlg.setHeader("네트워크 모드")
        }
    }

    override fun initDatabinding() {
        startModeNum = PrefUtil.getInt2(applicationContext, Define.START_MODE)
        deplaceModeNum = PrefUtil.getInt2(applicationContext, Define.DEPLACEMENT_MODE)
        collectionModeNum = PrefUtil.getInt2(applicationContext, Define.COLLECTION_INTERVAL)
        dataConnectionTypeNum = PrefUtil.getInt2(applicationContext, Define.DATA_CONNECTION_TYPE)
        networkModeNum = PrefUtil.getInt2(applicationContext, NETWORK_MODE)

        viewModel1.setRawDatavalue(PrefUtil.getBoolean(this, RAW_DATA_SAVE))
        viewModel1.setDataConnectionType(PrefUtil.getInt2(this, DATA_CONNECTION_TYPE))
        viewModel1.setNetworkMode(PrefUtil.getInt2(this, NETWORK_MODE))
        viewModel1.setAutoApn(PrefUtil.getBoolean(this, AUTO_APN))

        viewBinding.tvStartMode.text = OptionList.START_MODE_LIST[startModeNum]
        viewBinding.tvDisplacementMode.text = DEPLACEMENT_MODE_LIST[deplaceModeNum]
        viewBinding.tvCollectionInterval.text = COLLECTION_INTERVAL_LIST[collectionModeNum]
        viewBinding.tvDataConnectionType.text = DATA_CONNECTION_TYPE_List[dataConnectionTypeNum]
        viewBinding.tvNetworkMode.text = NETWORK_MODE_List[networkModeNum]
        viewBinding.swReferenceCountryAutoplay.isChecked =PrefUtil.getBoolean(applicationContext, REFERENCE_COUNTRY_AUTO_PLAY)
        viewBinding.swNetworkAutoConnect.isChecked =PrefUtil.getBoolean(applicationContext, NETWORK_AUTO_CONNECT)

    }
    fun savesettings(){
        PrefUtil.setInt(applicationContext, Define.NETWORK_MODE, networkModeNum)
        PrefUtil.setInt(applicationContext, Define.START_MODE, startModeNum)
        PrefUtil.setInt(applicationContext, Define.DEPLACEMENT_MODE, deplaceModeNum)
        PrefUtil.setInt(applicationContext, Define.COLLECTION_INTERVAL, collectionModeNum)
        PrefUtil.setInt(applicationContext, Define.DATA_CONNECTION_TYPE, dataConnectionTypeNum)
        PrefUtil.setBoolean(applicationContext, REFERENCE_COUNTRY_AUTO_PLAY, viewBinding.swReferenceCountryAutoplay.isChecked)
        PrefUtil.setBoolean(applicationContext, NETWORK_AUTO_CONNECT, viewBinding.swNetworkAutoConnect.isChecked)
        PrefUtil.setBoolean(this, RAW_DATA_SAVE, viewModel1.bool_rawdatasave.value!!);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode== RESULT_OK && requestCode == REQUEST_WORKMANAGER)
        {

            initDatabinding()
        }

        if(resultCode== RESULT_OK && requestCode == REQUEST_CORS_SERVER_MANAGER)
        {
            initDatabinding()
        }
    }


}
