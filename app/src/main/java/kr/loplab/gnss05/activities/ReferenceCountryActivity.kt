package kr.loplab.gnss05.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBar
import kotlinx.android.synthetic.main.activity_main.*

import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.MyDialog
import kr.loplab.gnss05.PositionInformationActivity
import kr.loplab.gnss05.R
import kr.loplab.gnss05.activities.viewmodel.ReferenceContryViewModel
import kr.loplab.gnss05.common.Define
import kr.loplab.gnss05.common.OptionList
import kr.loplab.gnss05.common.OptionList.Companion.COLLECTION_INTERVAL_LIST
import kr.loplab.gnss05.common.OptionList.Companion.DEPLACEMENT_MODE_LIST
import kr.loplab.gnss05.common.PrefUtil
import kr.loplab.gnss05.databinding.ActivityReferenceCountryBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReferenceCountryActivity : ActivityBase<ActivityReferenceCountryBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_reference_country
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun init() {
        val ab: ActionBar? = supportActionBar
        ab?.title ="기준국설정";

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
        viewBinding.layoutReferenceCountryAutoplay.setOnClickListener {
            viewBinding.swReferenceCountryAutoplay.isChecked = !viewBinding.swReferenceCountryAutoplay.isChecked
        }
    }

    override fun initDatabinding() {
        viewBinding.tvStartMode.text = OptionList.START_MODE_LIST[PrefUtil.getInt2(applicationContext,
            Define.START_MODE
        )]
        viewBinding.tvDisplacementMode.text = DEPLACEMENT_MODE_LIST[PrefUtil.getInt2(applicationContext,
            Define.DEPLACEMENT_MODE
        )]
        viewBinding.tvCollectionInterval.text = COLLECTION_INTERVAL_LIST[PrefUtil.getInt2(applicationContext,
            Define.COLLECTION_INTERVAL
        )]
    }


}
