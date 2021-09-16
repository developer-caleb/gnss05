package kr.loplab.gnss05

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayout
import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd
import com.huace.gnssserver.gnss.data.receiver.PositionInfo
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.connection.ConnectManager
import kr.loplab.gnss05.databinding.ActivityExportBinding
import kr.loplab.gnss05.databinding.ActivityPositionInformationBinding
import kr.loplab.gnss05.positioninfo.*
import kr.loplab.gnss05.receiver.ReceiverService
import kr.loplab.gnss05.receiver.entity.ReceiverAsw
import java.util.ArrayList

class PositionInformationActivity : ActivityBase<ActivityPositionInformationBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_position_information

    var tabposition = 0;

    private var fragmentManager: FragmentManager? = null
    private var fragments : Array<Fragment?> = arrayOfNulls(5)
/*  private var fragmentA: PositionDetailInfoFragment? = null
    private var fragmentB: PositionReferenceCountryFragment? = null
    private var fragmentC: PositionSatelliteMapFragment? = null
    private var fragmentD: PositionSatelliteInfoFragment? = null
    private var fragmentE: PositionSatelliteSNRFragment? = null*/
    private var transaction: FragmentTransaction? = null
    private val mReceiver = MyReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStop() {
        unregisterReceiver(mReceiver)
        super.onStop()

    }
    inner class MyReceiver : BroadcastReceiver() {
        val TAG : String = this.javaClass.simpleName
        override fun onReceive(context: Context, intent: Intent) {

            Log.d(TAG, "onReceive: ")
            val action = intent.action
            if (action == EnumReceiverCmd.RECEIVER_ASW_SET_GNSS_POSDATA.name
            ) {
                Log.d(TAG, "onReceive: 2")
                val asw: ReceiverAsw? = ReceiverService.getBroadcastData(intent)
                if (asw== null){
                    Log.d(TAG, "onReceive: null"); return}else{
                    Log.d(TAG, "onReceive: not null")}
                runOnUiThread {
                    Log.d(TAG, "onReceive: 3")
                    when (asw.receiverCmdType) {
                        EnumReceiverCmd.RECEIVER_ASW_SET_GNSS_POSDATA -> if (asw.getParcelable() is PositionInfo) {
                            val p = asw.getParcelable() as PositionInfo
                            if (p != null && p.satellitePosition != null && p.satellitePosition
                                    .position != null) {
                                Log.d(TAG, "run:x " + p.satellitePosition.position.x.toString())
                                Log.d(TAG, "run:y " + p.satellitePosition.position.y.toString())
                                Log.d(TAG, "run:z " + p.satellitePosition.position.z.toString())
                            }
                        }
                        else -> {
                            Log.d(TAG, "onReceive: 4")
                        }
                    }
                }

            }
        }
    }
    override fun onResume() {
        super.onResume()
        when (ConnectManager.instance?.isConnected)
        {
            true ->   { Log.d(TAG, "onResume: connected")
                //리시버
                val cmds: MutableList<EnumReceiverCmd> = ArrayList()
                cmds.add(EnumReceiverCmd.RECEIVER_ASW_SET_GNSS_POSDATA)
                registerReceiver(
                    mReceiver,
                    ReceiverService.createReceiverAswIntentFilter(cmds)
                )
            }
            false ->  Log.d(TAG, "onResume: unconnected")
        }
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