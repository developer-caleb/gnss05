package kr.loplab.gnss05.common

class OptionList {
  companion object{
      val EQUIPMENT_MAKER_LIST = arrayListOf<String>(
      "GEO(GINTEC)", "South", "Kolida", "Ruide", "Sanding", "Stonex", "UniStrong",
      "Hemisphere", "GINTEC", "GEOMAX", "Hi-Target", "HuaXing"
      )
      val CONNECT_MODE_LIST = arrayListOf<String>(
          "블루투스", "WIFI", "데모",
      )
      val START_MODE_LIST = arrayListOf<String>(
          "싱글 포인트", "기준국 좌표 입력",
      )
      val DEPLACEMENT_MODE_LIST = arrayListOf<String>(
          "RTCM2.3", "RTCM3","CMR","CMR+","DGPS", "RTCM3.2"
      )
      val COLLECTION_INTERVAL_LIST = arrayListOf<String>(
          "60s","30s","15s","10s","5s","2s","1HZ","2HZ","5HZ"
      )
      val DATA_CONNECTION_TYPE_List= arrayListOf<String>(
          "수신기 유심통신","내장 라디오","외장 라디오","듀얼"
      )
      val NETWORK_MODE_List= arrayListOf<String>(
          "GPRS","WIFI"
      )
      val INNER_RADIO_CHANNEL_LIST= arrayListOf<String>(
          "1","2","3","4","5", "6", "7","8"
      )
      val INNER_RADIO_PROTOCOL_LIST= arrayListOf<String>(
          "Satel","PCC_GMSK","TrimTalk 450S","South 9600","TrimMask III", "South 19200", "900M Hopping",
      )
      val NETWORK_SYSTEM_List= arrayListOf<String>(
          "자동","GSM", "CDMA1x"
      )
      val CUT_ANGLE_List= arrayListOf<String>(
          "1","2","3","5","10","15","20","25","30","35","40","45",
      )
      val PDOP_LIMIT_LIST= arrayListOf<String>(
          "0.5","1.0","2.0","3.0","5.0","10.0","15.0","20.0","50.0","99.9",
      )
      val DELAY_LIST= arrayListOf<String>(
          "1","2","3","5","10","15","20","25","30","60","120","180"
      )
  }
}