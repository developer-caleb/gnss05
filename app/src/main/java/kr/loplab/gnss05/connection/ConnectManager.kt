package kr.loplab.gnss05.connection

import android.content.Context
import android.util.Log
import kr.loplab.gnss05.connection.ConnectionTypes
import kr.loplab.gnss05.connection.IGnssConnection
import kr.loplab.gnss05.connection.ConnectionStatus
import kr.loplab.gnss05.connection.GnssConnectionFactory
import kr.loplab.gnss05.connection.GnssConnectionWay
import kr.loplab.gnss05.connection.IConnectionCallback
import kr.loplab.gnss05.receiver.UseDemo
import kr.loplab.gnss05.GlobalApplication
import kr.loplab.gnss05.MyDialog
import kr.loplab.gnss05.receiver.entity.Cmd
import kr.loplab.gnss05.connection.ConnectManager
import java.lang.Exception

class ConnectManager private constructor() {
    private val TAG = this.javaClass.simpleName
    private val mconnectionType = ConnectionTypes.DEMO
    private var mGnssConnection: IGnssConnection? = null
    private lateinit var connectionStateChangeListener:ConnectStateChangeListener
    var connectionStatus = ConnectionStatus.DISCONNECT
        private set //자바 -> 코틀린 옮기는 과정에서 생김

    /**
     * begin connecting
     */
    val isConnected: Boolean
        get() = connectionStatus == ConnectionStatus.CONNECTED

    fun connect(context: Context?): Boolean {
        return try {
            Log.d(TAG, "connect: 연결 성공")

            if (mGnssConnection != null) {
                mGnssConnection!!.disConnect()
                mGnssConnection = null
            }
            mGnssConnection = GnssConnectionFactory.makeConnection(GnssConnectionWay.BLUETOOTH)
            if (mGnssConnection == null) {
                return false
            }
            connectionStateChangeListener.onConnectStateChange(ConnectionStatus.CONNECTTNG)
            connectionStatus = ConnectionStatus.CONNECTTNG
            (mGnssConnection != null
                    && mGnssConnection!!.connect(context, mConnectionCallback))
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun disConnect() {
        if (mGnssConnection != null) {
            mGnssConnection!!.disConnect()
            mGnssConnection = null
        }
        connectionStateChangeListener.onConnectStateChange(ConnectionStatus.DISCONNECT)
        connectionStatus = ConnectionStatus.DISCONNECT
    }

    private val mConnectionCallback: IConnectionCallback = object : IConnectionCallback {
        override fun backConnectionState(success: Boolean) {
            connectionStatus =
                if (success) ConnectionStatus.CONNECTED else ConnectionStatus.CONNECT_FAILD
            if (success) connectionStateChangeListener.onConnectStateChange(ConnectionStatus.CONNECTED) else connectionStateChangeListener.onConnectStateChange(ConnectionStatus.CONNECT_FAILD)
            if (success) {
                // wifi connect success
                UseDemo.runReceiver(GlobalApplication.getInstance())
            } else {
                // wifi break
                UseDemo.stopReceiver()
            }
        }

        override fun connectionLost() {
            connectionStatus = ConnectionStatus.CONNECT_FAILD
            connectionStateChangeListener.onConnectStateChange(ConnectionStatus.CONNECT_FAILD)
            // 와이파이 연결 끊김
            UseDemo.stopReceiver()
            Log.e(TAG, "connectionLost: nn")
        }

        override fun beDisConnected() {
            connectionStatus = ConnectionStatus.DISCONNECT
            connectionStateChangeListener.onConnectStateChange(ConnectionStatus.DISCONNECT)

            // wifi disconnected
            UseDemo.stopReceiver()
            Log.e(TAG, "beDisConnected: stop it")
        }

        override fun receiver(data: ByteArray) {
            UseDemo.receiveData(data)
        }
    }

    /**
     * Write data to the receiver, don’t call it directly, only call CmdManager
     *
     * @param data
     */
    fun write(data: ByteArray?) {
        if (mGnssConnection != null) {
            mGnssConnection!!.writeDataToDevice(Cmd(data))
        }
    }

    companion object {
        private var sInstance: ConnectManager? = null
        @JvmStatic
		val instance: ConnectManager?
            get() {
                if (sInstance == null) {
                    synchronized(ConnectManager::class.java) {
                        if (sInstance == null) {
                            sInstance = ConnectManager()
                        }
                    }
                }
                return sInstance
            }
    }

    interface ConnectStateChangeListener {
        fun onConnectStateChange(connectionStatus : ConnectionStatus)
    }

    fun setOnConnectStateChangeListener(listener: (ConnectionStatus) -> Unit) {
        this.connectionStateChangeListener = object: ConnectManager.ConnectStateChangeListener {
            override fun onConnectStateChange(connectionStatus: ConnectionStatus) {
                listener(connectionStatus)
            }
        }
    }

}