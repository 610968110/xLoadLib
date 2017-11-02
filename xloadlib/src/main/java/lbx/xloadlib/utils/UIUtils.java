
package lbx.xloadlib.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class UIUtils {
    public UIUtils() {
    }

    public static String getString(Context context, int id) {
        return context.getResources().getString(id);
    }

    public static String[] getStringArray(Context context, int id) {
        return context.getResources().getStringArray(id);
    }

    public static Drawable getDrawable(Context context, int id) {
        return context.getResources().getDrawable(id);
    }

    public static int getColor(Context context, int id) {
        return context.getResources().getColor(id);
    }

    public static ColorStateList getColorStateList(Context context, int id) {
        return context.getResources().getColorStateList(id);
    }

    public static int getDimen(Context context, int id) {
        return context.getResources().getDimensionPixelSize(id);
    }

    public static int dip2px(Context context, float dip) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int)(dip * density + 0.5F);
    }

    public static float px2dip(Context context, int px) {
        float density = context.getResources().getDisplayMetrics().density;
        return (float)px / density;
    }

    public static int px2sp(Context context, int px) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)((float)px / fontScale + 0.5F);
    }

    public static int sp2px(Context context, int px) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)((float)px / fontScale + 0.5F);
    }

    public static View inflate(Context context, int id) {
        return View.inflate(context, id, (ViewGroup)null);
    }

    public static WindowManager getWindowManager(Context context) {
        return (WindowManager)context.getSystemService("window");
    }

    public static int getScreenWidth(Context context) {
        return getWindowManager(context).getDefaultDisplay().getWidth();
    }

    public static int getScreenHeight(Context context) {
        return getWindowManager(context).getDefaultDisplay().getHeight();
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if(resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }

        return result;
    }
}
