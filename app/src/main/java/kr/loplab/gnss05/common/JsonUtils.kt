package kr.loplab.gnss05.common

import org.json.JSONArray

class JsonUtils {
    companion object{
        fun jsonarrayToList(jsonarray : String , seperatornum : Int): String{
            var jArray = JSONArray(jsonarray);
            var formatDescriptionFormatList = ArrayList<String>()
            if (jArray != null) {
                for (i in 0 until jArray.length()) {
                    var jArray2 = JSONArray(jArray[i].toString())
                    println("JARRAY2 : ${jArray2.toString()}")
                    formatDescriptionFormatList.add(jArray2[0] as String);
                }
            }

            var formatdescription = "";
            formatDescriptionFormatList.forEachIndexed { index, smalldata -> formatdescription += "[${smalldata}]${OptionList.SEPERATOR_LIST_OUTPUT[seperatornum]} " }
            return formatdescription
        }


    }
}