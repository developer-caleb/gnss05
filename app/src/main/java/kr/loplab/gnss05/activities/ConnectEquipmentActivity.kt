package kr.loplab.gnss05.activities

import android.Manifest
import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.MyDialog
import kr.loplab.gnss05.R
import kr.loplab.gnss05.activities.bluetooth.ConnectController
import kr.loplab.gnss05.activities.viewmodel.ConnectEquipmentViewModel
import kr.loplab.gnss05.adapter.BluetoothEquipmentRecyclerViewAdapter
import kr.loplab.gnss05.adapter.WifiEquipmentRecyclerViewAdapter
import kr.loplab.gnss05.common.Define.CONNECT_MODE
import kr.loplab.gnss05.common.Define.EQUIPMENT_MAKER
import kr.loplab.gnss05.common.OptionList.Companion.CONNECT_MODE_LIST
import kr.loplab.gnss05.common.OptionList.Companion.EQUIPMENT_MAKER_LIST
import kr.loplab.gnss05.common.PrefUtil
import kr.loplab.gnss05.connection.ConnectManager
import kr.loplab.gnss05.connection.bluetooth.MyBluetoothManager
import kr.loplab.gnss05.databinding.ActivityConnectEquipmentBinding

class ConnectEquipmentActivity : ActivityBase<ActivityConnectEquipmentBinding>(), BluetoothEquipmentRecyclerViewAdapter.RecyclerItemClickListener,
WifiEquipmentRecyclerViewAdapter.RecyclerItemClickListener{
    override val layoutResourceId: Int
        get() = R.layout.activity_connect_equipment
    lateinit var viewModel1: ConnectEquipmentViewModel
    lateinit var wifiRecyclerViewAdapter : WifiEquipmentRecyclerViewAdapter
    lateinit var bluetoothRecyclerViewAdapter : BluetoothEquipmentRecyclerViewAdapter
    var bluetoothDefaultAdapter = BluetoothAdapter.getDefaultAdapter() //블루투스 검색
    private val REQUEST_PERMISSIONS= 2 //블루투스 검색
    private val REQUEST_ALL_PERMISSIONS= 2 //블루투스 검색
    private val SCAN_PERIOD = 15000 //블루투스 검색
    private val handler = Handler()  //블루투스 검색
    var btDevicesArr = arrayListOf<BluetoothDevice>()
    var wifiDevicesArr = arrayListOf<ScanResult>()
    var wifiManager: WifiManager? = null
    var intentFilter = IntentFilter()


    private val mController: ConnectController = ConnectController()
    private var mCount = 0

    private var mConnectDialog: ProgressDialog? = null
    private val PERMISSIONS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION) //블루투스 검색 -> 검색하려면 fine location permission 받아야한다고 함. 이유는 모름


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun init() {
        viewModel1 = ViewModelProvider(this).get(ConnectEquipmentViewModel::class.java)
        viewBinding.connectionequpmentviewmodel = viewModel1

        //Wifi Scan 관련
        wifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        applicationContext.registerReceiver(wifiScanReceiver, intentFilter)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initListener() {
    viewBinding.header01.setOnBackButtonClickListener{onBackPressed()}
    viewBinding.makerSelectBt.setOnClickListener {
            val dlg = MyDialog(this)
            dlg.firstLayoutUse = false
            dlg.list = EQUIPMENT_MAKER_LIST
            dlg.selectedposition= PrefUtil.getInt2(applicationContext, EQUIPMENT_MAKER)
            dlg.start("메인의 내용을 변경할까요?")
            dlg.setOnListClickedListener { view, i ->
            Log.d(TAG, "initListener: $i")
            PrefUtil.setInt(applicationContext, EQUIPMENT_MAKER, i)
            viewBinding.tvEquipmentMaker.text = EQUIPMENT_MAKER_LIST[PrefUtil.getInt2(applicationContext, EQUIPMENT_MAKER)]
            dlg.dismiss()
        }
        dlg.setHeader("장비제조사")
    }

        viewBinding.connectModeBt.setOnClickListener {
            val dlg = MyDialog(this)
            //dlg.title_dialog
            dlg.firstLayoutUse = false
            dlg.list = CONNECT_MODE_LIST
            dlg.selectedposition= PrefUtil.getInt2(applicationContext, CONNECT_MODE)
            dlg.start("");
            dlg.setOnListClickedListener { view, i ->
                Log.d(TAG, "initListener: $i")
                viewModel1.setConnectMode(i)
                PrefUtil.setInt(applicationContext, CONNECT_MODE, i)
                viewBinding.tvConnectMode.text = CONNECT_MODE_LIST[PrefUtil.getInt2(applicationContext, CONNECT_MODE)]
                dlg.refresh()
                dlg.dismiss()
            }
            dlg.setHeader("연결모드")
        }

        viewBinding.btScan.setOnClickListener {
            Log.d(TAG, "initListener: ")
            when(PrefUtil.getInt2(applicationContext, CONNECT_MODE)) {
                0->{ intent = Intent(Settings.ACTION_BLUETOOTH_SETTINGS)
                    startActivity(intent) }
                1->{
                    clickWifiScan()
                }
                else ->{

                }
            }
        }
        viewBinding.btConnect.setOnClickListener {
            when(PrefUtil.getInt2(applicationContext, CONNECT_MODE)){
            0 -> {if(bluetoothRecyclerViewAdapter.selectednum == -1){
                showToast("블루투스 장치를 선택해주세요"); return@setOnClickListener
            }else{
                bluetoothConnect()
            }}
                1->  {if(wifiRecyclerViewAdapter.selectednum == -1){
                    showToast("와이파이 장치를 선택해주세요"); return@setOnClickListener
                }else{
                    wifiConnect()
                }}
            }
        }

        viewBinding.header01.setOnOptionButtonClickListener{
            Log.d(TAG, "initListener: ${ConnectManager.getInstance().connectionStatus.toString()}") }
    }
    fun wifiConnect(){
        MyBluetoothManager.getInstance().setBlueName(
            wifiDevicesArr[wifiRecyclerViewAdapter.selectednum].SSID  )
        showWifiConnectDialog()
        mController.connect(this)
    }
    fun bluetoothConnect(){
        MyBluetoothManager.getInstance().setBlueName(
            btDevicesArr[bluetoothRecyclerViewAdapter.selectednum].name  )
        showBtConnectDialog()
        mController.connect(this)
    }
    private fun showBtConnectDialog() {
        mConnectDialog = ProgressDialog.show(this, "정보", "연결 중 입니다...")
        checkConnectStatus(viewBinding.mTvBtConnectStatus)
    }
    private fun showWifiConnectDialog() {
        mConnectDialog = ProgressDialog.show(this, "정보", "연결 중 입니다...")
        checkConnectStatus(viewBinding.mTvWifiConnectStatus)
    }
    override fun onResume() {
        super.onResume()
        setBluetoothList()
    }
    override fun initDatabinding() {
        viewModel1.setConnectMode(PrefUtil.getInt2(applicationContext, CONNECT_MODE))
        viewBinding.tvEquipmentMaker.text = if(PrefUtil.getInt2(applicationContext, EQUIPMENT_MAKER)==-1){"제조사명"}
        else{EQUIPMENT_MAKER_LIST[PrefUtil.getInt2(applicationContext, EQUIPMENT_MAKER)]}
        viewBinding.tvConnectMode.text = CONNECT_MODE_LIST[PrefUtil.getInt2(applicationContext, CONNECT_MODE)]
        setBluetoothList();
        setWifiList();
    }
    fun setBluetoothList(){

        val pairedBTDevices = BluetoothAdapter.getDefaultAdapter().bondedDevices
        btDevicesArr.clear()
        if (pairedBTDevices.size>0){
        pairedBTDevices.forEach { divices ->
            run {
                btDevicesArr.add(divices)
                Log.d(TAG, "setBluetoothList: ")
            }
        }}
        bluetoothRecyclerViewAdapter = BluetoothEquipmentRecyclerViewAdapter(this, btDevicesArr!!)
        viewBinding.recyclerviewBluetoothEquipment.adapter = bluetoothRecyclerViewAdapter
        bluetoothRecyclerViewAdapter.setClickListener(this)
    }
    fun setWifiList(){
        val results = wifiManager!!.scanResults
        wifiDevicesArr.clear()
        if (results.size>0){
            results.forEach { divices ->
                run {
                    wifiDevicesArr.add(divices)
                    Log.d(TAG, "setBluetoothList: ")
                }
            }}
        wifiRecyclerViewAdapter = WifiEquipmentRecyclerViewAdapter(this, wifiDevicesArr!!)
        viewBinding.recyclerviewWifiEquipment.adapter = wifiRecyclerViewAdapter
        wifiRecyclerViewAdapter.setClickListener(this)
    }
    private fun dismissDialog() {
        if (dialogIsShow()) {
            mConnectDialog!!.dismiss()
        }
    }

    private fun dialogIsShow(): Boolean {
        return mConnectDialog != null && mConnectDialog!!.isShowing
    }
  override fun onBluetoothItemClick(view: View?, position: Int) {
        Log.d(TAG, "onBluetoothItemClick:  $position")
        bluetoothRecyclerViewAdapter.selectednum = position
        bluetoothRecyclerViewAdapter.notifyDataSetChanged();
    }
    private fun updateStatus(textview : TextView) {
        textview.setText(ConnectManager.getInstance().getConnectionStatus().name
        )
    }
    private fun checkConnectStatus(textview : TextView) {
        mCount = 0
        textview.postDelayed(object : Runnable {
            override fun run() {
                if (mController.isConnect) {
                    dismissDialog()
                    showToast("연결에 성공하였습니다!")
                    updateStatus(textview)
                    return
                }
                mCount++
                if (mCount < 20) {
                    textview.postDelayed(this, 1000)
                    return
                }
              showToast("연결 실패！")
                updateStatus(textview)
                dismissDialog()
                mController.disConnect()
            }
        }, 1000)
    }


    var wifiScanReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(
            c: Context,
            intent: Intent
        ) {
            viewModel1.setBoolvalue(viewModel1.scanning , false)
            // wifiManager.startScan(); 시  발동되는 메소드 ( 예제에서는 버튼을 누르면 startScan()을 했음. )
            val success =
                intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false) //스캔 성공 여부 값 반환
            if (success) {
                setWifiList()  //Scan successed
            } else {
                scanFailure()
            }
        } // onReceive()..
    }

    fun clickWifiScan() {
        val success = wifiManager!!.startScan()

        if (!success) showToast("Wifi Scan에 실패하였습니다.")
    }

    private fun scanFailure() {    // Wifi검색 실패
        showToast("Wifi Scan에 실패하였습니다.")
    }

    override fun onWifiItemClick(view: View?, position: Int) {
        Log.d(TAG, "onWifiItemClick:  $position")
        wifiRecyclerViewAdapter.selectednum = position
        wifiRecyclerViewAdapter.notifyDataSetChanged();
    }
    //**와이파이 스캔,
    /*
   private fun hasPermissions(context: Context?, permissions: Array<String>): Boolean {
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
           for (permission in permissions) {
               if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                   return false
               }
           }
       }
       return true
   }

   // Permission 확인
   @RequiresApi(Build.VERSION_CODES.M)
   override fun onRequestPermissionsResult(
       requestCode: Int,
       permissions: Array<String?>,
       grantResults: IntArray
   ) {
       super.onRequestPermissionsResult(requestCode, permissions, grantResults)
       when (requestCode) {
           REQUEST_PERMISSIONS -> {
               // If request is cancelled, the result arrays are empty.
               if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                 showToast("Permissions granted!")
               } else {
                   requestPermissions(permissions, REQUEST_PERMISSIONS)
                showToast("Permissions must be granted")
               }
           }
       }
   }


   private val mLeScanCallback = @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
   object: ScanCallback() {
       override fun onScanFailed(errorCode: Int) {
           super.onScanFailed(errorCode)
           Log.d("scanCallback", "BLE Scan Failed : " + errorCode)
       }
       override fun onBatchScanResults(results: MutableList<android.bluetooth.le.ScanResult > ?) {
           super.onBatchScanResults(results)
           results?.let {
               // results is not null
               for(result in it) {
                   if(!devicesArr.contains(result.device) && result.device.name!=null) devicesArr.add(result.device)
               }
           }
       }
       override fun onScanResult(callbackType: Int, result: android.bluetooth.le.ScanResult?) {
           super.onScanResult(callbackType, result)
           result?.let {
               // result is not null
               if(!devicesArr.contains(it.device) && it.device.name!=null) devicesArr.add(it.device)
               bluetoothRecyclerViewAdapter.notifyDataSetChanged()
           }
       }
   }


   @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
   private fun scanDevice(state:Boolean) { if(state) {
       if(viewModel1.scanning.value!!){ // restart
           bluetoothDefaultAdapter?.bluetoothLeScanner?.stopScan(mLeScanCallback)
           viewModel1.setBoolvalue(viewModel1.scanning, true)
           scanDevice(true)
       }else {
           handler.postDelayed({
               viewModel1.setBoolvalue(viewModel1.scanning, false)
               bluetoothDefaultAdapter?.bluetoothLeScanner?.stopScan(mLeScanCallback)
           }, SCAN_PERIOD.toLong())
           viewModel1.setBoolvalue(viewModel1.scanning, true)
           devicesArr.clear()
           bluetoothDefaultAdapter?.bluetoothLeScanner?.startScan(mLeScanCallback)
       }
   }
   else {
       viewModel1.setBoolvalue(viewModel1.scanning, false)
       bluetoothDefaultAdapter?.bluetoothLeScanner?.stopScan(mLeScanCallback)
   }
   }*/
}