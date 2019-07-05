package org.neep.utils.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * 包名：com.mechat.connector.common.network<br>
 * <p>功能:</p>
 *
 * @version:1.0 <br/>
 */
public class NetWorkAddressUtils {
    private static Logger logger = LoggerFactory.getLogger(NetWorkAddressUtils.class);

    /**
     * <p>功能:获取本地IP地址</p>
     * Version:1.0 <br/>
     *
     * @param
     * @return
     */
    public static String findLocalHostAddress() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                Enumeration<InetAddress> addresss = networkInterface.getInetAddresses();
                while (addresss.hasMoreElements()) {
                    InetAddress nextElement = addresss.nextElement();
                    String hostAddress = nextElement.getHostAddress();
                    String name = networkInterface.getName();
                    if (StringHelper.contains(name, "docker")) {
                        continue;
                    }
                    if (StringHelper.equals(hostAddress, "127.0.0.1")) {
                        continue;
                    }
                    if (hostAddress.indexOf("0:0:0") >= 0) {
                        continue;
                    }
                    return hostAddress;
                }
            }
        } catch (Exception e) {
            logger.error("find local host address fail,error message:" + e.getMessage(), e);
        }
        return "";
    }
    public   static   long localIpLong() {
        String strIp=findLocalHostAddress();
        long[] ip = new long[4];
        // 先找到IP地址字符串中.的位置
        int position1 = strIp.indexOf(".");
        int position2 = strIp.indexOf(".", position1 + 1);
        int position3 = strIp.indexOf(".", position2 + 1);
        // 将每个.之间的字符串转换成整型
        ip[0] = Long.parseLong(strIp.substring(0, position1));
        ip[1] = Long.parseLong(strIp.substring(position1 + 1, position2));
        ip[2] = Long.parseLong(strIp.substring(position2 + 1, position3));
        ip[3] = Long.parseLong(strIp.substring(position3 + 1));
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }
}
