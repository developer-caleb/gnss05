package kr.loplab.gnss05.button;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import kr.loplab.gnss05.R;


public class NormalButton extends FrameLayout {
    TextView tvTitle, tvOption;
    ImageView ivBack;//, ivOption;
    ConstraintLayout loHeader, btnClose, btnOption;
    boolean backButtonEnabled, optionButtonEnabled;

    public NormalButton(@NonNull Context context) {
        super(context);
    }

    public NormalButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NormalButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_default_header, this, false);
        addView(view);

        loHeader = findViewById(R.id.loHeader);

        //왼쪽 위(뒤로가기)
        btnClose = findViewById(R.id.btnClose);
        ivBack = findViewById(R.id.btn_back);

        tvTitle = findViewById(R.id.tvTitle);

        //오른쪽 위(옵션)
        btnOption = findViewById(R.id.btnOption);
        tvOption = findViewById(R.id.tvOption);
//        ivOption = findViewById(R.id.ivOption);
    }


    ///설정창

    private void getAttrs(AttributeSet attrs){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.DefaultHeader);
        setTypedArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.DefaultHeader, defStyle, 0);
        setTypedArray(typedArray);
    }

    private void setTypedArray(TypedArray typedArray){
        String title = typedArray.getString(R.styleable.DefaultHeader_title);
        tvTitle.setText(title);

        float size = typedArray.getDimensionPixelSize(R.styleable.DefaultHeader_titleSize, getResources().getDimensionPixelSize(R.dimen.fontHeaderTitle));
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);

        int style = typedArray.getInt(R.styleable.DefaultHeader_titleStyle, 0);
        tvTitle.setTypeface(null, style);

        int backResId = typedArray.getResourceId(R.styleable.DefaultHeader_backButtonImageSource, R.drawable.icon_basic_header_btn_back);
        ivBack.setImageResource(backResId);

        int backgroundcolor =typedArray.getColor(R.styleable.DefaultHeader_backgroundColor, getResources().getColor(R.color.white));
        loHeader.setBackgroundColor(backgroundcolor);

        int backbuttoncolor =typedArray.getColor(R.styleable.DefaultHeader_backButtonColor, getResources().getColor(R.color.black));
        ivBack.setColorFilter(backbuttoncolor);

        int titletextcolor =typedArray.getColor(R.styleable.DefaultHeader_titleTextColor, getResources().getColor(R.color.black));
        tvTitle.setTextColor(titletextcolor);


        boolean setOutline = typedArray.getBoolean(R.styleable.DefaultHeader_optionButtonOutline, false);
        if(setOutline)
            tvOption.setBackgroundResource(R.drawable.shape_round_square_outline_primary);
        else{
            int optionResId = typedArray.getResourceId(R.styleable.DefaultHeader_optionButtonImageSource, 0);
            tvOption.setBackgroundResource(optionResId);
        }

        String text = typedArray.getString(R.styleable.DefaultHeader_optionButtonText);
        tvOption.setText(text);

        backButtonEnabled = typedArray.getBoolean(R.styleable.DefaultHeader_backButtonEnabled, true);
        if(!backButtonEnabled) loHeader.removeView(btnClose);

        optionButtonEnabled = typedArray.getBoolean(R.styleable.DefaultHeader_optionButtonEnabled, false);
        if(!optionButtonEnabled) loHeader.removeView(btnOption);



        typedArray.recycle();
    }

    public void setTitle(String text){
        tvTitle.setText(text);
    }
    public void setOptionButtonEnabled(Boolean enabled)
    {if (enabled) {btnOption.setVisibility(VISIBLE);}else{btnOption.setVisibility(INVISIBLE);}}
    public void setOptionButtonText(String text){
        tvOption.setText(text);
    }

    public void setOptionButtonOutline(boolean value){
        if(value){
            tvOption.setBackgroundResource(R.drawable.shape_round_square_outline_primary);
        }
    }

    public void setOptionButtonEnabled(boolean value){
        tvOption.setVisibility(value? VISIBLE : GONE);
    }

    public String getOptionButtonText(){
        return tvOption.getText().toString();
    }

    public void setOptionButtonImageResource(int id){
        tvOption.setBackgroundResource(id);
    }

    public void setOnBackButtonClickListener(OnClickListener listener){
        if(backButtonEnabled)
            btnClose.setOnClickListener(listener);
    }

    public void setOnOptionButtonClickListener(OnClickListener listener){
        if(optionButtonEnabled)
            btnOption.setOnClickListener(listener);
    }



}
