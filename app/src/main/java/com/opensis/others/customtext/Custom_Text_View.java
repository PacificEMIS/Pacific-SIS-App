package com.opensis.others.customtext;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


public class Custom_Text_View extends TextView {

    public Custom_Text_View(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public Custom_Text_View(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Custom_Text_View(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface dashboardtf = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/Roboto-Regular.ttf");
        setTypeface(dashboardtf);
    }
}
