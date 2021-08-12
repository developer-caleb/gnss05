package kr.loplab.gnss05

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayout
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.databinding.ActivityExportBinding
import kr.loplab.gnss05.databinding.ActivityPositionInformationBinding
import kr.loplab.gnss05.positioninfo.*

class PositionInformationActivity : ActivityBase<ActivityPositionInformationBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_position_information

    var tabposition = 0;

    private var fragmentManager: FragmentManager? = null
    private var fragments : Array<Fragment?> = arrayOfNulls(5)
/*    private var fragmentA: PositionDetailInfoFragment? = null
    private var fragmentB: PositionReferenceCountryFragment? = null
    private var fragmentC: PositionSatelliteMapFragment? = null
    private var fragmentD: PositionSatelliteInfoFragment? = null
    private var fragmentE: PositionSatelliteSNRFragment? = null*/
    private var transaction: FragmentTransaction? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun init() {
        fragmentManager = supportFragmentManager;
        fragments[0] = PositionDetailInfoFragment()
        fragments[1] = PositionReferenceCountryFragment()
        fragments[2] = PositionSatelliteMapFragment()
        fragments[3] = PositionSatelliteInfoFragment()
        fragments[4] = PositionSatelliteSNRFragment()

       /* fragmentA = PositionDetailInfoFragment()
        fragmentB = PositionReferenceCountryFragment()
        fragmentC = PositionSatelliteMapFragment()
        fragmentD = PositionSatelliteInfoFragment()
        fragmentE = PositionSatelliteSNRFragment()*/
        transaction = fragmentManager!!.beginTransaction();
        transaction!!.replace(R.id.frameLayout, fragments[0]!!).commitAllowingStateLoss();

    }

    override fun initListener() {
        viewBinding.header01.setOnBackButtonClickListener{onBackPressed()}



        //custom tab 완성
        viewBinding.tabLayout.getTabAt(0)!!.select()
        viewBinding.tabLayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                // tab.view.setBackgroundColor(resources.getColor(R.color.black))
                selecttab(tab.position)
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
                //  tab.view.setBackgroundColor(resources.getColor(R.color.blue))
            }
            override fun onTabReselected(tab: TabLayout.Tab) {
                //  tab.view.setBackgroundColor(resources.getColor(R.color.black))
            }

        })

    }

    override fun initDatabinding() {
    }


    fun selecttab(position : Int){
        tabposition = position;
        transaction = fragmentManager!!.beginTransaction();
        transaction!!.replace(R.id.frameLayout, fragments[position]!! ).commitAllowingStateLoss();
    }
}