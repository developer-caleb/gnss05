package kr.loplab.gnss05;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class TextDivider extends LinearLayout {
    TextView dividertitle;


    public TextDivider(Context context) {
        super(context);
        initView();
    }

    public TextDivider(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        getAttrs(attrs);
    }

    public TextDivider(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        getAttrs(attrs, defStyleAttr);
    }

    private void initView(){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_text_divider, this, false);
        addView(view);
        dividertitle = findViewById(R.id.tvTitle);
    }

    private void getAttrs(AttributeSet attrs){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TextDivider);
        setTypedArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TextDivider, defStyle, 0);
        setTypedArray(typedArray);
    }

    private void setTypedArray(TypedArray typedArray){
        String title = typedArray.getString(R.styleable.TextDivider_title2);
        dividertitle.setText(title);
        typedArray.recycle();
    }

    public void setTitle(String text){
        dividertitle.setText(text);
    }


}

