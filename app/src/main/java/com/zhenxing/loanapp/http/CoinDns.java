package com.zhenxing.loanapp.http;

import com.orhanobut.logger.Logger;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.Dns;

// $ nslookup api.58coin.com
//     Server:		202.106.196.115
//     Address:	202.106.196.115#53
//
//     Non-authoritative answer:
//     Name:	api.58coin.com
//     Address: 23.102.237.125
//     Name:	api.58coin.com
//     Address: 23.102.227.157

// $ nslookup hashapi.58coin.com
//     Server:		202.106.196.115
//     Address:	202.106.196.115#53
//
//     Non-authoritative answer:
//     Name:	hashapi.58coin.com
//     Address: 23.102.227.157
//     Name:	hashapi.58coin.com
//     Address: 23.102.237.125

// $ nslookup ws.58coin.com
//     Server:		202.106.196.115
//     Address:	202.106.196.115#53
//
//     Non-authoritative answer:
//     Name:	ws.58coin.com
//     Address: 52.221.182.86

public class CoinDns implements Dns {

    private static final boolean DEBUG = false;
    private Map<String, List<InetAddress>> dnsCache = new HashMap<>();

    @Override
    public List<InetAddress> lookup(String hostname) throws UnknownHostException {
        Logger.i("lookup: hostname:" + hostname);

        InetAddress[] sysInetAddressList = InetAddress.getAllByName(hostname);

        List<InetAddress> userInetAddressList = dnsCache.get(hostname);
        if (userInetAddressList == null) {
            if (DEBUG) {
                Logger.i(Arrays.asList(sysInetAddressList).toString());
            }
            return Arrays.asList(sysInetAddressList);
        }

        //检查系统返回dns, 如果包含有效dns则只利用系统dns
        boolean isUse = true;
        Iterator<InetAddress> inetAddressIterator = userInetAddressList.iterator();
        while (inetAddressIterator.hasNext()) {
            InetAddress inetAddress = inetAddressIterator.next();
            for (InetAddress hostInetAddress : sysInetAddressList) {
                if (inetAddress.getHostAddress().equals(hostInetAddress.getHostAddress())) {
                    isUse = false;
                    break;
                }
            }
            if (!isUse) {
                dnsCache.remove(hostname);
                break;
            }
        }

        List<InetAddress> list;
        if (isUse) {
            list = new ArrayList<>();
            list.addAll(Arrays.asList(sysInetAddressList));
            list.addAll(userInetAddressList);
        } else {
            list = Arrays.asList(sysInetAddressList);
        }
        if (DEBUG) {
            Logger.i(list.toString());
        }
        return list;
    }

    public void setInetAddressList(String host, List<InetAddress> inetAddress) {
        if (inetAddress == null || inetAddress.isEmpty()) {
            dnsCache.remove(host);
        } else {
            dnsCache.put(host, inetAddress);
        }
    }
}
