package org.spring.data.access.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ClientRequestMetaInfo {
    private static ThreadLocal<String> localIpAddress = new ThreadLocal<>();
    public static void setClientIpAddress(String ipAddress) {
        localIpAddress.set(ipAddress);
    }

    public static String getClientIpAddress() {
        return localIpAddress.get();
    }

}