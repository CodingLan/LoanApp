package com.zhenxing.loanapp.util;

public class ConstantUtil {
    public static class HttpCode {
        /**
         * 成功
         */
        public static final int CODE_SUCCESS = 0;

        public static final int CODE_OSS_FAIL = 9900001;

        /**
         * 需要验证
         */
        public static final int CODE_PRE_VERIFY = 112;

        /**
         * 转账处理中
         */
        public static final int CODE_TRANSFER_PROCESS = 610;

        /**
         * 登录失败次数
         */
        public static final int CODE_LOGIN_LAST_TIMES = 107;

        /**
         * 登录失败超次数
         */
        public static final int CODE_LOGIN_OVER_TIMES_1 = 108;

        /**
         * 登录失败超次数
         */
        public static final int CODE_LOGIN_OVER_TIMES_2 = 109;

        /**
         * 短信次数
         */
        public static final int CODE_SMS_LASTCOUNT = 302;

        /**
         * 二维码无效或已过期
         */
        public static final int CODE_SCAN_RESULT = 171;
    }
}
