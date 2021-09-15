package kr.loplab.gnss05.common

class OptionList {
  companion object{
      val EQUIPMENT_MAKER_LIST = arrayListOf<String>(
      "GEO(GINTEC)", "South", "Kolida", "Ruide", "Sanding", "Stonex", "UniStrong",
      "Hemisphere", "GINTEC", "GEOMAX", "Hi-Target", "HuaXing"
      )
      val SEPERATOR_LIST = arrayListOf<String>(",", "@", "Space")
      val SEPERATOR_LIST_OUTPUT = arrayListOf<String>(",", "@", " ")


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
      "BONH-RTCM23", "BONH-RTCM31", "BONH-RTCM32", "SB-AutoSelection-CMR", "SB-AutoSelection-CMRplus", "SB-AutoSelection-CMRx", "SB-AutoSelection-RTCM23",
          "SB-AutoSelection-RTCM31", "SB-AutoSelection-RTCM32", "VRS-CMR", "VRS-CMRplus", "VRS-CMRx","VRS-RTCM23","VRS-RTCM31", "VRS-RTCM31(2)"
          )
      val MOUNTPOINT_SORT_LIST= arrayListOf<String>("기본","이름 순서","이름 역순","거리 순서")

      //정지측량
      val HEIGHT_CALC_METHOD= arrayListOf<String>("중심으로부터의 높이","측정 라인에서의 경사 높이","측정 라인에서 직립 높이","높이계로 부터의 경사 높이","장치 바닥에서 수직으로 세운 높이")

      //좌표계 매개변수
      val ELLIPSOID_NAME_LIST= arrayListOf<String>("GRS80","IAU76","Airy-1830","APL4.9","NWL9D","mod_airy","andrae","aust_SA","GRS67","bessel_nam","clark66","CPM")
      val CONVERSION_TYPE_LIST= arrayListOf<String>("ITRF2014->ITRF2008","ITRF2014->ITRF2005","ITRF2014->ITRF2000","ITRF2014->ITRF1997","ITRF2008->ITRF2005",
          "ITRF2008->ITRF2000","ITRF2008->ITRF1997","ITRF2008->ITRF1989","ITRF2005->ITRF2000","ITRF2000->ITRF1997","ITRF2014->ETRF2000","ITRF2008->ETRF2000")
      val SEVEN_PARAMETER_MODE= arrayListOf<String>("Bursa_Wolf", "Bursa_Wolf(With the origin)", "Strict Bursa-Wolf", "Helmert", "Molodensky")

      //내보내기
      val DEGREE_FORM_TYPE = arrayListOf<String>("dd.mmsss", "dd:mm:ss.ssss", "ddºmm'ss.ssss\"", "dd (Decimal)", "Radian")
      val DEGREE_FILE_FORM1 = arrayListOf<String>("측량 점 데이터 형식(.csv)", "TPS 데이터형식(.csv)", "DOL 파일 형식(.csv)",
          "측점보고서(Brief)(.xls)", "측점보고서(Complete)(.xls)", "Gnss measuremnet report (.xls)", "전체 제어점 보고서 (.xls)")
      val DEGREE_FILE_FORM2 = arrayListOf<String>("단면 측정 데이터(.csv)")

      //UserFormatMake
      var optionitemlist  = arrayOf("이름", "코드", "위도", "경도", "고도", "X", "Y", "Z(레벨)", "X(공간)", "Y(공간)",  //0~9
          "Z(공간)", "도로명", "측설점", "단면스테이션", "중간 말뚝거리", "수직거리", "스테이션", "옵셋", "레벨편차", "시작거리",//19
          "타워명", "타워번호",  "속성유형", "현재날짜", "현재시간", "UTC날짜", "UTC시간", "위성연산", "위성추적중", "수평오차",//29
          "수직오차", "Σn", "σe", "PDOP", "HDOP", "VDOP", "저장모드", "솔루션 정보", "Detailed Solution Status", "지연",//39
          "컷 오프 각도", "반복횟수", "안테나 높이", "속도", "방향", "기준국 ID", "기준국 위도", "기준국 경도", "기준국 고도", "기준국과 거리",  //49
          "원점 위도", "원점 경도", "원점 고도", "Station Correction x", "Station Correction y", "Station Correction h", "경사 보정", "Pitch", "Roll", "Yaw",  //59
          "경사 각도", "투영 각도",  "X보정", "Y보정", "Z보정", "공분산 Cxx", "공분산 Cxy", "공분산 Cxz", "공분산 Cyx", "공분산 Cyy",  //69
          "공분산 Cyz","공분산 Czx", "공분산 Czy", "공분산 Czz", "SD", "HD", "VD", "HA", "VA", "PPM",   //79
          "참조각도", "알려진 방위각", "스테이션 좌표 북쪽", "스테이션 좌표 동쪽", "스테이션 좌표 높이", "반사경", "HI", "프리즘상수", "HT"  ) //88
  }
}