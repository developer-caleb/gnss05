package kr.loplab.gnss05.source

import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.huace.gnssserver.gnss.data.receiver.*
import kr.loplab.gnss05.R
import kr.loplab.gnss05.source.SourceListActivity
import kr.loplab.gnss05.receiver.sourcelist.GetSourceFromReceiver
import kr.loplab.gnss05.common.DialogUtils
import kr.loplab.gnss05.receiver.asw.GetSourceListEventArgs
import kr.loplab.gnss05.receiver.ReceiverService
import kr.loplab.gnss05.receiver.asw.GetModemDialStatusEventArgs
import kr.loplab.gnss05.receiver.asw.GetSourceTableEventArgs
import kr.loplab.gnss05.receiver.entity.ReceiverAsw
import java.text.SimpleDateFormat
import java.util.*

/**
 * Source list
 *
 * @author wangjun
 */
class SourceListActivity : AppCompatActivity() {
    var TAG = this.javaClass.simpleName
    private var mIp: String? = ""
    private var mPort = -1
    private var mLvSourceList: ListView? = null
    private var mDialog: Dialog? = null
    private val mDelayCancelRunnable = Runnable {
        if (mDialog != null && mDialog!!.isShowing) {
            cancel()
            Toast.makeText(
                this@SourceListActivity, "Failed to get the source list！",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sourcelist)
        initView()
        updateSourceList()
        initData()
    }

    private fun initData() {
        mIp = intent.getStringExtra(IP)
        mPort = intent.getIntExtra(PORT, -1)
        Log.d(TAG, "initData: ip: $mIp")
        Log.d(TAG, "initData: port: $mPort")
        // TODO Auto-generated method stub
    }

    private fun initView() {
        mLvSourceList = findViewById<View>(R.id.lvSourceList) as ListView
        mLvSourceList!!.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val it = Intent()
                val source = mLvSourceList!!.adapter.getItem(position) as String
                it.putExtra(SOURCE_LIST, source)
                setResult(RESULT_OK, it)
                finish()
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.source_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_obtain) {
            obtainSourceList()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    //리시버로 받고나서 업데이트
    private fun updateSourceList() {
        val data = GetSourceFromReceiver.getInstance().sourceList
        val arrayAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_expandable_list_item_1, data
        )
        mLvSourceList!!.adapter = arrayAdapter
    }

    private fun obtainSourceList() {
        //소스리스트 받기 dialog 시작
        if (TextUtils.isEmpty(mIp) || mPort <= 0) {
            Toast.makeText(this@SourceListActivity, "Failed To Get the IP and Address！", Toast.LENGTH_SHORT).show()
            return
        }
        mDialog = DialogUtils.showProgressDialog(this, R.string.msg_initing)
        val delaytime = 15 //원래 15
        //delaytime 이후에 cancel 시키기
        mLvSourceList!!.postDelayed(mDelayCancelRunnable, (delaytime * 1000).toLong())
        GetSourceFromReceiver.getInstance().loadSourceList(mIp, mPort)
    }

    private fun cancel() {
        if (mDialog != null) {
            mDialog!!.dismiss()
            mDialog = null
        }
    }

    public override fun onResume() {
        super.onResume()


        val cmds: MutableList<EnumReceiverCmd> = ArrayList()
        cmds.add(EnumReceiverCmd.RECEIVER_ASW_GET_MODEM_DIAL_STATUS)
        cmds.add(EnumReceiverCmd.RECEIVER_ASW_GET_SOURCE_TABLE)
        //cmds.add(EnumReceiverCmd.RECEIVER_ASW_SET_GNSS_POSDATA)
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, ReceiverService.createReceiverAswIntentFilter(cmds))
        Log.d(TAG, "onResume: 리시버 등록")


    }

    public override fun onStop() {
        super.onStop()
        unregisterReceiver(mReceiver)
        Log.d(TAG, "onStop: 리시버 등록 해제")
    }

    private val mReceiver: MyReceiver = MyReceiver()

    //리시버로 성공한 내역 받기,
    inner class MyReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Log.d(TAG, "onReceive: 받음 ${intent.action}")
            val action = intent.action
            when (action)
             {
                EnumReceiverCmd.RECEIVER_ASW_GET_SOURCE_TABLE.name -> {
                Log.d(TAG, "onReceive: 받음")
                val serializable = intent.getSerializableExtra(ReceiverService.RECEIVER_DATA)
                if (serializable is GetSourceTableEventArgs) {
                    runOnUiThread {
                        mLvSourceList!!.removeCallbacks(mDelayCancelRunnable)
                        cancel()
                        Toast.makeText(this@SourceListActivity, "Source Table Get Success！",
                            Toast.LENGTH_SHORT).show()
                        updateSourceList()
                    }
                }
            }


                EnumReceiverCmd.RECEIVER_ASW_GET_MODEM_DIAL_STATUS.name ->{
                    Log.d(TAG, "onReceive: 2")
                    val asw: ReceiverAsw? = ReceiverService.getBroadcastData(intent)
                    if (asw== null){ Log.d(TAG, "onReceive: null"); return}
                    runOnUiThread {
                        Log.d(TAG, "onReceive: 3")
                        if ( asw.receiverCmdType ==   EnumReceiverCmd.RECEIVER_ASW_GET_MODEM_DIAL_STATUS && (asw.getParcelable() is ModemDialStatus) ) {
                            val p = asw.getParcelable() as ModemDialStatus
                            if (p != null ) {
                                Log.d(TAG, "onReceive:  count" + p.count)
                                Log.d(TAG, "onReceive:  EnumModemDialStatus" + p.status)
                            }
                        }
                    }
                }

            }


        }
    }

    companion object {
        @JvmField
		var IP = "Ip"
        @JvmField
		var PORT = "Port"

        /**
         * source list
         */
        const val SOURCE_LIST = "SOURCE_LIST"
    }
}