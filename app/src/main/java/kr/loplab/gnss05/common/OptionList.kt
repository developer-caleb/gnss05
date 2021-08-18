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
  }
}