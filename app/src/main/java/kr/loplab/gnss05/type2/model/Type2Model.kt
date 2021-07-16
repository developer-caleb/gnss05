package kr.loplab.gnss05.type2.model

/**
 * Created by xiaoyulaoshi on 2018/1/30.
 */
class Type2Model {
    //현재 항목이 선택되었는지 여부
    var isPressed = false
    //상품명
    var productName: String? = null
    //제품 가격 데이터 수집
    var mPriceList: MutableList<String>? = null
}