package kr.loplab.gnss05.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.R
import kr.loplab.gnss05.common.Define.RECYCLERVIEW_LIST_MODE
import kr.loplab.gnss05.common.PrefUtil
import kr.loplab.gnss05.databinding.ActivityReferenceCountryBinding
import kr.loplab.gnss05.databinding.ActivitySettingBinding

class SettingActivity : ActivityBase<ActivitySettingBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_setting

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        viewBinding.radioGroupSetMainRecyclerview.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId){
                viewBinding.radioGrid.id -> {
                    Log.d(TAG, "onCreate: 111")
                    PrefUtil.setBoolean(this, RECYCLERVIEW_LIST_MODE, false)
                }
                viewBinding.radioList.id -> {
                    Log.d(TAG, "onCreate: 222")
                    PrefUtil.setBoolean(this, RECYCLERVIEW_LIST_MODE, true)
                }
            }
        }
        viewBinding.btBack01.setOnClickListener { Log.d(TAG, "onCreate: ")
        onBackPressed()
        }
    }

    override fun onBackPressed() {
        Log.d(TAG, "onBackPressed: ")
        setResult(RESULT_OK);
        //intent = Intent(this, MainActivity::class.java)
        //intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        //startActivity(intent);
        finish();
        //super.onBackPressed()

    }

    override fun init() {
        viewBinding.radioGrid.isChecked = !PrefUtil.getBoolean(this, RECYCLERVIEW_LIST_MODE)
        viewBinding.radioList.isChecked = PrefUtil.getBoolean(this, RECYCLERVIEW_LIST_MODE)
    }

    override fun initListener() {

    }

    override fun initDatabinding() {

    }


}
