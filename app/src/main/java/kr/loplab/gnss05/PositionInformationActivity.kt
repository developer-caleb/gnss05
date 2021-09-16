package kr.loplab.gnss05

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd
import com.huace.gnssserver.gnss.data.receiver.PositionInfo
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.activities.viewmodel.PositionInformationViewModel
import kr.loplab.gnss05.connection.ConnectManager
import kr.loplab.gnss05.databinding.ActivityPositionInformationBinding
import kr.loplab.gnss05.fragments.*
import kr.loplab.gnss05.receiver.ReceiverService
import kr.loplab.gnss05.receiver.entity.ReceiverAsw
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class PositionInformationActivity : ActivityBase<ActivityPositionInformationBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_position_information
    lateinit var viewModel1: PositionInformationViewModel
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
            when (action){
                EnumReceiverCmd.RECEIVER_ASW_SET_GNSS_POSDATA.name ->{
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
                                    Log.d(TAG, "시간 : ${p.time.year}-${p.time.month}-${p.time.day} ${p.time.hour}:${p.time.minute}:${p.time.second} ")
                                    viewModel1.setStringvalue(viewModel1.utcTime, "${p.time.year}-${p.time.month}-${p.time.day} ${p.time.hour}:${p.time.minute}:${p.time.second}")
                                    viewModel1.setStringvalue(viewModel1.time, SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA).format(Date(System.currentTimeMillis())))
                                    viewModel1.setStringvalue(viewModel1.x, p.satellitePosition.position.x.toString() )
                                    viewModel1.setStringvalue(viewModel1.y, p.satellitePosition.position.y.toString() )
                                    viewModel1.setStringvalue(viewModel1.z, p.satellitePosition.position.z.toString() )
                                    viewModel1.setStringvalue(viewModel1.horizontalError, p.satellitePrecision.hpre.toString() )
                                    viewModel1.setStringvalue(viewModel1.verticalError, p.satellitePrecision.vpre.toString() )
                                }
                            }
                            else -> {
                                Log.d(TAG, "onReceive: 4")
                            }
                        }
                    }
                }
                EnumReceiverCmd.RECEIVER_ASW_SET_GNSS_DOPSDATA.name -> {
                    Log.d(TAG, "onReceive: 4")

                }
            }

        }
    }
    override fun onResume() {
        super.onResume()
        when (ConnectManager.instance?.isConnected)
        {
            true ->   { Log.d(TAG, "onResume: connected")
            }
            false ->  Log.d(TAG, "onResume: unconnected")
        }
        //리시버
        val cmds: MutableList<EnumReceiverCmd> = ArrayList()
        cmds.add(EnumReceiverCmd.RECEIVER_ASW_SET_GNSS_POSDATA)
        cmds.add(EnumReceiverCmd.RECEIVER_ASW_SET_GNSS_DOPSDATA)
        registerReceiver(mReceiver, ReceiverService.createReceiverAswIntentFilter(cmds)
        )
    }
    
    override fun init() {
        viewModel1 = ViewModelProvider(this).get(PositionInformationViewModel::class.java)
        viewBinding.viewModel = viewModel1
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
        viewModel1.setStringvalue(viewModel1.latitude, "안녕?" )

    }


    fun selecttab(position : Int){
        tabposition = position;
        transaction = fragmentManager!!.beginTransaction();
        transaction!!.replace(R.id.frameLayout, fragments[position]!! ).commitAllowingStateLoss();
    }
}