package kr.loplab.gnss05

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class HamburgerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hamburger)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.hold, R.anim.slide_left_exit)
    }
}