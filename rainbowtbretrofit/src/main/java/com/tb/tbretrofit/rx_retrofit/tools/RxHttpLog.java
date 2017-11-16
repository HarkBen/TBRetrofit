package com.tb.tbretrofit.rx_retrofit.tools;

import android.util.Log;

/**
 * @描述： －
 * -
 * @作者：zhusw
 * @创建时间：17/11/16 上午11:13
 * @最后更新时间：17/11/16 上午11:13
 */
public class RxHttpLog {

	private static boolean deBug = true;
	private static String str = "";

	public static  void setDeBug(boolean isDebug){
		deBug = isDebug;
	}

	public static void printD(String TAG,String MSG){
		if(deBug){
		str = "RxHttpLog-->>"+TAG+"-->>"+MSG;
		Log.d(TAG, str);
		}
	}
	public static void printI(String TAG,String MSG){
		if(deBug){
			str = "RxHttpLog-->>"+TAG+"-->>"+MSG;
		Log.i(TAG, str);	
		}
	}



}
