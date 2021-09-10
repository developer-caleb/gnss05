package kr.loplab.gnss05.model

import androidx.appcompat.app.AppCompatActivity
import kr.loplab.gnss05.HamburgerActivity

data class MainIcons(
    var icon: Int = 0,
    var decription: String = "",
    var activityname : Class<*> = HamburgerActivity::class.java,
    var requestcode : Int = 0,
) {
    fun changeIcon(icon :Int){
        this.icon = icon
    }
}