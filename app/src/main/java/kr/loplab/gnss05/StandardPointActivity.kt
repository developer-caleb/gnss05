package kr.loplab.gnss05

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.loplab.gnss05.databinding.ActivityStandardPointBinding
import android.app.Activity
import android.content.Intent
import kr.loplab.gnss02.ActivityBase
import org.koin.androidx.viewmodel.ext.android.viewModel

class StandardPointActivity : ActivityBase<ActivityStandardPointBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_standard_point
    val viewModel : StandardPointViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    //액션버튼 메뉴 액션바에 집어 넣기
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
       // menuInflater.inflate(R.menu.standard_point_menu, menu)
        return true
    }
    //액션버튼 클릭 했을 때
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                onBackPressed()
                return true
            }
           /* R.id.action_search -> {
                //검색 버튼 눌렀을 때
                return super.onOptionsItemSelected(item)
            }
            R.id.action_share -> {
                //공유 버튼 눌렀을 때
                return super.onOptionsItemSelected(item)
            }*/
            else -> return super.onOptionsItemSelected(item!!)
        }
    }

    override fun init() {
        val ab: ActionBar? = supportActionBar
        ab?.title ="기준국설정";
    }

    override fun initListener() {
        viewBinding.header01.setOnBackButtonClickListener { onBackPressed();}
    }

    override fun initDatabinding() {

    }


}
