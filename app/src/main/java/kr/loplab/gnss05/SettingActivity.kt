package kr.loplab.gnss05

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kr.loplab.gnss05.common.Define
import kr.loplab.gnss05.common.Define.RECYCLERVIEW_LIST_MODE
import kr.loplab.gnss05.common.PrefUtil
import kr.loplab.gnss05.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    val TAG = javaClass.simpleName
    private lateinit var binding : ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.radioGrid.isChecked = !PrefUtil.getBoolean(this, RECYCLERVIEW_LIST_MODE)
        binding.radioList.isChecked = PrefUtil.getBoolean(this, RECYCLERVIEW_LIST_MODE)
        binding.radioGroupSetMainRecyclerview.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId){
                binding.radioGrid.id -> {Log.d(TAG, "onCreate: 111")
                    PrefUtil.setBoolean(this, RECYCLERVIEW_LIST_MODE, false)
                }
                binding.radioList.id -> {Log.d(TAG, "onCreate: 222")
                    PrefUtil.setBoolean(this, RECYCLERVIEW_LIST_MODE, true)
                }
            }

        }
    }

    override fun onBackPressed() {
        finishActivity(Define.REQUEST_SETTING)
        super.onBackPressed()
    }
}
