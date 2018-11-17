package com.zhenxing.loanapp.bean;

import com.zhenxing.loanapp.util.ConstantUtil.HttpCode;

/**
 * Created by Administrator on 2017/8/29.
 */

public class BaseBean <T> implements BaseBeanInterface {

    public int code;
    private String message;
    private String showMessage = "";//TBApplication.getInstance().getString(R.string.success);

    public T data;

    public BaseBean() {
    }

    public BaseBean(T data, int code) {
        this.data = data;
        this.code = code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public boolean isSuccess() {
        return code == HttpCode.CODE_SUCCESS;
    }

    private String getMessage() {
        return message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public String getShowMessage() {
        return showMessage;
    }

    public void setShowMessage(String showMessage) {
        this.showMessage = showMessage;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
               "code=" + code +
               ", message='" + message + '\'' +
               ", data=" + data +
               '}';
    }
}
