package com.opensis.others.customtext;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;


public class Custom_EditText extends EditText {

    public Custom_EditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public Custom_EditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Custom_EditText(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface dashboardtf = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/Roboto-Regular.ttf");
        setTypeface(dashboardtf);
    }
}
