package com.tb.rx_retrofit.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

/**
 * @描述： -网络状态判断
 * -
 * @作者：zhusw
 * @创建时间：17/11/17 下午12:11
 * @最后更新时间：17/11/17 下午12:11
 */

public class NetworkStatusUtils {

    public static boolean networkIsConnected(Context context) {
        if (null != context) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (null != cm) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Network[] networks = cm.getAllNetworks();
                    boolean networkIsConnected = false;
                    if (null != networks && networks.length > 0) {
                        NetworkInfo networkInfo;
                        for (int i = networks.length - 1; i >= 0; i--) {
                            networkInfo = cm.getNetworkInfo(networks[i]);
                            if (networkInfo != null && networkInfo.isConnected()) {
                                networkIsConnected = true;
                                break;
                            }
                        }
                    }
                    return networkIsConnected;
                } else {
                    NetworkInfo networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                    boolean isWifiConnected = networkInfo.isConnected();
                    networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                    boolean isMobileConnected = networkInfo.isConnected();
                    return isWifiConnected || isMobileConnected;
                }
            } else {
                throw new NullPointerException("cann not obtain  ConnectivityManager," +
                        "check Internet permission  or Mobile Phone Network Setting");
            }
        }
        return false;
    }
}
