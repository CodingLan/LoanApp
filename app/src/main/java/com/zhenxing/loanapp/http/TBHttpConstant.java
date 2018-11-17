package com.zhenxing.loanapp.http;

import android.content.Context;

import com.zhenxing.loanapp.R;
import com.zhenxing.loanapp.util.TBUtils;

/**
 * Created by xtdhwl on 26/09/2017.
 */

public class TBHttpConstant {

    private static final String HTTP_CODE = "HTTP_CODE_";

    /**
     * http 错误码
     */
    public static class TBHttpCode {

        public static final int Success = 0;

        public static final int FAIL_SERVER = -1;
        public static final int FAIL_NETWORK = -2;
        public static final int FAIL_UNKNOWN = -3;

        /**
         * 根据code获取描述
         *
         * @param code
         * @return
         */
        public static String getName(Context context, int code) {
            String desc = null;
            if (context != null) {
                switch (code) {
                    case FAIL_SERVER:
                        desc = TBUtils.getString(context, R.string.tb_error_server);
                        break;
                    case FAIL_NETWORK:
                        desc = TBUtils.getString(context, R.string.tb_error_net);
                        break;
                    case FAIL_UNKNOWN:
                        desc = TBUtils.getString(context, R.string.tb_error_unknown);
                        break;
                }

               /* if (desc == null) {
                    desc = ResourcesUtil.getString(context, HTTP_CODE + code);
                }*/
            }

            if (desc == null) {
                desc = "";
            }
            return desc;
        }
    }
}
