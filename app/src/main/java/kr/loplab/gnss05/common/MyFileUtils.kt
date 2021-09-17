package kr.loplab.gnss05.common

import kr.loplab.gnss05.common.MyFileUtils
import kr.loplab.gnss05.connection.ConnectManager

class MyFileUtils private constructor() {



    companion object {
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