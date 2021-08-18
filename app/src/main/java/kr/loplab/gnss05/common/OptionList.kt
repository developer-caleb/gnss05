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
  }
}