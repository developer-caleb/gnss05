package kr.loplab.gnss05

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class DefaultHeader : FrameLayout {
    var tvTitle: TextView? = null
    var tvOption: TextView? = null
    var ivBack //, ivOption;
            : ImageView? = null
    var loHeader: ConstraintLayout? = null
    var btnClose: ConstraintLayout? = null
    var btnOption: ConstraintLayout? = null
    var backButtonEnabled = false
    var optionButtonEnabled = false

    constructor(context: Context?) : super(context!!) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
        initView()
        getAttrs(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!, attrs, defStyleAttr
    ) {
        initView()
        getAttrs(attrs, defStyleAttr)
    }

    private fun initView() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.layout_default_header, this, false)
        addView(view)
        loHeader = findViewById(R.id.loHeader)

        //왼쪽 위(뒤로가기)
        btnClose = findViewById(R.id.btnClose)
        ivBack = findViewById(R.id.btn_back)
        tvTitle = findViewById(R.id.tvTitle)

        //오른쪽 위(옵션)
        btnOption = findViewById(R.id.btnOption)
        tvOption = findViewById(R.id.tvOption)
        //        ivOption = findViewById(R.id.ivOption);
    }

    private fun getAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DefaultHeader)
        setTypedArray(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet?, defStyle: Int) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.DefaultHeader, defStyle, 0)
        setTypedArray(typedArray)
    }

    private fun setTypedArray(typedArray: TypedArray) {
        val title = typedArray.getString(R.styleable.DefaultHeader_title)
        tvTitle!!.text = title
        val size = typedArray.getDimensionPixelSize(
            R.styleable.DefaultHeader_titleSize,
            resources.getDimensionPixelSize(R.dimen.fontHeaderTitle)
        ).toFloat()
        tvTitle!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
        val style = typedArray.getInt(R.styleable.DefaultHeader_titleStyle, 0)
        tvTitle!!.setTypeface(null, style)
        val backResId = typedArray.getResourceId(
            R.styleable.DefaultHeader_backButtonImageSource,
            R.drawable.icon_basic_header_btn_back
        )
        ivBack!!.setImageResource(backResId)
        val backgroundcolor = typedArray.getColor(
            R.styleable.DefaultHeader_backgroundColor,
            resources.getColor(R.color.headerblack)
        )
        loHeader!!.setBackgroundColor(backgroundcolor)
        val backbuttoncolor = typedArray.getColor(
            R.styleable.DefaultHeader_backButtonColor,
            resources.getColor(R.color.white)
        )
        ivBack!!.setColorFilter(backbuttoncolor)
        val titletextcolor = typedArray.getColor(
            R.styleable.DefaultHeader_titleTextColor,
            resources.getColor(R.color.white)
        )
        tvTitle!!.setTextColor(titletextcolor)
        val setOutline = typedArray.getBoolean(R.styleable.DefaultHeader_optionButtonOutline, false)
        if (setOutline) tvOption!!.setBackgroundResource(R.drawable.shape_round_square_outline_primary) else {
            val optionResId =
                typedArray.getResourceId(R.styleable.DefaultHeader_optionButtonImageSource, 0)
            tvOption!!.setBackgroundResource(optionResId)
        }
        val text = typedArray.getString(R.styleable.DefaultHeader_optionButtonText)
        tvOption!!.text = text
        backButtonEnabled = typedArray.getBoolean(R.styleable.DefaultHeader_backButtonEnabled, true)
        if (!backButtonEnabled) loHeader!!.removeView(btnClose)
        optionButtonEnabled =
            typedArray.getBoolean(R.styleable.DefaultHeader_optionButtonEnabled, false)
        if (!optionButtonEnabled) loHeader!!.removeView(btnOption)
        typedArray.recycle()
    }

    fun setTitle(text: String?) {
        tvTitle!!.text = text
    }



    fun setOptionButtonOutline(value: Boolean) {
        if (value) {
            tvOption!!.setBackgroundResource(R.drawable.shape_round_square_outline_primary)
        }
    }

    fun setOptionButtonEnabled3(value: Boolean) {
        tvOption!!.visibility = if (value) VISIBLE else GONE
        btnOption!!.visibility = if (value) VISIBLE else GONE
    }

    var optionButtonText: String?
        get() = tvOption!!.text.toString()
        set(text) {
            tvOption!!.text = text
        }

    fun setOptionButtonImageResource(id: Int) {
        tvOption!!.setBackgroundResource(id)
    }

    fun setOnBackButtonClickListener(listener: OnClickListener?) {
        if (backButtonEnabled) btnClose!!.setOnClickListener(listener)
    }

    fun setOnOptionButtonClickListener(listener: OnClickListener?) {
        if (optionButtonEnabled) btnOption!!.setOnClickListener(listener)
    }
}