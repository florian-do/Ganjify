package fr.do_f.ganjify.utils.fonts;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;

/**
 * Created by do_f on 21/02/16.
 */
public class TextView extends android.widget.TextView {
    public TextView(Context context) {
        super(context);
        setFont();
    }

    public TextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public TextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setFont();
    }

    public void setFont() {
        if (this.isInEditMode()) {
            return;
        }
        Typeface mTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/KaushanScript-Regular.otf");
        setTypeface(mTypeface);
    }
}
