package com.yk.silence.myapplication.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by Silence on 2016/6/26.
 */
public class BaseApplation extends Application {
	static Context mContext;

	@Override
	public void onCreate() {
		super.onCreate();
		this.mContext=getApplicationContext();
	}
	public static  synchronized  BaseApplation context(){
		return (BaseApplation) mContext;
	}
}
