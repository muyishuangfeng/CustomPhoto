package com.yk.silence.myapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.yk.silence.myapplication.base.BaseApplation;

/**
 * Created by Silence on 2016/6/26.
 */
public class TDevice {
	// 手机网络类型
	public static final int NETTYPE_WIFI = 0x01;
	public static final int NETTYPE_CMWAP = 0x02;
	public static final int NETTYPE_CMNET = 0x03;
	public static float displayDensity = 0.0F;
	//是否有摄像头
	private static Boolean mHasCamera = null;

	/**
	 * 收起软键盘
	 *
	 * @param activity
	 */
	public static void hideKeyBord(Activity activity) {
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(
				Activity.INPUT_METHOD_SERVICE);
		if (imm != null) {
			imm.hideSoftInputFromInputMethod(activity.getWindow().getDecorView().getWindowToken(), 0);
		}
	}

	/**
	 * 弹出软键盘
	 */
	public static void showKeyBoard(final View view) {
		view.setFocusable(true);
		view.setFocusableInTouchMode(true);
		view.requestFocus();

		view.postDelayed(new Runnable() {
			@Override
			public void run() {
				InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(
						Activity.INPUT_METHOD_SERVICE);
				imm.showSoftInput(view, InputMethodManager.RESULT_UNCHANGED_SHOWN);
			}
		}, 400);
	}

	/**
	 * dp转换为px
	 *
	 * @param dp
	 */
	public static float dpToPixel(float dp) {
		return dp * (getDisplayMetrics().densityDpi / 160F);
	}

	/**
	 * pixel转换为dp
	 *
	 * @param pix
	 * @return
	 */
	public static float pixelToDp(float pix) {
		return pix / (getDisplayMetrics().densityDpi / 160F);

	}

	/**
	 * 获取屏幕分辨率
	 *
	 * @return
	 */
	public static DisplayMetrics getDisplayMetrics() {
		DisplayMetrics displayMetrics = new DisplayMetrics();
		((WindowManager) BaseApplation.context().getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay()
				.getMetrics(displayMetrics);
		return displayMetrics;
	}

	/**
	 * 获取像素密度
	 *
	 * @return
	 */
	public static float getDisplay() {
		if (displayDensity == 0.0) {
			displayDensity = getDisplayMetrics().density;
		}
		return displayDensity;
	}

	/**
	 * 获取屏幕高
	 *
	 * @return
	 */
	public static float getScreenHeight() {
		return getDisplayMetrics().heightPixels;
	}

	/**
	 * 获取屏幕高
	 *
	 * @return
	 */
	public static float getScreenWidth() {
		return getDisplayMetrics().widthPixels;
	}

	/**
	 * 获取真实的屏幕大小
	 *
	 * @return
	 */
	public static int[] getRealScreenSize(Activity activity) {
		int[] size = new int[2];
		int screenWidth = 0;
		int screenHeight = 0;
		WindowManager wm = activity.getWindowManager();
		Display display = wm.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		// since SDK_INT = 1;
		screenHeight = metrics.heightPixels;
		screenWidth = metrics.widthPixels;

		if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17) {
			try {
				screenWidth = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
				screenHeight = (Integer) Display.class.getMethod("getRawHeight").invoke(display);

			} catch (Exception ex) {
				ex.printStackTrace();
			}
			if (Build.VERSION.SDK_INT > 17) {
				Point realSize = new Point();
				try {
					Display.class.getMethod("getRealSize", Point.class).invoke(realSize);
					screenHeight = realSize.y;
					screenWidth = realSize.x;

				} catch (Exception ex) {
				}
				size[0] = screenWidth;
				size[1] = screenHeight;
			}
		}
		return size;
	}

	/**
	 * 是否存摄像头
	 */
	public static final boolean hasCamera() {
		if (mHasCamera == null) {
			//獲取包管理器
			PackageManager pgm = BaseApplation.context().getPackageManager();
			boolean flag = pgm.hasSystemFeature("android.hardware.camera.front");
			boolean flag1 = pgm.hasSystemFeature("android.hardware.camera");
			boolean flag2;
			if (flag || flag1) {
				flag2 = true;
			} else {
				flag2 = false;
			}
			mHasCamera = Boolean.valueOf(flag2);
		}
		return mHasCamera.booleanValue();

	}
}
