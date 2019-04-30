package com.shoniz.saledistributemobility.view.customer.cardindex;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shoniz.saledistributemobility.R;

public class KeypadView extends LinearLayout  {

    int[] Ids = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,};

    private String empty = " ";
    private String value = empty;
    int maxNumberCount;
    int maxNumberDefault=4;

    KeypadViewClickNumListener viewClickNumListener;
    KeypadViewClickApplyListener viewClickApplyListener;
    KeypadViewClickClearListener viewClickClearListener;

    public void setValue(String value){
        this.value=value;
    }

    public KeypadView(Context context) {
        super(context);
        init(context, null);
    }
    public String getValueString()
    {
        return value;
    }
    public int getValueInteger()
    {
        if(value.length()>0 && !value.equals(empty))
        {
            return Integer.parseInt(value);
        }
       return 0;
    }


    public KeypadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    public KeypadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void setOnClickClearListener(KeypadViewClickClearListener viewClickClearListener) {
        this.viewClickClearListener = viewClickClearListener;
    }

    public void setOnClickApplyListener(KeypadViewClickApplyListener viewClickApplyListener) {
        this.viewClickApplyListener = viewClickApplyListener;
    }

    public void setOnClickNumListener(KeypadViewClickNumListener viewClickNumListener) {
        this.viewClickNumListener = viewClickNumListener;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public KeypadView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.KeypadView, 0, 0);

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_keypad, this, true);
        for (int id : Ids) {
            Button btn = findViewById(id);
            btn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(maxNumberCount>value.length()){
                        if (value.equals(empty)) {
                            value = ((TextView) v).getText().toString();
                        } else {
                            value += ((TextView) v).getText().toString();
                        }
                    }else{
                        String previousValue = value.substring(0, value.length() - 1);
                        value = previousValue + ((TextView) v).getText().toString();
                    }
                    if(viewClickNumListener!=null){
                        viewClickNumListener.OnClick(KeypadView.this);
                    }
                }
            });
        }

        int resApplyIcon = a.getResourceId(R.styleable.KeypadView_applyIcon, 0);
        int resClearIcon = a.getResourceId(R.styleable.KeypadView_clearIcon, 0);
          maxNumberCount = a.getInt(R.styleable.KeypadView_maxNumberCount, maxNumberDefault);


        ImageButton btnApply = findViewById(R.id.btnApply);
        ImageButton btnClear = findViewById(R.id.btnClear);
        if (resApplyIcon != 0) {
             Drawable drawableApply = context.getResources().getDrawable(resApplyIcon);
            btnApply.setImageDrawable(drawableApply);
        }
        if (resClearIcon != 0) {
            Drawable drawableClear = context.getResources().getDrawable(resClearIcon);
            btnClear.setImageDrawable(drawableClear);
        }
        btnApply.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewClickApplyListener!=null){
                    viewClickApplyListener.OnClick(KeypadView.this);
                }

            }
        });
        btnClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                backSpace();
                if(viewClickClearListener!=null){
                    viewClickClearListener.OnClick(KeypadView.this);
                }

            }
        });
        btnClear.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                emptyValue();
                return false;
            }


        });
    }

    public void backSpace() {
        if (!value.equals(empty) && value.length()>0)
            value = value.substring(0, value.length() - 1);
        else
            emptyValue();
    }

    public void emptyValue() {
        value = empty;
    }

    public interface KeypadViewClickNumListener {
        void OnClick(KeypadView view);
    }
    public interface KeypadViewClickClearListener {
        void OnClick(KeypadView view);
    }
    public interface KeypadViewClickApplyListener {
        void OnClick(KeypadView view);
    }
}
