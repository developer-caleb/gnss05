package kr.loplab.gnss05.type3.model

import kr.loplab.gnss05.type3.model.Type3PriceModel

/**
 * Created by xiaoyulaoshi on 2018/1/30.
 */
class Type3ProductModel {
    //현재 항목이 선택되었는지 여부
    var isPressed = false
    //상품명
    var productName: String = ""
    //제품 내부 가격표
    var mRightDataList: MutableList<Type3PriceModel> = mutableListOf()
}