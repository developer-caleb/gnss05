package kr.loplab.gnss05.activities

import android.os.Bundle
import androidx.appcompat.app.ActionBar

import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.R
import kr.loplab.gnss05.activities.viewmodel.ReferenceContryViewModel
import kr.loplab.gnss05.databinding.ActivityReferenceCountryBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReferenceCountryActivity : ActivityBase<ActivityReferenceCountryBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_reference_country
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

  /*  //액션버튼 메뉴 액션바에 집어 넣기
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
           R.id.action_search -> {
                //검색 버튼 눌렀을 때
                return super.onOptionsItemSelected(item)
            }

            R.id.action_share -> {
                //공유 버튼 눌렀을 때
                return super.onOptionsItemSelected(item)
            }
            else -> return super.onOptionsItemSelected(item!!)
        }
    }
*/
    override fun init() {
        val ab: ActionBar? = supportActionBar
        ab?.title ="기준국설정";
    }

    override fun initListener() {
        viewBinding.header01.setOnBackButtonClickListener { onBackPressed();}
       // viewBinding.bt1.setOnClickListener { viewModel.btclick() }
    }

    override fun initDatabinding() {

    }


}
