package fr.do_f.ganjify.utils;

import android.content.res.Resources;

/**
 * Created by do_f on 21/02/16.
 */
public class Utils {
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
