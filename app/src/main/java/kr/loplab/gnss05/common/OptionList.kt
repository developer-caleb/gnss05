package kr.loplab.gnss05.common

class OptionList {
  companion object{
      val EQUIPMENT_MAKER_LIST = arrayListOf<String>(
      "GEO(GINTEC)", "South", "Kolida", "Ruide", "Sanding", "Stonex", "UniStrong",
      "Hemisphere", "GINTEC", "GEOMAX", "Hi-Target", "HuaXing"
      )
      val SEPERATOR_LIST = arrayListOf<String>(",", "@", "Space")


      //기준국
      val CONNECT_MODE_LIST = arrayListOf<String>("블루투스", "WIFI", "데모",)
      val START_MODE_LIST = arrayListOf<String>("싱글 포인트", "기준국 좌표 입력",)
      val DEPLACEMENT_MODE_LIST = arrayListOf<String>("RTCM2.3", "RTCM3","CMR","CMR+","DGPS", "RTCM3.2")
      val COLLECTION_INTERVAL_LIST = arrayListOf<String>("60s","30s","15s","10s","5s","2s","1HZ","2HZ","5HZ")
      val REFERENCE_COUNTRY_DATA_CONNECTION_TYPE_LIST= arrayListOf<String>("내장 라디오","외장 라디오")
      val NETWORK_MODE_List= arrayListOf<String>("GPRS","WIFI")
      val INNER_RADIO_CHANNEL_LIST= arrayListOf<String>("1","2","3","4","5", "6", "7","8")
      val INNER_RADIO_PROTOCOL_LIST= arrayListOf<String>("Satel","PCC_GMSK","TrimTalk 450S","South 9600","TrimMask III", "South 19200", "900M Hopping",)
      val INNER_RADIO_INTERVAL_LIST= arrayListOf<String>("12.5","25.0",)
      val INNER_RADIO_POWER_LIST= arrayListOf<String>("약","고",)
      val NETWORK_SYSTEM_List= arrayListOf<String>("자동","GSM", "CDMA1x")
      val CUT_ANGLE_LIST= arrayListOf<String>("1","2","3","5","10","15","20","25","30","35","40","45",)
      val PDOP_LIMIT_LIST= arrayListOf<String>("0.5","1.0","2.0","3.0","5.0","10.0","15.0","20.0","50.0","99.9",)
      val DELAY_LIST= arrayListOf<String>("1","2","3","5","10","15","20","25","30","60","120","180")
      val COMMUNICATION_SPEED_LIST= arrayListOf<String>("9600","19200","38400","57600","115200")

      //이동국
      val MOBILESTATION_DATA_CONNECTION_TYPE_LIST= arrayListOf<String>("없음","수신기 유심통신","내장 라디오","외장 라디오","단말기 유심통신", "L-band")
      val GGA_UPLOAD_INTERVAL_LIST= arrayListOf<String>("1","2","3","5","10","15","20","25","30","60","120","180")
      val MOUNTPOINT_LIST= arrayListOf<String>("ANHN-RTCM30","BHAO-RTCM30","BHAO-RTCM32","BOEN-BINEX","BOEN-RTCM23", "BOEN-RTCM31", "BONH-BINEX",
      "BONH-RTCM23", "BONH-RTCM31", "BONH-RTCM32")
      val MOUNTPOINT_SORT_LIST= arrayListOf<String>("기본","이름 순서","이름 역순","거리 순서")

      //정지측량
      val HEIGHT_CALC_METHOD= arrayListOf<String>("중심으로부터의 높이","측정 라인에서의 경사 높이","측정 라인에서 직립 높이","높이계로 부터의 경사 높이","장치 바닥에서 수직으로 세운 높이")

      //좌표계 매개변수
      val ELLIPSOID_NAME_LIST= arrayListOf<String>("GRS80","IAU76","Airy-1830","APL4.9","NWL9D","mod_airy","andrae","aust_SA","GRS67","bessel_nam","clark66","CPM")
      val CONVERSION_TYPE_LIST= arrayListOf<String>("ITRF2014->ITRF2008","ITRF2014->ITRF2005","ITRF2014->ITRF2000","ITRF2014->ITRF1997","ITRF2008->ITRF2005",
          "ITRF2008->ITRF2000","ITRF2008->ITRF1997","ITRF2008->ITRF1989","ITRF2005->ITRF2000","ITRF2000->ITRF1997","ITRF2014->ETRF2000","ITRF2008->ETRF2000")
      val SEVEN_PARAMETER_MODE= arrayListOf<String>("Bursa_Wolf", "Bursa_Wolf(With the origin)", "Strict Bursa-Wolf", "Helmert", "Molodensky")


  }
}