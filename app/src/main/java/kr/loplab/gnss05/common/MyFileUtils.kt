package kr.loplab.gnss05.common

import android.os.Environment
import kr.loplab.gnss05.GlobalApplication
import kr.loplab.gnss05.common.MyFileUtils
import kr.loplab.gnss05.connection.ConnectManager
import java.io.File

class MyFileUtils private constructor() {



    companion object {
        @JvmStatic
        fun sdCardPath1(): String {
            return (GlobalApplication.getInstance().applicationContext.filesDir.absolutePath
                    + File.separator)
        }

        private var sInstance: MyFileUtils? = null
        @JvmStatic
        val instance: MyFileUtils?
            get() {
                if (sInstance == null) {
                    synchronized(MyFileUtils::class.java) {
                        if (sInstance == null) {
                            sInstance = MyFileUtils()
                        }
                    }
                }
                return sInstance
            }
    }
}